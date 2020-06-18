package BPF.Graal;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.UnexpectedResultException;

@NodeInfo(language = "BPF", description = "The abstract base node for all BPF nodes")
public abstract class BaseNode extends Node {

	public abstract Object executeGeneric(VirtualFrame frame);

	public byte executeByte(VirtualFrame frame) throws UnexpectedResultException {
		return TypesGen.expectByte(executeGeneric(frame));
	}

	public short executeShort(VirtualFrame frame) throws UnexpectedResultException {
		return TypesGen.expectShort(executeGeneric(frame));
	}

	public int executeInteger(VirtualFrame frame) throws UnexpectedResultException {
		return TypesGen.expectInteger(executeGeneric(frame));
	}

	public boolean executeBoolean(VirtualFrame frame) throws UnexpectedResultException {
		return TypesGen.expectBoolean(executeGeneric(frame));
	}

}
