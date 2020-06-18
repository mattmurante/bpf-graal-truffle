package BPF.Graal;

import java.nio.ByteOrder;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotTypeException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

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
	
	// Method that carries out an operation (defined by input lambda) on registers
	public boolean regOp(RegLambda lambda, VirtualFrame frame) throws FrameSlotTypeException {
		// Loading stored program information from frame
		FrameSlot pcSlot = frame.getFrameDescriptor().findFrameSlot("pc");
		FrameSlot regsSlot = frame.getFrameDescriptor().findFrameSlot("regs");
		int pc = frame.getInt(pcSlot) + 1;
		long[] regs = (long[]) frame.getValue(regsSlot);
		// Performing operation on registers and saving program state to frame
		lambda.function(regs);
		frame.setInt(pcSlot, pc);
		frame.setObject(regsSlot, regs);
		return true;
	}
	
	// Specializations for instruction nodes based on opcode
	
	@Specialization(guards = "opcode == EBPF_OP_LE", rewriteOn = FrameSlotTypeException.class)
	public boolean doLE(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN)
				return;
			regs[destReg] = byteSwap(regs[destReg], imm);
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_BE", rewriteOn = FrameSlotTypeException.class)
	public boolean doBE(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			if (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN)
				return;
			regs[destReg] = byteSwap(regs[destReg], imm);
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_MOV_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doMOV_IMM(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] = imm;
			regs[destReg] &= LOWER_BITS;
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_MOV64_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doMOV64_IMM(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] = imm;
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_MOV_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doMOV_REG(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] = regs[srcReg];
			regs[destReg] &= LOWER_BITS;
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_MOV64_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doMOV64_REG(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] = regs[srcReg];
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_LSH_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doLSH_IMM(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] <<= imm;
			regs[destReg] &= LOWER_BITS;
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_LSH64_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doLSH64_IMM(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] <<= imm;
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_LSH_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doLSH_REG(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] <<= regs[srcReg];
			regs[destReg] &= LOWER_BITS;
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_LSH64_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doLSH64_REG(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] <<= regs[srcReg];
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_RSH_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doRSH_IMM(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] >>>= imm;
			regs[destReg] &= LOWER_BITS;
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_RSH64_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doRSH64_IMM(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] >>>= imm;
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_RSH_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doRSH_REG(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] >>>= regs[srcReg];
			regs[destReg] &= LOWER_BITS;
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_RSH64_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doRSH64_REG(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] >>>= regs[srcReg];
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_ARSH_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doARSH_IMM(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] >>= imm;
			regs[destReg] &= LOWER_BITS;
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_ARSH64_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doARSH64_IMM(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] >>= imm;
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_ARSH_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doARSH_REG(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] >>= regs[srcReg];
			regs[destReg] &= LOWER_BITS;
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_ARSH64_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doARSH64_REG(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] >>= regs[srcReg];
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_MUL_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doMUL_REG(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] *= regs[srcReg];
			regs[destReg] &= LOWER_BITS;
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_MUL64_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doMUL64_REG(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] *= regs[srcReg];
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_MUL_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doMUL_IMM(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] *= imm;
			regs[destReg] &= LOWER_BITS;
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_MUL64_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doMUL64_IMM(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] *= imm;
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_NEG", rewriteOn = FrameSlotTypeException.class)
	public boolean doNEG(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] *= -1;
			regs[destReg] &= LOWER_BITS;
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_NEG64", rewriteOn = FrameSlotTypeException.class)
	public boolean doNEG64(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] *= -1;
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_OR_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doOR_IMM(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] |= imm;
			regs[destReg] &= LOWER_BITS;
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_OR64_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doOR64_IMM(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] |= imm;
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_OR_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doOR_REG(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] |= regs[srcReg];
			regs[destReg] &= LOWER_BITS;
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_OR64_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doOR64_REG(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] |= regs[srcReg];
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_AND_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doAND_IMM(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] &= imm;
			regs[destReg] &= LOWER_BITS;
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_AND64_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doAND64_IMM(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] &= imm;
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_AND_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doAND_REG(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] &= regs[srcReg];
			regs[destReg] &= LOWER_BITS;
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_AND64_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doAND64_REG(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] &= regs[srcReg];
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_XOR_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doXOR_IMM(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] ^= imm;
			regs[destReg] &= LOWER_BITS;
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_XOR64_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doXOR64_IMM(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] ^= imm;
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_XOR_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doXOR_REG(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] ^= regs[srcReg];
			regs[destReg] &= LOWER_BITS;
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_XOR64_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doXOR64_REG(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] ^= regs[srcReg];
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_MOD_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doMOD_IMM(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] %= imm;
			regs[destReg] &= LOWER_BITS;
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_MOD64_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doMOD64_IMM(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] %= imm;
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_MOD_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doMOD_REG(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] %= regs[srcReg];
			regs[destReg] &= LOWER_BITS;
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_MOD64_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doMOD64_REG(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] %= regs[srcReg];
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_ADD_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doADD_IMM(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] += imm;
			regs[destReg] &= LOWER_BITS;
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_ADD64_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doADD64_IMM(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] += imm;
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_ADD_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doADD_REG(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] += regs[srcReg];
			regs[destReg] &= LOWER_BITS;
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_ADD64_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doADD64_REG(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] += regs[srcReg];
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_SUB_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doSUB_IMM(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] -= imm;
			regs[destReg] &= LOWER_BITS;
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_SUB64_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doSUB64_IMM(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] -= imm;
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_SUB_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doSUB_REG(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] -= regs[srcReg];
			regs[destReg] &= LOWER_BITS;
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_SUB64_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doSUB64_REG(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] -= regs[srcReg];
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_DIV_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doDIV_REG(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] = Long.divideUnsigned(regs[destReg], regs[srcReg]);
			regs[destReg] &= LOWER_BITS;
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_DIV64_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doDIV64_REG(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] = Long.divideUnsigned(regs[destReg], regs[srcReg]);
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_DIV_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doDIV_IMM(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] = Long.divideUnsigned(regs[destReg], imm);
			regs[destReg] &= LOWER_BITS;
		};
		return regOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_DIV64_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doDIV64_IMM(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] = Long.divideUnsigned(regs[destReg], imm);
		};
		return regOp(lambda, frame);
	}
	
}
