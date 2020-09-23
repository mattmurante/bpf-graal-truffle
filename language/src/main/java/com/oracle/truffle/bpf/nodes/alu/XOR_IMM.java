package com.oracle.truffle.bpf.nodes.alu;

import com.oracle.truffle.api.dsl.CachedLanguage;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.bpf.BPFLanguage;
import com.oracle.truffle.bpf.nodes.InstructionNode;

@NodeInfo(language = "BPF", description = "XOR_IMM operation")
public abstract class XOR_IMM extends InstructionNode {
	
	public XOR_IMM(byte opcode, byte srcReg, byte destReg, short offset, int imm) {
		super(opcode, srcReg, destReg, offset, imm);
	}

	@Specialization
	public boolean operation(@CachedLanguage BPFLanguage language) {
		language.incPc();
		long[] regs = language.getRegisters();
		regs[destReg] ^= imm;
		regs[destReg] &= 0xffffffffL;
		return true;
	}
	
}
