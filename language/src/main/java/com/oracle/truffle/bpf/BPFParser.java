package com.oracle.truffle.bpf;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.bpf.nodes.InstructionNode;
import com.oracle.truffle.bpf.nodes.ProgramNode;
import com.oracle.truffle.bpf.nodes.util.EBPFOpcodes;

public class BPFParser {
	
	//Parser function that generates tree of instruction nodes for each line in BPF program
	public static ProgramNode parse(BPFLanguage language, Source source) {
		byte[] program = source.getBytes().toByteArray();
		int count = 0;
		ByteBuffer bb = ByteBuffer.wrap(program);
		//Preparing frame slots for use
		final FrameDescriptor desc = new FrameDescriptor();
		/* May need to change byte order depending on system - i.e. change to
		 * ByteOrder.nativeOrder() assuming bpf program is generated on local machine
		*/
		bb.order(ByteOrder.LITTLE_ENDIAN);
		InstructionNode[] insts = new InstructionNode[program.length/8];
		BPFNodeFactory factory = new BPFNodeFactory();
		while (8 + (count*8) <= program.length) {
			try {
				final byte opcode = bb.get();
				final byte regs = bb.get();
				final byte srcReg = (byte) ((regs >>> 4) & 0x0f);
				final byte destReg = (byte) (regs & 0x0f);
				final short offset = bb.getShort();
				final int imm = bb.getInt();
				//Determine what kind of instruction it is
				final byte instType = (byte) ((opcode & 0x0f) % 0x08);
				InstructionNode currentInst;
				//Create jump instruction
				if (instType == EBPFOpcodes.EBPF_CLS_JMP) {
					currentInst = factory.jumpInst(opcode, srcReg, destReg, offset, imm);
				}
				//Create alu instruction
				else if (instType == EBPFOpcodes.EBPF_CLS_ALU) {
					currentInst = factory.aluInst(opcode, srcReg, destReg, offset, imm);
				}
				//Create alu64 instruction
				else if (instType == EBPFOpcodes.EBPF_CLS_ALU64) {
					currentInst = factory.alu64Inst(opcode, srcReg, destReg, offset, imm);
				}
				//Create memory instruction
				else {
					currentInst = factory.memInst(opcode, srcReg, destReg, offset, imm);
					//In the case that opcode is LDDW, begin second half of the 2-instruction operation
					if (opcode == EBPFOpcodes.EBPF_OP_LDDW) {
						if (count * 8 + 8 > program.length) {
							throw new Exception("Unexpected end of Program");
						}
						insts[count] = currentInst;
						count++;
						bb.getInt(); //Ignores the first 32 bits of instruction
						final int imm2 = bb.getInt();
						currentInst = factory.lddwInst2(opcode, srcReg, destReg, offset, imm2);
					}
				}
				insts[count] = currentInst;
				count++;
			} catch (Exception e) {
				System.err.println("Error that caused improper parsing of BPF program: " + e);
				e.printStackTrace();
				System.exit(-1);
			}
		}
		return new ProgramNode(language, desc, insts, program);
	}
	
}
