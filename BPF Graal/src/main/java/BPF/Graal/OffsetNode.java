package BPF.Graal;

import com.oracle.truffle.api.frame.VirtualFrame;

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
