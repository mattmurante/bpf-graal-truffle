package com.oracle.truffle.bpf.nodes;

import java.nio.ByteOrder;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.bpf.nodes.util.*;

@NodeInfo(language = "BPF", description = "Specialized instruction node to perform ALU ops")
public abstract class ALUInstructionNode extends InstructionNode {
	
	// Convenience method to swap order of bytes in a register
	public long byteSwap(long regVal, int imm) {
		// Ensure byte swap is for a valid size
		if (!(imm == 16 || imm == 32 || imm == 64)) {
			throw new NotYetImplemented();
		}
		// Swap bytes
		long value = 0;
		for (int i = imm - 8; i >= 0; i -= 8) {
			value |= (0xffL & regVal) << i;
			regVal >>>= 8;
		}
		return value;
	}
	
	// Specializations for instruction nodes based on opcode
	
	@Specialization(guards = "opcode == EBPF_OP_LE")
	@TruffleBoundary
	public RegLambda doLE() {
		RegLambda lambda = (long[] regs) -> {
			if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN)
				return;
			regs[getDestReg()] = byteSwap(regs[getDestReg()], getImm());
		};
		return lambda;
	}

	@Specialization(guards = "opcode == EBPF_OP_BE")
	@TruffleBoundary
	public RegLambda doBE() {
		RegLambda lambda = (long[] regs) -> {
			if (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN)
				return;
			regs[getDestReg()] = byteSwap(regs[getDestReg()], getImm());
		};
		return lambda;
	}

	@Specialization(guards = "opcode == EBPF_OP_MOV_IMM")
	@TruffleBoundary
	public RegLambda doMOV_IMM() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] = getImm();
			regs[getDestReg()] &= LOWER_BITS;
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_MOV_REG")
	@TruffleBoundary
	public RegLambda doMOV_REG() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] = regs[getSrcReg()];
			regs[getDestReg()] &= LOWER_BITS;
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_LSH_IMM")
	@TruffleBoundary
	public RegLambda doLSH_IMM() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] <<= getImm();
			regs[getDestReg()] &= LOWER_BITS;
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_LSH_REG")
	@TruffleBoundary
	public RegLambda doLSH_REG() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] <<= regs[getSrcReg()];
			regs[getDestReg()] &= LOWER_BITS;
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_RSH_IMM")
	@TruffleBoundary
	public RegLambda doRSH_IMM() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] >>>= getImm();
			regs[getDestReg()] &= LOWER_BITS;
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_RSH_REG")
	@TruffleBoundary
	public RegLambda doRSH_REG() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] >>>= regs[getSrcReg()];
			regs[getDestReg()] &= LOWER_BITS;
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_ARSH_IMM")
	@TruffleBoundary
	public RegLambda doARSH_IMM() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] >>= getImm();
			regs[getDestReg()] &= LOWER_BITS;
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_ARSH_REG")
	@TruffleBoundary
	public RegLambda doARSH_REG() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] >>= regs[getSrcReg()];
			regs[getDestReg()] &= LOWER_BITS;
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_MUL_REG")
	@TruffleBoundary
	public RegLambda doMUL_REG() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] *= regs[getSrcReg()];
			regs[getDestReg()] &= LOWER_BITS;
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_MUL_IMM")
	@TruffleBoundary
	public RegLambda doMUL_IMM() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] *= getImm();
			regs[getDestReg()] &= LOWER_BITS;
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_NEG")
	@TruffleBoundary
	public RegLambda doNEG() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] *= -1;
			regs[getDestReg()] &= LOWER_BITS;
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_OR_IMM")
	@TruffleBoundary
	public RegLambda doOR_IMM() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] |= getImm();
			regs[getDestReg()] &= LOWER_BITS;
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_OR_REG")
	@TruffleBoundary
	public RegLambda doOR_REG() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] |= regs[getSrcReg()];
			regs[getDestReg()] &= LOWER_BITS;
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_AND_IMM")
	@TruffleBoundary
	public RegLambda doAND_IMM() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] &= getImm();
			regs[getDestReg()] &= LOWER_BITS;
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_AND_REG")
	@TruffleBoundary
	public RegLambda doAND_REG() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] &= regs[getSrcReg()];
			regs[getDestReg()] &= LOWER_BITS;
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_XOR_IMM")
	@TruffleBoundary
	public RegLambda doXOR_IMM() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] ^= getImm();
			regs[getDestReg()] &= LOWER_BITS;
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_XOR_REG")
	@TruffleBoundary
	public RegLambda doXOR_REG() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] ^= regs[getSrcReg()];
			regs[getDestReg()] &= LOWER_BITS;
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_MOD_IMM")
	@TruffleBoundary
	public RegLambda doMOD_IMM() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] %= getImm();
			regs[getDestReg()] &= LOWER_BITS;
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_MOD_REG")
	@TruffleBoundary
	public RegLambda doMOD_REG() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] %= regs[getSrcReg()];
			regs[getDestReg()] &= LOWER_BITS;
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_ADD_IMM")
	@TruffleBoundary
	public RegLambda doADD_IMM() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] += getImm();
			regs[getDestReg()] &= LOWER_BITS;
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_ADD_REG")
	@TruffleBoundary
	public RegLambda doADD_REG() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] += regs[getSrcReg()];
			regs[getDestReg()] &= LOWER_BITS;
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_SUB_IMM")
	@TruffleBoundary
	public RegLambda doSUB_IMM() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] -= getImm();
			regs[getDestReg()] &= LOWER_BITS;
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_SUB_REG")
	@TruffleBoundary
	public RegLambda doSUB_REG() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] -= regs[getSrcReg()];
			regs[getDestReg()] &= LOWER_BITS;
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_DIV_REG")
	@TruffleBoundary
	public RegLambda doDIV_REG() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] = Long.divideUnsigned(regs[getDestReg()], regs[getSrcReg()]);
			regs[getDestReg()] &= LOWER_BITS;
		};
		return lambda;
	}
	
	@Specialization(guards = "opcode == EBPF_OP_DIV_IMM")
	@TruffleBoundary
	public RegLambda doDIV_IMM() {
		RegLambda lambda = (long[] regs) -> {
			regs[getDestReg()] = Long.divideUnsigned(regs[getDestReg()], getImm());
			regs[getDestReg()] &= LOWER_BITS;
		};
		return lambda;
	}
	
}
