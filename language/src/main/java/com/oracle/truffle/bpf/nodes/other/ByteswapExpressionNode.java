package com.oracle.truffle.bpf.nodes.other;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.bpf.nodes.ExpressionNode;
import com.oracle.truffle.bpf.nodes.util.NotYetImplemented;
import com.oracle.truffle.bpf.nodes.util.ReadRegs;

@NodeInfo(language = "BPF", description = "Base node for each byteswap operation")
@NodeChild(value = "regs", type = ReadRegs.class)
public abstract class ByteswapExpressionNode extends ExpressionNode {
	
	// Convenience method to swap order of bytes in a register
	public long byteSwap(long regVal, int imm) {
		// Ensure byte swap is for a valid size
		if (!(imm == 16 || imm == 32 || imm == 64)) {
			throw new NotYetImplemented();
		}
		// Swap bytes
		long value = 0;
		for (int i = imm - 8; i >= 0; i -= 8) {
			value |= (0xffL & regVal) << i;
			regVal >>>= 8;
		}
		return value;
	}
	
}
