package com.oracle.truffle.bpf.nodes.alu64;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(language = "BPF", description = "DIV64_IMM operation")
public abstract class DIV64_IMM extends ALU64ExpressionNode {
	
	@Specialization
	@TruffleBoundary
	public long[] operation(long[] regs) {
		byte dest = getDestReg();
		regs[dest] = Long.divideUnsigned(regs[dest], getImm());
		return regs;
	}
	
}
