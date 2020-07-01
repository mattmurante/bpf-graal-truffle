package com.oracle.truffle.bpf.nodes.mem;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.bpf.nodes.ExpressionNode;
import com.oracle.truffle.bpf.nodes.util.Memory;
import com.oracle.truffle.bpf.nodes.util.MemoryRegion;
import com.oracle.truffle.bpf.nodes.util.NotYetImplemented;
import com.oracle.truffle.bpf.nodes.util.ReadMemory;
import com.oracle.truffle.bpf.nodes.util.ReadRegs;

@NodeInfo(language = "BPF", description = "Base node for each memory operation")
@NodeChild(value = "regs", type = ReadRegs.class)
@NodeChild(value = "memory", type = ReadMemory.class)
public abstract class MemExpressionNode extends ExpressionNode {
	
	// Read and write methods from RPython implementation

	// Convenience method to write to memory
	protected void write(Memory memory, long addr, int size, long value) {
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
	protected long read(Memory memory, long addr, int size) {
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
	
}
