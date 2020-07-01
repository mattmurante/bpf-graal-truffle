package com.oracle.truffle.bpf.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.bpf.nodes.util.Memory;
import com.oracle.truffle.bpf.nodes.util.TypesGen;

@NodeInfo(language = "BPF", description = "The abstract base node for all BPF nodes")
public abstract class BaseNode extends Node {

	public abstract Object executeGeneric(VirtualFrame frame);
	
	public boolean executeBoolean(VirtualFrame frame) throws UnexpectedResultException {
		return TypesGen.expectBoolean(executeGeneric(frame));
	}
	
	public int executeInteger(VirtualFrame frame) throws UnexpectedResultException {
		return TypesGen.expectInteger(executeGeneric(frame));
	}
	
	public long[] executeLongArray(VirtualFrame frame) throws UnexpectedResultException {
		return TypesGen.expectLongArray(executeGeneric(frame));
	}
	
	public Memory executeMemory(VirtualFrame frame) throws UnexpectedResultException {
		return TypesGen.expectMemory(executeGeneric(frame));
	}
	
}
