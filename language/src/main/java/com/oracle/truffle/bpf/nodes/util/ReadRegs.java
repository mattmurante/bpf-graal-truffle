package com.oracle.truffle.bpf.nodes.util;

import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.bpf.nodes.BaseNode;

@NodeInfo(language = "BPF", description = "Retrieves current state of registers")
@NodeField(name = "regsSlot", type = FrameSlot.class)
public abstract class ReadRegs extends BaseNode {
	
	protected abstract FrameSlot getRegsSlot();
	
	@Specialization
	public long[] readRegs(VirtualFrame frame) {
		return (long[]) frame.getValue(getRegsSlot());
	}
	
}
