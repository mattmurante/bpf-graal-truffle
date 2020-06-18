package com.oracle.truffle.bpf;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.bpf.nodes.*;
import com.oracle.truffle.bpf.nodes.util.EBPFOpcodes;

public class BPFParser {

	// Parser that generates tree of instruction nodes for each line in BPF program

	public static ProgramNode parse(BPFLanguage language, Source source) {
		// Creating statement nodes for each instruction, outputting tree with
		// program root node
		byte[] program = source.getBytes().toByteArray();
		int count = 0;
		ByteBuffer bb = ByteBuffer.wrap(program);
		// Preparing frame slots for use
		FrameDescriptor desc = new FrameDescriptor();
		FrameSlot pcSlot = desc.findOrAddFrameSlot("pc", FrameSlotKind.Int);
		FrameSlot regsSlot = desc.findOrAddFrameSlot("regs", FrameSlotKind.Object);
		FrameSlot memSlot = desc.findOrAddFrameSlot("mem", FrameSlotKind.Object);
		// May need to change byte order depending on system - i.e. change to
		// ByteOrder.nativeOrder() assuming bpf program is generated on local machine
		bb.order(ByteOrder.LITTLE_ENDIAN);
		CommitStateNode[] insts = new CommitStateNode[program.length/8];
		while (count * 8 + 8 <= program.length) {
			try {
				final byte opcode = bb.get();
				final byte regs = bb.get();
				final short offset = bb.getShort();
				final int imm = bb.getInt();
				//Determine what kind of instruction it is
				final byte instType = (byte) ((opcode & 0x0f) % 0x08);
				if (instType == EBPFOpcodes.EBPF_CLS_JMP) {
					insts[count] = CommitStateNodeGen.create(pcSlot, regsSlot, memSlot,
							JumpInstructionNodeGen.create(opcode,
							(byte) ((regs >>> 4) & 0x0f), (byte) (regs & 0x0f),
							offset, imm));
				}
				else if (instType == EBPFOpcodes.EBPF_CLS_ALU) {
					insts[count] = CommitStateNodeGen.create(pcSlot, regsSlot, memSlot,
							ALUInstructionNodeGen.create(opcode,
							(byte) ((regs >>> 4) & 0x0f), (byte) (regs & 0x0f),
							offset, imm));
				}
				else if (instType == EBPFOpcodes.EBPF_CLS_ALU64) {
					insts[count] = CommitStateNodeGen.create(pcSlot, regsSlot, memSlot,
							ALU64InstructionNodeGen.create(opcode,
							(byte) ((regs >>> 4) & 0x0f), (byte) (regs & 0x0f),
							offset, imm));
				}
				else {
					insts[count] = CommitStateNodeGen.create(pcSlot, regsSlot, memSlot,
							MemInstructionNodeGen.create(opcode,
							(byte) ((regs >>> 4) & 0x0f), (byte) (regs & 0x0f),
							offset, imm));
				}
				count++;
				// If loading double word, make helper instruction node
				if (opcode == EBPFOpcodes.EBPF_OP_LDDW && count * 8 + 8 <= program.length) {
					bb.getInt();
					final int imm2 = bb.getInt();
					insts[count] = CommitStateNodeGen.create(pcSlot, regsSlot, memSlot,
							DoubleWordHelperNodeGen.create(opcode,
							(byte) ((regs >>> 4) & 0x0f), (byte) (regs & 0x0f),
							offset, imm2));
					count++;
				}
			} catch (Exception e) {
				System.err.println("Error that caused improper parsing of BPF program: " + e);
				e.printStackTrace();
				System.exit(-1);
			}
		}
		return new ProgramNode(language, desc, pcSlot, regsSlot, memSlot, insts, program);
	}
}
