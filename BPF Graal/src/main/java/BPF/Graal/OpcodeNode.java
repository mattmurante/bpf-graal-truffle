package BPF.Graal;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(language = "BPF", description = "Node to encode opcode")
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
