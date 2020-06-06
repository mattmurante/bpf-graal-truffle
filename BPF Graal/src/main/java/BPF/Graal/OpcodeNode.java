package BPF.Graal;

import com.oracle.truffle.api.frame.VirtualFrame;

public class OpcodeNode extends BaseNode {

	private final byte op;
	
	public OpcodeNode(byte op) {
		this.op = op;
	}
	
	public byte executeByte(VirtualFrame frame) {
		return op;
	}
	
	public Object executeGeneric(VirtualFrame frame) {
		return op;
	}
	
}
