package com.oracle.truffle.bpf.nodes.other;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.LoopNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.RepeatingNode;
import com.oracle.truffle.bpf.nodes.InstructionNode;

@NodeInfo(language = "BPF", description = "Vector Addition operation for Testing")
public abstract class VectorInst extends InstructionNode {
	
	@Child private LoopNode loop;
	
	public VectorInst(byte opcode, byte srcReg, byte destReg, short offset, int imm) {
		super(opcode, srcReg, destReg, offset, imm);
		loop = Truffle.getRuntime().createLoopNode(new RepeatNode());
	}

	@Specialization
	public boolean operation(VirtualFrame frame) {
		loop.execute(frame);
		return true;
	}
	
	//Adds 1 to each element of an array of 512 integers
	private static class RepeatNode extends Node implements RepeatingNode {
		private int i;
		private int[] elements;
		
		public RepeatNode() {
			i = 0;
			elements = new int[512];
		}
		
		@Override
		public boolean executeRepeating(VirtualFrame frame) {
			if (i < elements.length) {
				elements[i]++;
				i++;
				return true;
			}
			return false;
		}
		
	}
	
}
