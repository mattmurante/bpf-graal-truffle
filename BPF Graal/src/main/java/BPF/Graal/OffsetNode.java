package BPF.Graal;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(language = "BPF", description = "Node to encode offset")
public class OffsetNode extends BaseNode {

	private final short off;

	public OffsetNode(short off) {
		this.off = off;
	}

	public short executeShort(VirtualFrame frame) {
		return off;
	}

	public Object executeGeneric(VirtualFrame frame) {
		return off;
	}

}
