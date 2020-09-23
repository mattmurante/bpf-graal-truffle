package com.oracle.truffle.bpf.nodes.jump;

import com.oracle.truffle.api.dsl.CachedLanguage;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.bpf.BPFLanguage;
import com.oracle.truffle.bpf.nodes.InstructionNode;

@NodeInfo(language = "BPF", description = "JSLT_REG operation")
public abstract class JSLT_REG extends InstructionNode {
	
	public JSLT_REG(byte opcode, byte srcReg, byte destReg, short offset, int imm) {
		super(opcode, srcReg, destReg, offset, imm);
	}

	@Specialization
	public boolean operation(@CachedLanguage BPFLanguage language) {
		int pc = language.getPc() + 1;
		long[] regs = language.getRegisters();
		if (regs[destReg] < regs[srcReg])
			pc += offset;
		language.setPc(pc);
		return true;
	}
	
}
