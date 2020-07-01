package com.oracle.truffle.bpf.nodes.jump;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(language = "BPF", description = "JLT_REG operation")
public abstract class JLT_REG extends JumpExpressionNode {
	
	@Specialization
	@TruffleBoundary
	public int operation(int pc, long[] regs) {
		if (Long.compareUnsigned(regs[getDestReg()], regs[getSrcReg()]) < 0)
			pc += getOffset();
		return pc;
	}
	
}
