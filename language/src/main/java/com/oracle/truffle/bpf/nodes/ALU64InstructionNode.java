package com.oracle.truffle.bpf.nodes;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.bpf.nodes.util.*;

@NodeInfo(language = "BPF", description = "Specialized instruction node to perform ALU64 ops")
public abstract class ALU64InstructionNode extends ALUInstructionNode {
	
	// Specializations for instruction nodes based on opcode
	
	@Specialization(guards = "opcode == EBPF_OP_MOV64_IMM")
	@TruffleBoundary
	public RegLambda doMOV64_IMM() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] = getImm();
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_MOV64_REG")
	@TruffleBoundary
	public RegLambda doMOV64_REG() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] = regs[getSrcReg()];
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_LSH64_IMM")
	@TruffleBoundary
	public RegLambda doLSH64_IMM() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] <<= getImm();
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_LSH64_REG")
	@TruffleBoundary
	public RegLambda doLSH64_REG() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] <<= regs[getSrcReg()];
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_RSH64_IMM")
	@TruffleBoundary
	public RegLambda doRSH64_IMM() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] >>>= getImm();
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_RSH64_REG")
	@TruffleBoundary
	public RegLambda doRSH64_REG() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] >>>= regs[getSrcReg()];
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_ARSH64_IMM")
	@TruffleBoundary
	public RegLambda doARSH64_IMM() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] >>= getImm();
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_ARSH64_REG")
	@TruffleBoundary
	public RegLambda doARSH64_REG() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] >>= regs[getSrcReg()];
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_MUL64_REG")
	@TruffleBoundary
	public RegLambda doMUL64_REG() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] *= regs[getSrcReg()];
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_MUL64_IMM")
	@TruffleBoundary
	public RegLambda doMUL64_IMM() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] *= getImm();
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_NEG64")
	@TruffleBoundary
	public RegLambda doNEG64() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] *= -1;
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_OR64_IMM")
	@TruffleBoundary
	public RegLambda doOR64_IMM() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] |= getImm();
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_OR64_REG")
	@TruffleBoundary
	public RegLambda doOR64_REG() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] |= regs[getSrcReg()];
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_AND64_IMM")
	@TruffleBoundary
	public RegLambda doAND64_IMM() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] &= getImm();
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_AND64_REG")
	@TruffleBoundary
	public RegLambda doAND64_REG() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] &= regs[getSrcReg()];
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_XOR64_IMM")
	@TruffleBoundary
	public RegLambda doXOR64_IMM() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] ^= getImm();
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_XOR64_REG")
	@TruffleBoundary
	public RegLambda doXOR64_REG() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] ^= regs[getSrcReg()];
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_MOD64_IMM")
	@TruffleBoundary
	public RegLambda doMOD64_IMM() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] %= getImm();
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_MOD64_REG")
	@TruffleBoundary
	public RegLambda doMOD64_REG() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] %= regs[getSrcReg()];
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_ADD64_IMM")
	@TruffleBoundary
	public RegLambda doADD64_IMM() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] += getImm();
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_ADD64_REG")
	@TruffleBoundary
	public RegLambda doADD64_REG() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] += regs[getSrcReg()];
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_SUB64_IMM")
	@TruffleBoundary
	public RegLambda doSUB64_IMM() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] -= getImm();
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_SUB64_REG")
	@TruffleBoundary
	public RegLambda doSUB64_REG() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] -= regs[getSrcReg()];
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_DIV64_REG")
	@TruffleBoundary
	public RegLambda doDIV64_REG() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] = Long.divideUnsigned(regs[getDestReg()], regs[getSrcReg()]);
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_DIV64_IMM")
	@TruffleBoundary
	public RegLambda doDIV64_IMM() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] = Long.divideUnsigned(regs[getDestReg()], getImm());
		};
		return lambda;
	}
	
}
