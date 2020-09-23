package com.oracle.truffle.bpf.nodes.other;

import java.nio.ByteOrder;

import com.oracle.truffle.api.dsl.CachedLanguage;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.bpf.BPFLanguage;

@NodeInfo(language = "BPF", description = "BE operation")
public abstract class BE extends ByteswapExpressionNode {
	
	public BE(byte opcode, byte srcReg, byte destReg, short offset, int imm) {
		super(opcode, srcReg, destReg, offset, imm);
	}

	@Specialization
	public boolean operation(@CachedLanguage BPFLanguage language) {
		language.incPc();
		long[] regs = language.getRegisters();
		if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
			regs[destReg] = byteSwap(regs[destReg], imm);
		}
		return true;
	}
	
}
