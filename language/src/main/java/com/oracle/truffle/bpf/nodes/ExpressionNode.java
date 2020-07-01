package com.oracle.truffle.bpf.nodes;

import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.nodes.NodeInfo;

//Executes different instructions based on what opcode is given

@NodeInfo(language = "BPF", description = "Base node for each operation to implement")
@NodeField(name = "opcode", type = byte.class)
@NodeField(name = "srcReg", type = byte.class)
@NodeField(name = "destReg", type = byte.class)
@NodeField(name = "offset", type = short.class)
@NodeField(name = "imm", type = int.class)
public abstract class ExpressionNode extends BaseNode {
	
	protected abstract byte getOpcode();
	protected abstract byte getSrcReg();
	protected abstract byte getDestReg();
	protected abstract short getOffset();
	protected abstract int getImm();
	
	// TODO: How to handle fn calls?
	
}
