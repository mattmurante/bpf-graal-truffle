package com.oracle.truffle.bpf.nodes.other;

import com.oracle.truffle.api.dsl.CachedLanguage;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.bpf.BPFLanguage;
import com.oracle.truffle.bpf.nodes.InstructionNode;
import com.oracle.truffle.bpf.nodes.util.FunctionList;

@NodeInfo(language = "BPF", description = "CALL operation")
public abstract class CALL extends InstructionNode {
	
	public CALL(byte opcode, byte srcReg, byte destReg, short offset, int imm) {
		super(opcode, srcReg, destReg, offset, imm);
	}
	
	@Specialization
	public boolean operation(@CachedLanguage BPFLanguage language) {
		language.incPc();
		long[] regs = language.getRegisters();
		FunctionList extFns = language.getExtFns();
		regs[0] = extFns.execute(imm, regs[1], regs[2], regs[3], regs[4], regs[5]);
		return true;
	}
	
}
