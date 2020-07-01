package com.oracle.truffle.bpf.nodes.alu64;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(language = "BPF", description = "AND64_IMM operation")
public abstract class AND64_IMM extends ALU64ExpressionNode {
	
	@Specialization
	@TruffleBoundary
	public long[] operation(long[] regs) {
		regs[getDestReg()] &= getImm();
		return regs;
	}
	
}