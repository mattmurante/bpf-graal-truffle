package deprecated;

//Class to help convert between addresses

public class Pointer {
	
	private final long address;
	private final long offset;
	
	public Pointer(long address, long offset) {
		this.address = address;
		this.offset = offset;
	}
	
	public long getPtr(long addr) {
		return (offset + (addr-address));
	}
	
}
