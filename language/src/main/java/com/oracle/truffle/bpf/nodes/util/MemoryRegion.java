package com.oracle.truffle.bpf.nodes.util;

//Inspired from MemoryRegion class in RPython implementation

public class MemoryRegion {
	
	private final long start;
	private final long end;
	private final long realAddr;

	public MemoryRegion(long start, long size, long realAddr) {
		this.start = start;
		this.end = start + size - 1;
		this.realAddr = realAddr;
	}

	public boolean overlaps(MemoryRegion memRegion) {
		if (this.start > memRegion.end)
			return false;
		if (this.end < memRegion.start)
			return false;
		return true;
	}

	public boolean containsAddr(long addr) {
		return (addr >= this.start && addr <= this.end);
	}

	public long getStart() {
		return start;
	}

	public long getEnd() {
		return end;
	}
	
	public long realFromVirtual(long virtualAddr) {
		return realAddr + (virtualAddr-start);
	}
	
}
