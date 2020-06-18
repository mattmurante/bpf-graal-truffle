package com.oracle.truffle.bpf.nodes.util;

//Interface that provides an abstract lambda expression that can operate on registers
public interface RegLambda {

	void function(long[] regs);

}
