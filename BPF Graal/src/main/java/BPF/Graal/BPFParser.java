package BPF.Graal;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.graalvm.polyglot.io.ByteSequence;
import com.oracle.truffle.api.source.Source;

public class BPFParser {

	// Generates Truffle tree of statement nodes for each line in BPF program

	public static Program parse(BPFLanguage language, Source source) {
		// Creating statement nodes for each instruction, outputting Truffle tree with
		// program root node
		byte[] program = source.getBytes().toByteArray();
		int count = 0;
		ByteBuffer bb = ByteBuffer.wrap(program);
		// May need to change byte order depending on system - i.e. change to
		// ByteOrder.nativeOrder() assuming bpf program is generated on local machine
		bb.order(ByteOrder.LITTLE_ENDIAN);
		InstructionNode[] insts = new InstructionNode[program.length / 8];
		while (count * 8 + 8 <= program.length) {
			try {
				final byte opcode = bb.get();
				final byte regs = bb.get();
				final short offset = bb.getShort();
				final int imm = bb.getInt();
				//Determine what kind of instruction it is
				final byte instType = (byte) ((opcode & 0x0f) % 0x08);
				if (instType == BPF.Graal.EBPFOpcodes.EBPF_CLS_JMP) {
					insts[count] = JumpInstructionNodeGen.create(new OpcodeNode(opcode),
							new RegisterNode((byte) ((regs >>> 4) & 0x0f)), new RegisterNode((byte) (regs & 0x0f)),
							new OffsetNode(offset), new ImmNode(imm));
				}
				else if (instType == BPF.Graal.EBPFOpcodes.EBPF_CLS_ALU || instType == BPF.Graal.EBPFOpcodes.EBPF_CLS_ALU64) {
					insts[count] = ALUInstructionNodeGen.create(new OpcodeNode(opcode),
							new RegisterNode((byte) ((regs >>> 4) & 0x0f)), new RegisterNode((byte) (regs & 0x0f)),
							new OffsetNode(offset), new ImmNode(imm));
				}
				else {
					insts[count] = MemInstructionNodeGen.create(new OpcodeNode(opcode),
					new RegisterNode((byte) ((regs >>> 4) & 0x0f)), new RegisterNode((byte) (regs & 0x0f)),
					new OffsetNode(offset), new ImmNode(imm));
				}
				count++;
				// If loading double word, make helper instruction node
				if (opcode == EBPFOpcodes.EBPF_OP_LDDW && count * 8 + 8 <= program.length) {
					bb.getInt();
					final int imm2 = bb.getInt();
					insts[count] = DoubleWordHelperNodeGen.create(new OpcodeNode(opcode),
							new RegisterNode((byte) ((regs >>> 4) & 0x0f)), new RegisterNode((byte) (regs & 0x0f)),
							new OffsetNode(offset), new ImmNode(imm2));
					count++;
				}
			} catch (Exception e) {
				System.err.println("Error that caused improper parsing of BPF program: " + e);
				e.printStackTrace();
				System.exit(-1);
			}
		}
		return new Program(language, insts, program);
	}
}
