package com.oracle.truffle.bpf.nodes.jump;

import com.oracle.truffle.api.dsl.CachedLanguage;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.bpf.BPFLanguage;
import com.oracle.truffle.bpf.nodes.InstructionNode;

@NodeInfo(language = "BPF", description = "JLE_IMM operation")
public abstract class JLE_IMM extends InstructionNode {
	
	public JLE_IMM(byte opcode, byte srcReg, byte destReg, short offset, int imm) {
		super(opcode, srcReg, destReg, offset, imm);
	}

	@Specialization
	public boolean operation(@CachedLanguage BPFLanguage language) {
		int pc = language.getPc() + 1;
		long[] regs = language.getRegisters();
		if (Long.compareUnsigned(regs[destReg], imm) <= 0)
			pc += offset;
		language.setPc(pc);
		return true;
	}
	
}
