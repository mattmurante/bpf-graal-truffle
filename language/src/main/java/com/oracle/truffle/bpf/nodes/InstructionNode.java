package com.oracle.truffle.bpf.nodes;

import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.bpf.nodes.util.EBPFOpcodes;

//Executes different instructions based on what opcode is given

@NodeInfo(language = "BPF", description = "Base node for each instruction")
@ImportStatic(value = EBPFOpcodes.class)
@NodeField(name = "opcode", type = byte.class)
@NodeField(name = "srcReg", type = byte.class)
@NodeField(name = "destReg", type = byte.class)
@NodeField(name = "offset", type = short.class)
@NodeField(name = "imm", type = int.class)
public abstract class InstructionNode extends BaseNode {
	
	public abstract byte getOpcode();
	public abstract byte getSrcReg();
	public abstract byte getDestReg();
	public abstract short getOffset();
	public abstract int getImm();
	
	protected final long LOWER_BITS = 0xffffffffL;

	/*
	 * Instructions are divided into three main categories, defined by the three
	 * methods in each subclass: - Memory operations: load/store at addresses given by register
	 * values and offset - Register operations: arithmetic on register
	 * values/immediate - Jump operations: changing program counter based on
	 * register values/immediate Each instruction node is specialized at runtime
	 * based on its opcode. The chosen specialization function will carry out an
	 * instruction by executing one of the three methods, and providing the actual
	 * task to perform via lambda expression. The only instructions that do not
	 * follow this "framework" are the jump and exit instructions, as these only use
	 * the program counter - it would be wasteful to use one of the three subclass
	 * methods for those instructions.
	 */
	
	// TODO: How to handle fn calls?
	
}
