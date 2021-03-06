package com.oracle.truffle.bpf.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.bpf.nodes.util.TypesGen;

@NodeInfo(language = "BPF", description = "The abstract base node for all BPF nodes")
public abstract class BaseNode extends Node {

	public abstract Object executeGeneric(VirtualFrame frame);
	
	public boolean executeBoolean(VirtualFrame frame) throws UnexpectedResultException {
		return TypesGen.expectBoolean(executeGeneric(frame));
	}
}
