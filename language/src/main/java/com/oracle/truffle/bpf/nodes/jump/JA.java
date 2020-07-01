package com.oracle.truffle.bpf.nodes.jump;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(language = "BPF", description = "JA operation")
public abstract class JA extends JumpExpressionNode {
	
	@Specialization
	@TruffleBoundary
	public int operation(int pc, long[] regs) {
		return pc + getOffset();
	}
	
}
