package com.oracle.truffle.bpf.nodes.other;

import java.nio.ByteOrder;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(language = "BPF", description = "LE operation")
public abstract class LE extends ByteswapExpressionNode {
	
	@Specialization
	@TruffleBoundary
	public long[] operation(long[] regs) {
		if (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN) {
			byte dest = getDestReg();
			regs[dest] = byteSwap(regs[dest], getImm());
		}
		return regs;
	}
	
}
