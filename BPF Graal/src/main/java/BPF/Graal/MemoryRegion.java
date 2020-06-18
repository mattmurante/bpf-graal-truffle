package BPF.Graal;

//Inspired from MemoryRegion class in RPython implementation

public class MemoryRegion {

	private final long start;
	private final long end;
	private final byte[] region;

	public MemoryRegion(long start, byte[] region) {
		this.start = start;
		this.end = start + region.length - 1;
		this.region = region;
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

	public byte[] getRegion() {
		return region;
	}

}
