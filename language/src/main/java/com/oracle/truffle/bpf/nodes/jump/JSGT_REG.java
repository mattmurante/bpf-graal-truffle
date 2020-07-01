package com.oracle.truffle.bpf.nodes.jump;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(language = "BPF", description = "JSGT_REG operation")
public abstract class JSGT_REG extends JumpExpressionNode {
	
	@Specialization
	@TruffleBoundary
	public int operation(int pc, long[] regs) {
		if (regs[getDestReg()] > regs[getSrcReg()])
			pc += getOffset();
		return pc;
	}
	
}
