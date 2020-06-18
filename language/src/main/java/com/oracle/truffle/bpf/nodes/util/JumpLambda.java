package com.oracle.truffle.bpf.nodes.util;

//Interface that provides an abstract lambda expression that can carry out a jump instruction
public interface JumpLambda {

	int function(long[] regs, int pc);

}
