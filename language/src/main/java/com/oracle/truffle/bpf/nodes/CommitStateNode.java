package com.oracle.truffle.bpf.nodes;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotTypeException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.bpf.nodes.util.JumpLambda;
import com.oracle.truffle.bpf.nodes.util.MemLambda;
import com.oracle.truffle.bpf.nodes.util.Memory;
import com.oracle.truffle.bpf.nodes.util.RegLambda;

//Carries out an instruction and commits the resulting program counter, register values, and memory state to the frame

@NodeChild(value = "inst", type = InstructionNode.class)
@NodeInfo(language = "BPF", description = "Special intermediary node to commit changes of an operation")
public abstract class CommitStateNode extends BaseNode {
	
	protected final FrameSlot pcSlot;
	protected final FrameSlot regsSlot;
	protected final FrameSlot memSlot;
	
	public CommitStateNode(FrameSlot pcSlot, FrameSlot regsSlot, FrameSlot memSlot) {
		this.pcSlot = pcSlot;
		this.regsSlot = regsSlot;
		this.memSlot = memSlot;
	}
	
	@Specialization(rewriteOn = FrameSlotTypeException.class)
	public boolean commitJump(VirtualFrame frame, JumpLambda inst) throws FrameSlotTypeException {
		int pc = frame.getInt(pcSlot) + 1;
		long[] regs = (long[]) frame.getValue(regsSlot);
		pc = inst.function(regs, pc);
		frame.setInt(pcSlot, pc);
		return true;
	}
	
	@Specialization(rewriteOn = FrameSlotTypeException.class)
	public boolean commitRegs(VirtualFrame frame, RegLambda inst) throws FrameSlotTypeException {
		int pc = frame.getInt(pcSlot) + 1;
		long[] regs = (long[]) frame.getValue(regsSlot);
		inst.function(regs);
		frame.setInt(pcSlot, pc);
		frame.setObject(regsSlot, regs);
		return true;
	}
	
	@Specialization(rewriteOn = FrameSlotTypeException.class)
	public boolean commitMem(VirtualFrame frame, MemLambda inst) throws FrameSlotTypeException {
		int pc = frame.getInt(pcSlot) + 1;
		long[] regs = (long[]) frame.getValue(regsSlot);
		Memory mem = (Memory) frame.getValue(memSlot);
		inst.function(mem, regs);
		frame.setInt(pcSlot, pc);
		frame.setObject(memSlot, mem);
		return true;
	}
	
	//Should only execute on exit instruction
	@Specialization(rewriteOn = FrameSlotTypeException.class)
	public boolean commitBool(VirtualFrame frame, boolean inst) throws FrameSlotTypeException {
		int pc = frame.getInt(pcSlot) + 1;
		frame.setInt(pcSlot, pc);
		return inst;
	}
	
}
