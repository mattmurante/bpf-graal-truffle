package com.oracle.truffle.bpf.nodes.alu;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(language = "BPF", description = "XOR_IMM operation")
public abstract class XOR_IMM extends ALUExpressionNode {
	
	@Specialization
	@TruffleBoundary
	public long[] operation(long[] regs) {
		byte dest = getDestReg();
		regs[dest] ^= getImm();
		regs[dest] &= LOWER_BITS;
		return regs;
	}
	
}
