package com.oracle.truffle.bpf.nodes.mem;

import com.oracle.truffle.api.dsl.CachedLanguage;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.bpf.BPFLanguage;

@NodeInfo(language = "BPF", description = "Second half of LDDW operation")
public abstract class LDDW2 extends MemExpressionNode {
	
	public LDDW2(byte opcode, byte srcReg, byte destReg, short offset, int imm) {
		super(opcode, srcReg, destReg, offset, imm);
	}

	@Specialization
	public boolean operation(@CachedLanguage BPFLanguage language) {
		language.incPc();
		long[] regs = language.getRegisters();
		regs[destReg] |= Integer.toUnsignedLong(imm) << 32;
		return true;
	}
	
}
