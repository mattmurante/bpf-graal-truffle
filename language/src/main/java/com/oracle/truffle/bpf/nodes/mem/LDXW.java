package com.oracle.truffle.bpf.nodes.mem;

import com.oracle.truffle.api.dsl.CachedLanguage;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.bpf.BPFLanguage;
import com.oracle.truffle.bpf.nodes.util.Memory;

@NodeInfo(language = "BPF", description = "LDXW operation")
public abstract class LDXW extends MemExpressionNode {
	
	public LDXW(byte opcode, byte srcReg, byte destReg, short offset, int imm) {
		super(opcode, srcReg, destReg, offset, imm);
	}

	@Specialization
	public boolean operation(@CachedLanguage BPFLanguage language) {
		language.incPc();
		long[] regs = language.getRegisters();
		Memory memory = language.getMemory();
		regs[destReg] = memory.getInt(regs[srcReg] + offset);
		return true;
	}
	
}
