package BPF.Graal;

import com.oracle.truffle.api.frame.VirtualFrame;

public class ImmNode extends BaseNode {

	private final int imm;
	
	public ImmNode(int imm) {
		this.imm = imm;
	}
	
	public int executeInteger(VirtualFrame frame) {
		return imm;
	}
	
	public Object executeGeneric(VirtualFrame frame) {
		return imm;
	}
	
	
}
