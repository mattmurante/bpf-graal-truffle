package com.oracle.truffle.bpf.nodes.mem;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.bpf.nodes.util.Memory;

@NodeInfo(language = "BPF", description = "LDXH operation")
public abstract class LDXH extends MemExpressionNode {
	
	@Specialization
	@TruffleBoundary
	public long[] operation(long[] regs, Memory memory) {
		regs[getDestReg()] = read(memory, regs[getSrcReg()] + getOffset(), 2);
		return regs;
	}
	
}
