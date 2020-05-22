package BPF.Graal;

import java.util.HashMap;

//Implementation of memory_map from RPython VM

public class Memory {
	//Does this really need to be a hashmap? Do we ever use names?
	private final HashMap<String, MemoryRegion> mem = new HashMap<String, MemoryRegion>();
	
	public void addRegion(String name, MemoryRegion memRegion) throws Exception {
		for (MemoryRegion m : mem.values()) {
			if (m.overlaps(memRegion)) throw new Exception("Cannot add overlapping memory region");
		}
		mem.put(name, memRegion);
	}
	
	public MemoryRegion getRegion(long address) throws Exception {
		for (MemoryRegion m : mem.values()) {
			if (m.containsAddr(address)){
				return m;
			}
		}
		throw new Exception("No memory region for specified address");
	}
	
	public HashMap<String, MemoryRegion> getMemory() {
		return mem;
	}
	
}
