package BPF.Graal;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotTypeException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(language = "BPF", description = "Specialized instruction node to perform memory ops")
public abstract class MemInstructionNode extends InstructionNode {
	
	// Read and write methods from RPython implementation

	// Convenience method to write to memory
	public void write(Memory memory, long addr, int size, long value) {
		// Load the memory region
		MemoryRegion r = null;
		try {
			r = memory.getRegion(addr);
		} catch (Exception e) {
			System.err.println("No memory region corresponding to this address");
		}
		// Ensure data size is valid
		if (!(size == 1 || size == 2 || size == 4 || size == 8)) {
			throw new NotYetImplemented();
		}
		// Check alignment
		assert (addr & (size - 1)) == 0 : "Value to write not aligned to address";
		// Write to memory
		for (long index = (addr - r.getStart()); index < (addr - r.getStart()) + size; index++) {
			r.getRegion()[(int) index] = (byte) (value & 0xff);
			value >>>= 8;
		}
	}

	// Convenience method to read from memory
	public long read(Memory memory, long addr, int size) {
		// Load the memory region
		MemoryRegion r = null;
		try {
			r = memory.getRegion(addr);
		} catch (Exception e) {
			System.err.println("No memory region corresponding to this address");
		}
		// Ensure data size is valid
		if (!(size == 1 || size == 2 || size == 4 || size == 8)) {
			throw new NotYetImplemented();
		}
		// Check alignment
		assert (addr & (size - 1)) == 0 : "Value to write not aligned to address";
		// Read from memory
		long value = 0;
		int counter = 0;
		for (long index = (addr - r.getStart()); index < (addr - r.getStart()) + size; index++) {
			value |= Byte.toUnsignedLong(r.getRegion()[(int) index]) << counter;
			counter += 8;
		}
		return value;
	}
	
	// Method that carries out an operation (defined by input lambda) on memory
	public boolean memOp(MemLambda lambda, VirtualFrame frame) throws FrameSlotTypeException {
		// Loading stored program information from frame
		FrameSlot pcSlot = frame.getFrameDescriptor().findFrameSlot("pc");
		FrameSlot regsSlot = frame.getFrameDescriptor().findFrameSlot("regs");
		FrameSlot memSlot = frame.getFrameDescriptor().findFrameSlot("mem");
		int pc = frame.getInt(pcSlot) + 1;
		long[] regs = (long[]) frame.getValue(regsSlot);
		Memory memory = (Memory) frame.getValue(memSlot);
		// Performing operation on memory and saving program state to frame
		lambda.function(memory, regs);
		frame.setInt(pcSlot, pc);
		frame.setObject(memSlot, memory);
		return true;
	}
	
	// Specializations for instruction nodes based on opcode
	
	@Specialization(guards = "opcode == EBPF_OP_STXDW", rewriteOn = FrameSlotTypeException.class)
	public boolean doSTXDW(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		MemLambda lambda = (Memory memory, long[] regs) -> write(memory, regs[destReg] + offset, 8, regs[srcReg]);
		return memOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_STXW", rewriteOn = FrameSlotTypeException.class)
	public boolean doSTXW(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		MemLambda lambda = (Memory memory, long[] regs) -> write(memory, regs[destReg] + offset, 4, regs[srcReg]);
		return memOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_STXH", rewriteOn = FrameSlotTypeException.class)
	public boolean doSTXH(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		MemLambda lambda = (Memory memory, long[] regs) -> write(memory, regs[destReg] + offset, 2, regs[srcReg]);
		return memOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_STXB", rewriteOn = FrameSlotTypeException.class)
	public boolean doSTXB(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		MemLambda lambda = (Memory memory, long[] regs) -> write(memory, regs[destReg] + offset, 1, regs[srcReg]);
		return memOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_STDW", rewriteOn = FrameSlotTypeException.class)
	public boolean doSTDW(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		MemLambda lambda = (Memory memory, long[] regs) -> write(memory, regs[destReg] + offset, 8, imm);
		return memOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_STW", rewriteOn = FrameSlotTypeException.class)
	public boolean doSTW(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		MemLambda lambda = (Memory memory, long[] regs) -> write(memory, regs[destReg] + offset, 4, imm);
		return memOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_STH", rewriteOn = FrameSlotTypeException.class)
	public boolean doSTH(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		MemLambda lambda = (Memory memory, long[] regs) -> write(memory, regs[destReg] + offset, 2, imm);
		return memOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_STB", rewriteOn = FrameSlotTypeException.class)
	public boolean doSTB(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		MemLambda lambda = (Memory memory, long[] regs) -> write(memory, regs[destReg] + offset, 1, imm);
		return memOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_LDXDW", rewriteOn = FrameSlotTypeException.class)
	public boolean doLDXDW(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		MemLambda lambda = (Memory memory, long[] regs) -> regs[destReg] = read(memory, regs[srcReg] + offset, 8);
		return memOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_LDXW", rewriteOn = FrameSlotTypeException.class)
	public boolean doLDXW(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		MemLambda lambda = (Memory memory, long[] regs) -> regs[destReg] = read(memory, regs[srcReg] + offset, 4);
		return memOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_LDXH", rewriteOn = FrameSlotTypeException.class)
	public boolean doLDXH(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		MemLambda lambda = (Memory memory, long[] regs) -> regs[destReg] = read(memory, regs[srcReg] + offset, 2);
		return memOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_LDXB", rewriteOn = FrameSlotTypeException.class)
	public boolean doLDXB(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		MemLambda lambda = (Memory memory, long[] regs) -> regs[destReg] = read(memory, regs[srcReg] + offset, 1);
		return memOp(lambda, frame);
	}

	@Specialization(guards = "opcode == EBPF_OP_LDDW", rewriteOn = FrameSlotTypeException.class)
	public boolean doLDDW(VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm)
			throws FrameSlotTypeException {
		MemLambda lambda = (Memory memory, long[] regs) -> regs[destReg] = Integer.toUnsignedLong(imm);
		return memOp(lambda, frame);
	}
	
}
