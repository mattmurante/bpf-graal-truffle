package com.oracle.truffle.bpf.nodes.mem;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.bpf.nodes.util.Memory;

@NodeInfo(language = "BPF", description = "LDXW operation")
public abstract class LDXW extends MemExpressionNode {
	
	@Specialization
	@TruffleBoundary
	public long[] operation(long[] regs, Memory memory) {
		regs[getDestReg()] = read(memory, regs[getSrcReg()] + getOffset(), 4);
		return regs;
	}
	
}
