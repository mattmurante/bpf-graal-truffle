package BPF.Graal;

import com.oracle.truffle.api.frame.VirtualFrame;

public class RegisterNode extends BaseNode {
	
	private final byte reg;
	
	public RegisterNode(byte reg) {
		this.reg = reg;
	}
	
	public byte executeByte(VirtualFrame frame) {
		return reg;
	}
	
	public Object executeGeneric(VirtualFrame frame) {
		return reg;
	}
	
}
