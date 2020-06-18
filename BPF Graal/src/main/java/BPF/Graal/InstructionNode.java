package BPF.Graal;

import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.NodeChildren;
import com.oracle.truffle.api.nodes.NodeInfo;

//Executes different instructions based on what opcode is given

@NodeInfo(language = "BPF", description = "Base node for each instruction")
@ImportStatic(value = EBPFOpcodes.class)
@NodeChildren({ @NodeChild(value = "opcode", type = OpcodeNode.class),
		@NodeChild(value = "srcReg", type = RegisterNode.class),
		@NodeChild(value = "destReg", type = RegisterNode.class),
		@NodeChild(value = "offset", type = OffsetNode.class),
		@NodeChild(value = "imm", type = ImmNode.class) })
public abstract class InstructionNode extends BaseNode {

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
