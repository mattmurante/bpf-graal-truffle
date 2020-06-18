package com.oracle.truffle.bpf.nodes;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.bpf.nodes.util.*;

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
	
	// Specializations for instruction nodes based on opcode
	
	@Specialization(guards = "opcode == EBPF_OP_STXDW")
	@TruffleBoundary
	public MemLambda doSTXDW() {
		MemLambda lambda = (Memory memory, long[] regs) -> write(memory, regs[getDestReg()] + getOffset(), 8, regs[getSrcReg()]);
		return lambda;
	}

	@Specialization(guards = "opcode == EBPF_OP_STXW")
	@TruffleBoundary
	public MemLambda doSTXW() {
		MemLambda lambda = (Memory memory, long[] regs) -> write(memory, regs[getDestReg()] + getOffset(), 4, regs[getSrcReg()]);
		return lambda;
	}

	@Specialization(guards = "opcode == EBPF_OP_STXH")
	@TruffleBoundary
	public MemLambda doSTXH() {
		MemLambda lambda = (Memory memory, long[] regs) -> write(memory, regs[getDestReg()] + getOffset(), 2, regs[getSrcReg()]);
		return lambda;
	}

	@Specialization(guards = "opcode == EBPF_OP_STXB")
	@TruffleBoundary
	public MemLambda doSTXB() {
		MemLambda lambda = (Memory memory, long[] regs) -> write(memory, regs[getDestReg()] + getOffset(), 1, regs[getSrcReg()]);
		return lambda;
	}

	@Specialization(guards = "opcode == EBPF_OP_STDW")
	@TruffleBoundary
	public MemLambda doSTDW() {
		MemLambda lambda = (Memory memory, long[] regs) -> write(memory, regs[getDestReg()] + getOffset(), 8, getImm());
		return lambda;
	}

	@Specialization(guards = "opcode == EBPF_OP_STW")
	@TruffleBoundary
	public MemLambda doSTW() {
		MemLambda lambda = (Memory memory, long[] regs) -> write(memory, regs[getDestReg()] + getOffset(), 4, getImm());
		return lambda;
	}

	@Specialization(guards = "opcode == EBPF_OP_STH")
	@TruffleBoundary
	public MemLambda doSTH() {
		MemLambda lambda = (Memory memory, long[] regs) -> write(memory, regs[getDestReg()] + getOffset(), 2, getImm());
		return lambda;
	}

	@Specialization(guards = "opcode == EBPF_OP_STB")
	@TruffleBoundary
	public MemLambda doSTB() {
		MemLambda lambda = (Memory memory, long[] regs) -> write(memory, regs[getDestReg()] + getOffset(), 1, getImm());
		return lambda;
	}

	@Specialization(guards = "opcode == EBPF_OP_LDXDW")
	@TruffleBoundary
	public MemLambda doLDXDW() {
		MemLambda lambda = (Memory memory, long[] regs) -> regs[getDestReg()] = read(memory, regs[getSrcReg()] + getOffset(), 8);
		return lambda;
	}

	@Specialization(guards = "opcode == EBPF_OP_LDXW")
	@TruffleBoundary
	public MemLambda doLDXW() {
		MemLambda lambda = (Memory memory, long[] regs) -> regs[getDestReg()] = read(memory, regs[getSrcReg()] + getOffset(), 4);
		return lambda;
	}

	@Specialization(guards = "opcode == EBPF_OP_LDXH")
	@TruffleBoundary
	public MemLambda doLDXH() {
		MemLambda lambda = (Memory memory, long[] regs) -> regs[getDestReg()] = read(memory, regs[getSrcReg()] + getOffset(), 2);
		return lambda;
	}

	@Specialization(guards = "opcode == EBPF_OP_LDXB")
	@TruffleBoundary
	public MemLambda doLDXB() {
		MemLambda lambda = (Memory memory, long[] regs) -> regs[getDestReg()] = read(memory, regs[getSrcReg()] + getOffset(), 1);
		return lambda;
	}

	@Specialization(guards = "opcode == EBPF_OP_LDDW")
	@TruffleBoundary
	public MemLambda doLDDW() {
		MemLambda lambda = (Memory memory, long[] regs) -> regs[getDestReg()] = Integer.toUnsignedLong(getImm());
		return lambda;
	}
	
}
