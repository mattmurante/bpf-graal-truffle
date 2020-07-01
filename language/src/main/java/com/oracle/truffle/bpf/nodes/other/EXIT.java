package com.oracle.truffle.bpf.nodes.other;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.bpf.nodes.ExpressionNode;

@NodeInfo(language = "BPF", description = "EXIT operation")
public abstract class EXIT extends ExpressionNode {
	
	@Specialization
	@TruffleBoundary
	public boolean operation() {
		return false;
	}
	
}
