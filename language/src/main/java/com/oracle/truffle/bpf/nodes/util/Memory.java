package com.oracle.truffle.bpf.nodes.util;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;

import sun.misc.Unsafe;

//Class that acts like a wrapper for Unsafe object methods to allow direct memory management
//Heavily inspired by GraalVM's Sulong LLVM implementation
//May want to add pointer checking, maybe Truffle node pointer class, and methods to provide debug info

@SuppressWarnings("restriction")
public final class Memory {
	
	private static final Unsafe unsafe = getUnsafe();
	private final ArrayList<MemoryRegion> addresses = new ArrayList<MemoryRegion>();
	
	public void addRegion(long start, long size) {
		addresses.add(new MemoryRegion(start, size, allocate(size)));
	}
	
	//Bad practice, should fix
	public MemoryRegion getRegion(long virtualAddr) {
		MemoryRegion curr;
		for (int i = 0; i < addresses.size(); i++) {
			curr = addresses.get(i);
			if (curr.containsAddr(virtualAddr)) {
				return curr;
			}
		}
		return null;
	}
	
	public long realFromRegion(long virtualAddr) {
		return getRegion(virtualAddr).realFromVirtual(virtualAddr);
	}
	
	//Use reflection to retrieve unsafe object for direct writing to memory
	private static Unsafe getUnsafe() {
		Field theUnsafe;
		try {
			theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
			theUnsafe.setAccessible(true);
			return (Unsafe) theUnsafe.get(null);
		} catch (Exception e) {
			throw new AssertionError();
		}
	}
	
	@TruffleBoundary
	public void memset(long address, long size, byte value) {
		unsafe.setMemory(address, size, value);
	}
	
	@TruffleBoundary
	public void memcopy(long sourceAddress, long targetAddress, long size) {
		unsafe.copyMemory(sourceAddress, targetAddress, size);
	}
	
	@TruffleBoundary
	public void free(long address) {
		unsafe.freeMemory(address);
	}
	
	@TruffleBoundary
	public long allocate(long size) {
		return unsafe.allocateMemory(size);
	}
	
	public byte getByte(long address) {
		address = realFromRegion(address);
		return unsafe.getByte(address);
	}
	
	public short getShort(long address) {
		address = realFromRegion(address);
		return unsafe.getShort(address);
	}
	
	public int getInt(long address) {
		address = realFromRegion(address);
		return unsafe.getInt(address);
	}
	
	public long getLong(long address) {
		address = realFromRegion(address);
		return unsafe.getLong(address);
	}
	
	public void setByte(long address, byte value) {
		address = realFromRegion(address);
		unsafe.putByte(address, value);
	}
	
	public void setShort(long address, short value) {
		address = realFromRegion(address);
		unsafe.putShort(address, value);
	}
	
	public void setInt(long address, int value) {
		address = realFromRegion(address);
		unsafe.putInt(address, value);
	}
	
	public void setLong(long address, long value) {
		address = realFromRegion(address);
		unsafe.putLong(address, value);
	}
	
	public void setByteArray(long address, byte[] values) {
		for (int i = 0; i < values.length; i++) {
			setByte(address, values[i]);
			address += Byte.BYTES;
		}
	}
	
}
