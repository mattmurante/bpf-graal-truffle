package com.oracle.truffle.bpf.nodes.mem;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.bpf.nodes.util.Memory;

@NodeInfo(language = "BPF", description = "STW operation")
public abstract class STW extends MemExpressionNode {
	
	@Specialization
	@TruffleBoundary
	public Memory operation(long[] regs, Memory memory) {
		write(memory, regs[getDestReg()] + getOffset(), 4, getImm());
		return memory;
	}
	
}
