package com.oracle.truffle.bpf.nodes.alu;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.bpf.nodes.ExpressionNode;
import com.oracle.truffle.bpf.nodes.util.ReadRegs;

@NodeInfo(language = "BPF", description = "Base node for each ALU operation")
@NodeChild(value = "regs", type = ReadRegs.class)
public abstract class ALUExpressionNode extends ExpressionNode {
	
	protected final long LOWER_BITS = 0xffffffffL;
	
}
