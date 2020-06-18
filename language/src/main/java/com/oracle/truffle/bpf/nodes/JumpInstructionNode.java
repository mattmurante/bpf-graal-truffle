package com.oracle.truffle.bpf.nodes;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.bpf.nodes.util.*;

@NodeInfo(language = "BPF", description = "Specialized instruction node to perform jump ops")
public abstract class JumpInstructionNode extends InstructionNode {
	
	// Specializations for instruction nodes based on opcode
	
	@Specialization(guards = "opcode == EBPF_OP_JEQ_IMM")
	@TruffleBoundary
	public JumpLambda doJEQ_IMM() {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (Long.compareUnsigned(regs[getDestReg()], getImm()) == 0)
				pc += getOffset();
			return pc;
		};
		return lambda;
	}

	@Specialization(guards = "opcode == EBPF_OP_JNE_IMM")
	@TruffleBoundary
	public JumpLambda doJNE_IMM() {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (Long.compareUnsigned(regs[getDestReg()], getImm()) != 0)
				pc += getOffset();
			return pc;
		};
		return lambda;
	}

	@Specialization(guards = "opcode == EBPF_OP_JNE_REG")
	@TruffleBoundary
	public JumpLambda doJNE_REG() {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (Long.compareUnsigned(regs[getDestReg()], regs[getSrcReg()]) != 0)
				pc += getOffset();
			return pc;
		};
		return lambda;
	}

	@Specialization(guards = "opcode == EBPF_OP_JEQ_REG")
	@TruffleBoundary
	public JumpLambda doJEQ_REG() {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (Long.compareUnsigned(regs[getDestReg()], regs[getSrcReg()]) == 0)
				pc += getOffset();
			return pc;
		};
		return lambda;
	}

	@Specialization(guards = "opcode == EBPF_OP_JGE_IMM")
	@TruffleBoundary
	public JumpLambda doJGE_IMM() {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (Long.compareUnsigned(regs[getDestReg()], getImm()) >= 0)
				pc += getOffset();
			return pc;
		};
		return lambda;
	}

	@Specialization(guards = "opcode == EBPF_OP_JGE_REG")
	@TruffleBoundary
	public JumpLambda doJGE_REG() {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (Long.compareUnsigned(regs[getDestReg()], regs[getSrcReg()]) >= 0)
				pc += getOffset();
			return pc;
		};
		return lambda;
	}

	@Specialization(guards = "opcode == EBPF_OP_JGT_REG")
	@TruffleBoundary
	public JumpLambda doJGT_REG() {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (Long.compareUnsigned(regs[getDestReg()], regs[getSrcReg()]) > 0)
				pc += getOffset();
			return pc;
		};
		return lambda;
	}

	@Specialization(guards = "opcode == EBPF_OP_JGT_IMM")
	@TruffleBoundary
	public JumpLambda doJGT_IMM() {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (Long.compareUnsigned(regs[getDestReg()], getImm()) > 0)
				pc += getOffset();
			return pc;
		};
		return lambda;
	}

	@Specialization(guards = "opcode == EBPF_OP_JSGT_REG")
	@TruffleBoundary
	public JumpLambda doJSGT_REG() {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (regs[getDestReg()] > regs[getSrcReg()])
				pc += getOffset();
			return pc;
		};
		return lambda;
	}

	@Specialization(guards = "opcode == EBPF_OP_JSGT_IMM")
	@TruffleBoundary
	public JumpLambda doJSGT_IMM() {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (regs[getDestReg()] > getImm())
				pc += getOffset();
			return pc;
		};
		return lambda;
	}

	@Specialization(guards = "opcode == EBPF_OP_JSGE_IMM")
	@TruffleBoundary
	public JumpLambda doJSGE_IMM() {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (regs[getDestReg()] >= getImm())
				pc += getOffset();
			return pc;
		};
		return lambda;
	}

	@Specialization(guards = "opcode == EBPF_OP_JSGE_REG")
	@TruffleBoundary
	public JumpLambda doJSGE_REG() {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (regs[getDestReg()] >= regs[getSrcReg()])
				pc += getOffset();
			return pc;
		};
		return lambda;
	}

	@Specialization(guards = "opcode == EBPF_OP_JLT_IMM")
	@TruffleBoundary
	public JumpLambda doJLT_IMM() {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (Long.compareUnsigned(regs[getDestReg()], getImm()) < 0)
				pc += getOffset();
			return pc;
		};
		return lambda;
	}

	@Specialization(guards = "opcode == EBPF_OP_JLT_REG")
	@TruffleBoundary
	public JumpLambda doJLT_REG() {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (Long.compareUnsigned(regs[getDestReg()], regs[getSrcReg()]) < 0)
				pc += getOffset();
			return pc;
		};
		return lambda;
	}

	@Specialization(guards = "opcode == EBPF_OP_JLE_IMM")
	@TruffleBoundary
	public JumpLambda doJLE_IMM() {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (Long.compareUnsigned(regs[getDestReg()], getImm()) <= 0)
				pc += getOffset();
			return pc;
		};
		return lambda;
	}

	@Specialization(guards = "opcode == EBPF_OP_JLE_REG")
	@TruffleBoundary
	public JumpLambda doJLE_REG() {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (Long.compareUnsigned(regs[getDestReg()], regs[getSrcReg()]) <= 0)
				pc += getOffset();
			return pc;
		};
		return lambda;
	}

	@Specialization(guards = "opcode == EBPF_OP_JSLT_IMM")
	@TruffleBoundary
	public JumpLambda doJSLT_IMM() {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (regs[getDestReg()] < getImm())
				pc += getOffset();
			return pc;
		};
		return lambda;
	}

	@Specialization(guards = "opcode == EBPF_OP_JSLT_REG")
	@TruffleBoundary
	public JumpLambda doJSLT_REG() {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (regs[getDestReg()] < regs[getSrcReg()])
				pc += getOffset();
			return pc;
		};
		return lambda;
	}

	@Specialization(guards = "opcode == EBPF_OP_JSLE_IMM")
	@TruffleBoundary
	public JumpLambda doJSLE_IMM() {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (regs[getDestReg()] <= getImm())
				pc += getOffset();
			return pc;
		};
		return lambda;
	}

	@Specialization(guards = "opcode == EBPF_OP_JSLE_REG")
	@TruffleBoundary
	public JumpLambda doJSLE_REG() {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (regs[getDestReg()] <= regs[getSrcReg()])
				pc += getOffset();
			return pc;
		};
		return lambda;
	}

	@Specialization(guards = "opcode == EBPF_OP_JSET_REG")
	@TruffleBoundary
	public JumpLambda doJSET_REG() {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (Long.compareUnsigned(regs[getDestReg()] & regs[getSrcReg()], 0) > 0)
				pc += getOffset();
			return pc;
		};
		return lambda;
	}

	@Specialization(guards = "opcode == EBPF_OP_JSET_IMM")
	@TruffleBoundary
	public JumpLambda doJSET_IMM() {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (Long.compareUnsigned(regs[getDestReg()] & getImm(), 0) > 0)
				pc += getOffset();
			return pc;
		};
		return lambda;
	}

	@Specialization(guards = "opcode == EBPF_OP_JA")
	@TruffleBoundary
	public JumpLambda doJA() {
		JumpLambda lambda = (long[] regs, int pc) -> {
			return pc + getOffset();
		};
		return lambda;
	}

	@Specialization(guards = "opcode == EBPF_OP_EXIT")
	@TruffleBoundary
	public boolean doEXIT() {
		return false;
	}
	
}
