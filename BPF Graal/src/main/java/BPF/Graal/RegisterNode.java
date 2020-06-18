package BPF.Graal;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(language = "BPF", description = "Node to encode register")
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
