package BPF.Graal;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class TruffleTreeGenerator {
	
	//Generates Truffle tree of statement nodes for each line in BPF program
	
    public Program genTree(byte[] program) {
    	//Creating statement nodes for each instruction, outputting Truffle tree with program root node
    	int count = 0;
    	ByteBuffer bb = ByteBuffer.wrap(program);
    	//May need to change byte order depending on system - i.e. change to ByteOrder.nativeOrder() assuming bpf program is generated on local machine
    	bb.order(ByteOrder.LITTLE_ENDIAN);
    	InstructionNode[] insts = new InstructionNode[program.length/8];
   		while (count*8 + 8 <= program.length) {
    		try {
    			final byte opcode = bb.get();
    			final byte regs = bb.get();
    			final short offset = bb.getShort();
    			final int imm = bb.getInt();
	    		insts[count] = InstructionNodeGen.create(new OpcodeNode(opcode), new RegisterNode((byte) ((regs >>> 4) & 0x0f)), new RegisterNode((byte) (regs & 0x0f)), new OffsetNode(offset), new ImmNode(imm));
	    		count++;
    			//If loading double word, make helper instruction node
    			if (opcode == EBPFOpcodes.EBPF_OP_LDDW && count*8 + 8 <= program.length) {
    				bb.getInt();
    				final int imm2 = bb.getInt();
    				insts[count] = DoubleWordHelperNodeGen.create(new OpcodeNode(opcode), new RegisterNode((byte) ((regs >>> 4) & 0x0f)), new RegisterNode((byte) (regs & 0x0f)), new OffsetNode(offset), new ImmNode(imm2));
    				count++;
    			}
    		}
    		catch (Exception e) {
   				System.err.println("Error that caused improper parsing of BPF program: " + e);
   				e.printStackTrace();
   				System.exit(-1);
   			}
    	}
    	return new Program(insts, program);
    }
}
