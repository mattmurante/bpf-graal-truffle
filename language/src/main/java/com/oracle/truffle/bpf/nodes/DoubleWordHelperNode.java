package com.oracle.truffle.bpf.nodes;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.bpf.nodes.util.RegLambda;

//Helper instruction node to load double word - maybe not the most elegant solution, but works nonetheless

@NodeInfo(language = "BPF", description = "Specialized instruction node to help with LDDW op")
public abstract class DoubleWordHelperNode extends InstructionNode {

	@Specialization(guards = "opcode == EBPF_OP_LDDW")
	@TruffleBoundary
	public RegLambda doLDDW() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] |= Integer.toUnsignedLong(getImm()) << 32;
		};
		return lambda;
	}

}
