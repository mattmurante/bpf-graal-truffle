package com.oracle.truffle.bpf.nodes.jump;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.bpf.nodes.ExpressionNode;
import com.oracle.truffle.bpf.nodes.util.ReadPC;
import com.oracle.truffle.bpf.nodes.util.ReadRegs;

@NodeInfo(language = "BPF", description = "Base node for each jump operation")
@NodeChild(value = "pc", type = ReadPC.class)
@NodeChild(value = "regs", type = ReadRegs.class)
public abstract class JumpExpressionNode extends ExpressionNode {
	
}
