package com.oracle.truffle.bpf.nodes.util;

import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotTypeException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.bpf.nodes.BaseNode;

@NodeInfo(language = "BPF", description = "Retrieves current state of program counter")
@NodeField(name = "pcSlot", type = FrameSlot.class)
public abstract class ReadPC extends BaseNode {
	
	protected abstract FrameSlot getPcSlot();
	
	@Specialization(rewriteOn = FrameSlotTypeException.class)
	public int readPc(VirtualFrame frame) throws FrameSlotTypeException {
		return frame.getInt(getPcSlot()) + 1;
	}
	
}
