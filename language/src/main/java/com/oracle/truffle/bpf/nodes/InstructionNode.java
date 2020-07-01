package com.oracle.truffle.bpf.nodes;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotTypeException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.bpf.nodes.util.Memory;

@NodeInfo(language = "BPF", description = "Base node for all bpf instructions")
@NodeChild(value = "expr", type = ExpressionNode.class)
@NodeField(name = "pcSlot", type = FrameSlot.class)
@NodeField(name = "regsSlot", type = FrameSlot.class)
@NodeField(name = "memSlot", type = FrameSlot.class)
public abstract class InstructionNode extends BaseNode {
	
	protected abstract FrameSlot getPcSlot();
	protected abstract FrameSlot getRegsSlot();
	protected abstract FrameSlot getMemSlot();
	
	//May want to replace with a readpc child for clarity?
	public void incrementPc(VirtualFrame frame) throws FrameSlotTypeException {
		int pc = frame.getInt(getPcSlot()) + 1;
		frame.setInt(getPcSlot(), pc);
	}
	
	@Specialization
	public boolean doPcInstruction(VirtualFrame frame, int expr) {
		frame.setInt(getPcSlot(), expr);
		return true;
	}
	
	@Specialization(rewriteOn = FrameSlotTypeException.class)
	public boolean doRegsInstruction(VirtualFrame frame, long[] expr) throws FrameSlotTypeException {
		incrementPc(frame);
		frame.setObject(getRegsSlot(), expr);
		return true;
	}
	
	@Specialization(rewriteOn = FrameSlotTypeException.class)
	public boolean doMemInstruction(VirtualFrame frame, Memory expr) throws FrameSlotTypeException {
		incrementPc(frame);
		frame.setObject(getMemSlot(), expr);
		return true;
	}
	
	@Specialization(rewriteOn = FrameSlotTypeException.class)
	public boolean doExitInstruction(VirtualFrame frame, boolean expr) throws FrameSlotTypeException {
		incrementPc(frame);
		assert expr == false : "Exit instruction not terminating program or non-exit instruction signaling termination of program";
		return false;
	}
	
}
