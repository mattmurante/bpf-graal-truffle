package com.oracle.truffle.bpf.nodes;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotTypeException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.bpf.BPFLanguage;
import com.oracle.truffle.bpf.nodes.util.Memory;
import com.oracle.truffle.bpf.nodes.util.MemoryRegion;

//Primarily drawn from SequenceNode in Stefan Marr's Graal & Truffle blogpost

@NodeInfo(language = "BPF", description = "The root node for BPF programs")
public class ProgramNode extends RootNode {
	
	private final int STACK_SIZE = 512;
	
	@Children private final InstructionNode[] insts;
	private final FrameSlot pcSlot;
	private final FrameSlot regsSlot;
	private final FrameSlot memSlot;
	private final byte[] program;
	
	public ProgramNode(BPFLanguage language, FrameDescriptor desc, FrameSlot pcSlot, FrameSlot regsSlot, FrameSlot memSlot, InstructionNode[] insts, byte[] program) {
		super(language, desc);
		this.insts = insts;
		this.program = program;
		this.pcSlot = pcSlot;
		this.regsSlot = regsSlot;
		this.memSlot = memSlot;
	}

	public void initFrame(VirtualFrame frame) {
		// Initializing frame elements for each slot
		long[] registers = new long[11];
		registers[10] = 16384 * 8;
		Memory memory = new Memory();
		try {
			memory.addRegion("Program", new MemoryRegion(0, program));
			memory.addRegion("Stack", new MemoryRegion((16384 * 8 - STACK_SIZE), new byte[STACK_SIZE]));
		} catch (Exception e) {
			System.out.println("Memory regions could not be added to memory map successfully");
		}
		frame.setInt(pcSlot, 0);
		frame.setObject(regsSlot, registers);
		frame.setObject(memSlot, memory);
	}

	// Executes program by running through each statement in sequence
	@ExplodeLoop
	public Object execute(VirtualFrame frame) {
		initFrame(frame);
		// Executing each instruction node
		int pc = 0;
		boolean running = true;
		long result = 0;
		while (running) {
			try {
				running = insts[pc].executeBoolean(frame);
				pc = frame.getInt(pcSlot);
			} catch (UnexpectedResultException | FrameSlotTypeException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
		// Output register 0's contents as result
		long[] regs = (long[]) frame.getValue(regsSlot);
		result = regs[0];
		return result;
	}
}
