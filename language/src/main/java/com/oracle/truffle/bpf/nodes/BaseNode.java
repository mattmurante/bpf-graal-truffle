package com.oracle.truffle.bpf.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.bpf.nodes.util.JumpLambda;
import com.oracle.truffle.bpf.nodes.util.MemLambda;
import com.oracle.truffle.bpf.nodes.util.RegLambda;
import com.oracle.truffle.bpf.nodes.util.TypesGen;

@NodeInfo(language = "BPF", description = "The abstract base node for all BPF nodes")
public abstract class BaseNode extends Node {

	public abstract Object executeGeneric(VirtualFrame frame);
	
	public boolean executeBoolean(VirtualFrame frame) throws UnexpectedResultException {
		return TypesGen.expectBoolean(executeGeneric(frame));
	}
	
	public JumpLambda executeJumpLambda(VirtualFrame frame) throws UnexpectedResultException {
		return TypesGen.expectJumpLambda(executeGeneric(frame));
	}
	
	public RegLambda executeRegLambda(VirtualFrame frame) throws UnexpectedResultException {
		return TypesGen.expectRegLambda(executeGeneric(frame));
	}
	
	public MemLambda executeMemLambda(VirtualFrame frame) throws UnexpectedResultException {
		return TypesGen.expectMemLambda(executeGeneric(frame));
	}
	
}
