package BPF.Graal;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotTypeException;
import com.oracle.truffle.api.frame.VirtualFrame;

//Helper instruction node to load double word - maybe not the most elegant solution, but works nonetheless

public abstract class DoubleWordHelperNode extends InstructionNode {
	
	@Specialization(guards = "opcode == EBPF_OP_LDDW", rewriteOn = FrameSlotTypeException.class)
    public boolean doLDDW(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] |= Integer.toUnsignedLong(imm) << 32;
		};
		regOp(lambda, frame);
    	return true;
    }
	
}
