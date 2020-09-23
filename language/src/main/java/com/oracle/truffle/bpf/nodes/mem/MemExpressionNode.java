package com.oracle.truffle.bpf.nodes.mem;

import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.bpf.nodes.InstructionNode;

@NodeInfo(language = "BPF", description = "Base node for each memory operation")
public abstract class MemExpressionNode extends InstructionNode {
	
	public MemExpressionNode(byte opcode, byte srcReg, byte destReg, short offset, int imm) {
		super(opcode, srcReg, destReg, offset, imm);
	}
	
}
