package com.oracle.truffle.bpf.nodes.util;

import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.bpf.nodes.BaseNode;

@NodeInfo(language = "BPF", description = "Retrieves current state of memory")
@NodeField(name = "memSlot", type = FrameSlot.class)
public abstract class ReadMemory extends BaseNode {
	
	protected abstract FrameSlot getMemSlot();
	
	@Specialization
	public Memory readMem(VirtualFrame frame) {
		return (Memory) frame.getValue(getMemSlot());
	}
	
}
