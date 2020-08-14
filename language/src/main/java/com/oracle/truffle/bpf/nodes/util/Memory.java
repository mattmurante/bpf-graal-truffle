package com.oracle.truffle.bpf.nodes.util;

//Implementation of memory_map from RPython VM

public class Memory {
	
	private static final int MAX_MEMORY_PARTITIONS = 3;
	
	private final MemoryRegion[] mem = new MemoryRegion[MAX_MEMORY_PARTITIONS];
	private int size = 0;

	public void addRegion(MemoryRegion memRegion) throws Exception {
		if (size >= MAX_MEMORY_PARTITIONS) {
			throw new Exception("Cannot exceed maximum number of memory partitions");
		}
		for (int i = 0; i < size; i++) {
			if (mem[i].overlaps(memRegion))
				throw new Exception("Cannot add overlapping memory region");
		}
		mem[size++] = memRegion;
	}

	public MemoryRegion getRegion(long address) throws Exception {
		for (int i = 0; i < size; i++) {
			if (mem[i].containsAddr(address)) {
				return mem[i];
			}
		}
		throw new Exception("No memory region for specified address");
	}

	public MemoryRegion[] getMemory() {
		return mem;
	}

}
