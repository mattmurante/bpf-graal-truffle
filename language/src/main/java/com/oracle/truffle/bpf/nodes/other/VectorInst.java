package com.oracle.truffle.bpf.nodes.other;

import com.oracle.truffle.api.dsl.CachedLanguage;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.bpf.BPFLanguage;
import com.oracle.truffle.bpf.nodes.InstructionNode;
import com.oracle.truffle.bpf.nodes.util.Memory;

@NodeInfo(language = "BPF", description = "Vector Addition operation (test)")
public abstract class VectorInst extends InstructionNode {
	
	public VectorInst(byte opcode, byte srcReg, byte destReg, short offset, int imm) {
		super(opcode, srcReg, destReg, offset, imm);
	}

	@Specialization
	public boolean operation(@CachedLanguage BPFLanguage language) {
		language.incPc();
		Memory memory = language.getMemory();
		long address = imm;
		byte temp;
		for (int i = 0; i < 256; i++) {
			temp = memory.getByte(address);
			temp += offset;
			memory.setByte(address, temp);
			address += Byte.BYTES;
		}
		return true;
	}
	
}
