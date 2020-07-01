package com.oracle.truffle.bpf.nodes.alu;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(language = "BPF", description = "NEG operation")
public abstract class NEG extends ALUExpressionNode {
	
	@Specialization
	@TruffleBoundary
	public long[] operation(long[] regs) {
		byte dest = getDestReg();
		regs[dest] *= -1;
		regs[dest] &= LOWER_BITS;
		return regs;
	}
	
}
