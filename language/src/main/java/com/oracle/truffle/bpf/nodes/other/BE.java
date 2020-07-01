package com.oracle.truffle.bpf.nodes.other;

import java.nio.ByteOrder;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(language = "BPF", description = "BE operation")
public abstract class BE extends ByteswapExpressionNode {
	
	@Specialization
	@TruffleBoundary
	public long[] operation(long[] regs) {
		if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
			byte dest = getDestReg();
			regs[dest] = byteSwap(regs[dest], getImm());
		}
		return regs;
	}
	
}
