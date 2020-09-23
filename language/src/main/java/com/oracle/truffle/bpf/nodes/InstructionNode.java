package com.oracle.truffle.bpf.nodes;

import com.oracle.truffle.api.nodes.NodeInfo;

//Executes different instructions based on what opcode is given

@NodeInfo(language = "BPF", description = "Base node for each operation to implement")
public abstract class InstructionNode extends BaseNode {
	
	protected final byte opcode;
	protected final byte srcReg;
	protected final byte destReg;
	protected final short offset;
	protected final int imm;
	
	public InstructionNode(byte opcode, byte srcReg, byte destReg, short offset, int imm) {
		this.opcode = opcode;
		this.srcReg = srcReg;
		this.destReg = destReg;
		this.offset = offset;
		this.imm = imm;
	}
	
}
