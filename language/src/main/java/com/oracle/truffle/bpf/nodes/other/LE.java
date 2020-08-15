package com.oracle.truffle.bpf.nodes.other;

import java.nio.ByteOrder;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.CachedLanguage;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.bpf.BPFLanguage;

@NodeInfo(language = "BPF", description = "LE operation")
public abstract class LE extends ByteswapExpressionNode {
	
	public LE(byte opcode, byte srcReg, byte destReg, short offset, int imm) {
		super(opcode, srcReg, destReg, offset, imm);
	}

	@Specialization
	@TruffleBoundary
	public boolean operation(@CachedLanguage BPFLanguage language) {
		language.incPc();
		long[] regs = language.getRegisters();
		if (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN) {
			regs[destReg] = byteSwap(regs[destReg], imm);
		}
		return true;
	}
	
}
