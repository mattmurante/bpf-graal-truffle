package com.oracle.truffle.bpf.nodes.alu;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(language = "BPF", description = "ADD_IMM operation")
public abstract class ADD_IMM extends ALUExpressionNode {
	
	@Specialization
	@TruffleBoundary
	public long[] operation(long[] regs) {
		byte dest = getDestReg();
		regs[dest] += getImm();
		regs[dest] &= LOWER_BITS;
		return regs;
	}
	
}
