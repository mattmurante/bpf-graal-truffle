package BPF.Graal;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotTypeException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(language = "BPF", description = "Specialized instruction node to perform jump ops")
public abstract class JumpInstructionNode extends InstructionNode {
	
	// Method that carries out a jump operation (defined by input lambda)
	public boolean jumpOp(JumpLambda lambda, VirtualFrame frame) throws FrameSlotTypeException {
		// Loading stored program information from frame
		FrameSlot pcSlot = frame.getFrameDescriptor().findFrameSlot("pc");
		FrameSlot regsSlot = frame.getFrameDescriptor().findFrameSlot("regs");
		int pc = frame.getInt(pcSlot) + 1;
		long[] regs = (long[]) frame.getValue(regsSlot);
		// Performing jump instruction and saving program state to frame
		pc = lambda.function(regs, pc);
		frame.setInt(pcSlot, pc);
		return true;
	}
	
	// Specializations for instruction nodes based on opcode
	
	@Specialization(guards = "opcode == EBPF_OP_JEQ_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doJEQ_IMM(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (Long.compareUnsigned(regs[destReg], imm) == 0)
				pc += offset;
			return pc;
		};
		return jumpOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_JNE_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doJNE_IMM(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (Long.compareUnsigned(regs[destReg], imm) != 0)
				pc += offset;
			return pc;
		};
		return jumpOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_JNE_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doJNE_REG(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (Long.compareUnsigned(regs[destReg], regs[srcReg]) != 0)
				pc += offset;
			return pc;
		};
		return jumpOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_JEQ_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doJEQ_REG(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (Long.compareUnsigned(regs[destReg], regs[srcReg]) == 0)
				pc += offset;
			return pc;
		};
		return jumpOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_JGE_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doJGE_IMM(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (Long.compareUnsigned(regs[destReg], imm) >= 0)
				pc += offset;
			return pc;
		};
		return jumpOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_JGE_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doJGE_REG(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (Long.compareUnsigned(regs[destReg], regs[srcReg]) >= 0)
				pc += offset;
			return pc;
		};
		return jumpOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_JGT_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doJGT_REG(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (Long.compareUnsigned(regs[destReg], regs[srcReg]) > 0)
				pc += offset;
			return pc;
		};
		return jumpOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_JGT_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doJGT_IMM(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (Long.compareUnsigned(regs[destReg], imm) > 0)
				pc += offset;
			return pc;
		};
		return jumpOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_JSGT_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doJSGT_REG(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (regs[destReg] > regs[srcReg])
				pc += offset;
			return pc;
		};
		return jumpOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_JSGT_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doJSGT_IMM(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (regs[destReg] > imm)
				pc += offset;
			return pc;
		};
		return jumpOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_JSGE_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doJSGE_IMM(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (regs[destReg] >= imm)
				pc += offset;
			return pc;
		};
		return jumpOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_JSGE_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doJSGE_REG(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (regs[destReg] >= regs[srcReg])
				pc += offset;
			return pc;
		};
		return jumpOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_JLT_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doJLT_IMM(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (Long.compareUnsigned(regs[destReg], imm) < 0)
				pc += offset;
			return pc;
		};
		return jumpOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_JLT_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doJLT_REG(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (Long.compareUnsigned(regs[destReg], regs[srcReg]) < 0)
				pc += offset;
			return pc;
		};
		return jumpOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_JLE_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doJLE_IMM(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (Long.compareUnsigned(regs[destReg], imm) <= 0)
				pc += offset;
			return pc;
		};
		return jumpOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_JLE_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doJLE_REG(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (Long.compareUnsigned(regs[destReg], regs[srcReg]) <= 0)
				pc += offset;
			return pc;
		};
		return jumpOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_JSLT_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doJSLT_IMM(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (regs[destReg] < imm)
				pc += offset;
			return pc;
		};
		return jumpOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_JSLT_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doJSLT_REG(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (regs[destReg] < regs[srcReg])
				pc += offset;
			return pc;
		};
		return jumpOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_JSLE_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doJSLE_IMM(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (regs[destReg] <= imm)
				pc += offset;
			return pc;
		};
		return jumpOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_JSLE_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doJSLE_REG(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (regs[destReg] <= regs[srcReg])
				pc += offset;
			return pc;
		};
		return jumpOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_JSET_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doJSET_REG(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (Long.compareUnsigned(regs[destReg] & regs[srcReg], 0) > 0)
				pc += offset;
			return pc;
		};
		return jumpOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_JSET_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doJSET_IMM(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (Long.compareUnsigned(regs[destReg] & imm, 0) > 0)
				pc += offset;
			return pc;
		};
		return jumpOp(lambda, frame);
	}

	// The bottom two operations only depend on program counter, so they do not call
	// memOp, regOp, or jumpOp

	@Specialization(guards = "opcode == EBPF_OP_JA", rewriteOn = FrameSlotTypeException.class)
	public boolean doJA(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		// Loading stored program information from frame
		FrameSlot pcSlot = frame.getFrameDescriptor().findFrameSlot("pc");
		int pc = frame.getInt(pcSlot) + 1;
		pc += offset;
		// Updating program counter to new value
		frame.setInt(pcSlot, pc);
		return true;
	}

	@Specialization(guards = "opcode == EBPF_OP_EXIT", rewriteOn = FrameSlotTypeException.class)
	public boolean doEXIT(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		// Loading stored program information from frame
		FrameSlot pcSlot = frame.getFrameDescriptor().findFrameSlot("pc");
		int pc = frame.getInt(pcSlot) + 1;
		// Updating program counter to new value (just goes to next instruction)
		frame.setInt(pcSlot, pc);
		return false;
	}
	
}
