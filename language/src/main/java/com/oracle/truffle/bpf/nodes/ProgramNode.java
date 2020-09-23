package com.oracle.truffle.bpf.nodes;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.bpf.BPFLanguage;
import com.oracle.truffle.bpf.nodes.util.Memory;

//Primarily drawn from SequenceNode in Stefan Marr's Graal & Truffle blogpost

@NodeInfo(language = "BPF", description = "The root node for BPF programs")
public class ProgramNode extends RootNode {
	
	@Children private final InstructionNode[] insts;
	private final byte[] program;
	private final BPFLanguage language;
	
	public ProgramNode(BPFLanguage language, FrameDescriptor desc, InstructionNode[] insts, byte[] program) {
		super(language, desc);
		this.insts = insts;
		this.program = program;
		this.language = language;
		storeProgram();
	}

	public void storeProgram() {
		//Storing program in memory
		try {
			Memory memory = language.getMemory();
			memory.addRegion(0, program.length);
			memory.setByteArray(0, program);
		} catch (Exception e) {
			System.err.println("Program could not be allocated successfully");
		}
	}
	
	// Executes program by running through each statement in sequence
	@ExplodeLoop
	public Object execute(VirtualFrame frame) {
		long result = 0;
		int pc = 0;
		boolean running = true;
		// Executing each instruction node
		while (running) {
			try {
				running = insts[pc].executeBoolean(frame);
			} catch (UnexpectedResultException e) {
				System.err.println("Expected a boolean indicating the running status of the program after executing the instruction");
			}
			pc = language.getPc();
		}
		// Output register 0's contents as result
		result = language.getRegisters()[0];
		return result;
	}
}
