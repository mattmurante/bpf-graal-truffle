package com.oracle.truffle.bpf.nodes.util;

public class FunctionList {
	
	private final int MAX_FNS = 64;
	private int currIndex = 0;
	
	public interface Function {
		long execute(long reg1, long reg2, long reg3, long reg4, long reg5);
	}
	
	private Function[] functions = new Function[MAX_FNS];
	
	public boolean addFunction(Function function) {
		if (currIndex >= MAX_FNS) {
			return false;
		}
		functions[currIndex] = function;
		currIndex++;
		return true;
	}
	
	public long execute(int functionId, long reg1, long reg2, long reg3, long reg4, long reg5) {
		return functions[functionId].execute(reg1, reg2, reg3, reg4, reg5);
	}
	
}
