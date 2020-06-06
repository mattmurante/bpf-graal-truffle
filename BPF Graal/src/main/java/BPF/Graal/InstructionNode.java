package BPF.Graal;

import java.nio.ByteOrder;

import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.NodeChildren;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotTypeException;
import com.oracle.truffle.api.frame.VirtualFrame;

//Executes different instructions based on what opcode is given

@ImportStatic(value = EBPFOpcodes.class)
@NodeChildren({
	@NodeChild(value = "opcode", type = OpcodeNode.class),
	@NodeChild(value = "srcReg", type = RegisterNode.class),
	@NodeChild(value = "destReg", type = RegisterNode.class),
	@NodeChild(value = "offset", type = OffsetNode.class),
	@NodeChild(value = "imm", type = ImmNode.class)
})
public abstract class InstructionNode extends BaseNode {
	
	private final long LOWER_BITS = 0xffffffffL;
	
    //Read and write methods from RPython implementation
    
    //Convenience method to write to memory
    public void write(Memory memory, long addr, int size, long value) {
    	//Load the memory region
    	MemoryRegion r = null;
    	try {
			r = memory.getRegion(addr);
		} catch (Exception e) {
			System.err.println("No memory region corresponding to this address");
		}
    	//Ensure data size is valid
    	if (!(size == 1 || size == 2 || size == 4 || size == 8)) {
    		throw new NotYetImplemented();
    	}
    	//Check alignment
    	assert (addr & (size-1)) == 0 : "Value to write not aligned to address";
    	//Write to memory
    	for (long index = (addr-r.getStart()); index < (addr-r.getStart()) + size; index++) {
    		r.getRegion()[(int) index] = (byte) (value & 0xff);
    		value >>>= 8;
    	}
    }
    
    //Convenience method to read from memory
    public long read(Memory memory, long addr, int size) {
    	//Load the memory region
    	MemoryRegion r = null;
    	try {
			r = memory.getRegion(addr);
		} catch (Exception e) {
			System.err.println("No memory region corresponding to this address");
		}
    	//Ensure data size is valid
    	if (!(size == 1 || size == 2 || size == 4 || size == 8)) {
    		throw new NotYetImplemented();
    	}
    	//Check alignment
    	assert (addr & (size-1)) == 0 : "Value to write not aligned to address";
    	//Read from memory
    	long value = 0;
    	int counter = 0;
    	for (long index = (addr-r.getStart()); index < (addr-r.getStart()) + size; index++) {
    		value |= Byte.toUnsignedLong(r.getRegion()[(int) index]) << counter;
    		counter += 8;
    	}
    	return value;
    }
    
    //Convenience method to swap order of bytes in a register
    public long byteSwap(long regVal, int imm) {
    	//Ensure byte swap is for a valid size
		if (!(imm == 16 || imm == 32 || imm == 64)) {
    		throw new NotYetImplemented();
    	}
    	//Swap bytes
    	long value = 0;
    	for (int i = imm - 8; i >= 0; i-=8) {
    		value |= (0xffL & regVal) << i;
    		regVal >>>= 8;
    	}
    	return value;
    }
    
    /*
     * Instructions are divided into three main categories, defined by the three methods below:
     * - Memory operations: load/store at addresses given by register values and offset
     * - Register operations: arithmetic on register values/immediate
     * - Jump operations: changing program counter based on register values/immediate
     * Each instruction node is specialized at runtime based on its opcode. The chosen
     * specialization function will carry out an instruction by executing one of the three
     * methods, and providing the actual task to perform via lambda expression.
     * The only instructions that do not follow this "framework" are the jump and exit instructions,
     * as these only use the program counter - it would be wasteful to use one of the below three
     * methods for those instructions.
     */
    
    //Method that carries out an operation (defined by input lambda) on memory
    public boolean memOp(MemLambda lambda, VirtualFrame frame) throws FrameSlotTypeException {
    	//Loading stored program information from frame
    	FrameSlot pcSlot = frame.getFrameDescriptor().findFrameSlot("pc");
    	FrameSlot regsSlot = frame.getFrameDescriptor().findFrameSlot("regs");
    	FrameSlot memSlot = frame.getFrameDescriptor().findFrameSlot("mem");
    	int pc = frame.getInt(pcSlot) + 1;
    	long[] regs = (long[]) frame.getValue(regsSlot);
    	Memory memory = (Memory) frame.getValue(memSlot);
    	//Performing operation on memory and saving program state to frame
    	lambda.function(memory, regs);
		frame.setInt(pcSlot, pc);
		frame.setObject(memSlot, memory);
		return true;
    }
    
    //Method that carries out an operation (defined by input lambda) on registers
    public boolean regOp(RegLambda lambda, VirtualFrame frame) throws FrameSlotTypeException {
    	//Loading stored program information from frame
    	FrameSlot pcSlot = frame.getFrameDescriptor().findFrameSlot("pc");
    	FrameSlot regsSlot = frame.getFrameDescriptor().findFrameSlot("regs");
    	int pc = frame.getInt(pcSlot) + 1;
    	long[] regs = (long[]) frame.getValue(regsSlot);
    	//Performing operation on registers and saving program state to frame
    	lambda.function(regs);
    	frame.setInt(pcSlot, pc);
    	frame.setObject(regsSlot, regs);
    	return true;
    }
    
    //Method that carries out a jump operation (defined by input lambda)
    public boolean jumpOp(JumpLambda lambda, VirtualFrame frame) throws FrameSlotTypeException {
    	//Loading stored program information from frame
    	FrameSlot pcSlot = frame.getFrameDescriptor().findFrameSlot("pc");
    	FrameSlot regsSlot = frame.getFrameDescriptor().findFrameSlot("regs");
    	int pc = frame.getInt(pcSlot) + 1;
    	long[] regs = (long[]) frame.getValue(regsSlot);
    	//Performing jump instruction and saving program state to frame
    	pc = lambda.function(regs, pc);
    	frame.setInt(pcSlot, pc);
    	return true;
    }
    
	//Specializations for instruction nodes based on opcode
	//TODO: How to handle fn calls?
	
	@Specialization (guards = "opcode == EBPF_OP_STXDW", rewriteOn = FrameSlotTypeException.class)
	public boolean doSTXDW (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		MemLambda lambda = (Memory memory, long[] regs) -> write(memory, regs[destReg] + offset, 8, regs[srcReg]);
		return memOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_STXW", rewriteOn = FrameSlotTypeException.class)
	public boolean doSTXW (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
    	MemLambda lambda = (Memory memory, long[] regs) -> write(memory, regs[destReg] + offset, 4, regs[srcReg]);
    	return memOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_STXH", rewriteOn = FrameSlotTypeException.class)
	public boolean doSTXH (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		MemLambda lambda = (Memory memory, long[] regs) -> write(memory, regs[destReg] + offset, 2, regs[srcReg]);
		return memOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_STXB", rewriteOn = FrameSlotTypeException.class)
	public boolean doSTXB (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
    	MemLambda lambda = (Memory memory, long[] regs) -> write(memory, regs[destReg] + offset, 1, regs[srcReg]);
    	return memOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_STDW", rewriteOn = FrameSlotTypeException.class)
	public boolean doSTDW (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		MemLambda lambda = (Memory memory, long[] regs) -> write(memory, regs[destReg] + offset, 8, imm);
		return memOp(lambda, frame);
	}
    
	@Specialization (guards = "opcode == EBPF_OP_STW", rewriteOn = FrameSlotTypeException.class)
	public boolean doSTW (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		MemLambda lambda = (Memory memory, long[] regs) -> write(memory, regs[destReg] + offset, 4, imm);
    	return memOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_STH", rewriteOn = FrameSlotTypeException.class)
	public boolean doSTH (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		MemLambda lambda = (Memory memory, long[] regs) -> write(memory, regs[destReg] + offset, 2, imm);
		return memOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_STB", rewriteOn = FrameSlotTypeException.class)
	public boolean doSTB (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		MemLambda lambda = (Memory memory, long[] regs) -> write(memory, regs[destReg] + offset, 1, imm);
		return memOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_LDXDW", rewriteOn = FrameSlotTypeException.class)
	public boolean doLDXDW (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		MemLambda lambda = (Memory memory, long[] regs) -> regs[destReg] = read(memory, regs[srcReg] + offset, 8);
		return memOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_LDXW", rewriteOn = FrameSlotTypeException.class)
	public boolean doLDXW (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		MemLambda lambda = (Memory memory, long[] regs) -> regs[destReg] = read(memory, regs[srcReg] + offset, 4);
		return memOp(lambda, frame);
	}

	@Specialization (guards = "opcode == EBPF_OP_LDXH", rewriteOn = FrameSlotTypeException.class)
	public boolean doLDXH (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		MemLambda lambda = (Memory memory, long[] regs) -> regs[destReg] = read(memory, regs[srcReg] + offset, 2);
		return memOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_LDXB", rewriteOn = FrameSlotTypeException.class)
	public boolean doLDXB (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		MemLambda lambda = (Memory memory, long[] regs) -> regs[destReg] = read(memory, regs[srcReg] + offset, 1);
		return memOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_LDDW", rewriteOn = FrameSlotTypeException.class)
	public boolean doLDDW (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		MemLambda lambda = (Memory memory, long[] regs) -> regs[destReg] = Integer.toUnsignedLong(imm);
		return memOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_LE", rewriteOn = FrameSlotTypeException.class)
	public boolean doLE (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) return;
			regs[destReg] = byteSwap(regs[destReg], imm);
    	};
    	return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_BE", rewriteOn = FrameSlotTypeException.class)
	public boolean doBE (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			if (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN) return;
			regs[destReg] = byteSwap(regs[destReg], imm);
		};
    	return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_MOV_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doMOV_IMM (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] = imm;
			regs[destReg] &= LOWER_BITS;
		};
		return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_MOV64_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doMOV64_IMM (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] = imm;
		};
		return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_MOV_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doMOV_REG (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] = regs[srcReg];
			regs[destReg] &= LOWER_BITS;
		};
		return regOp(lambda, frame);
	}
    
	@Specialization (guards = "opcode == EBPF_OP_MOV64_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doMOV64_REG (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] = regs[srcReg];
		};
		return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_LSH_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doLSH_IMM (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] <<= imm;
			regs[destReg] &= LOWER_BITS;
		};
		return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_LSH64_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doLSH64_IMM (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] <<= imm;
		};
		return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_LSH_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doLSH_REG (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] <<= regs[srcReg];
			regs[destReg] &= LOWER_BITS;
		};
		return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_LSH64_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doLSH64_REG (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] <<= regs[srcReg];
		};
		return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_RSH_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doRSH_IMM (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] >>>= imm;
			regs[destReg] &= LOWER_BITS;
		};
		return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_RSH64_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doRSH64_IMM (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] >>>= imm;
		};
		return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_RSH_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doRSH_REG (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] >>>= regs[srcReg];
			regs[destReg] &= LOWER_BITS;
		};
		return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_RSH64_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doRSH64_REG (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] >>>= regs[srcReg];
		};
		return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_ARSH_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doARSH_IMM (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] >>= imm;
			regs[destReg] &= LOWER_BITS;
		};
		return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_ARSH64_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doARSH64_IMM (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] >>= imm;
		};
    	return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_ARSH_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doARSH_REG (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] >>= regs[srcReg];
			regs[destReg] &= LOWER_BITS;
		};
		return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_ARSH64_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doARSH64_REG (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] >>= regs[srcReg];
		};
		return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_MUL_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doMUL_REG (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] *= regs[srcReg];
			regs[destReg] &= LOWER_BITS;
		};
    	return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_MUL64_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doMUL64_REG (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] *= regs[srcReg];
		};
		return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_MUL_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doMUL_IMM (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] *= imm;
			regs[destReg] &= LOWER_BITS;
		};
		return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_MUL64_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doMUL64_IMM (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] *= imm;
		};
		return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_NEG", rewriteOn = FrameSlotTypeException.class)
	public boolean doNEG (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] *= -1;
			regs[destReg] &= LOWER_BITS;
		};
    	return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_NEG64", rewriteOn = FrameSlotTypeException.class)
	public boolean doNEG64 (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] *= -1;
		};
		return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_OR_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doOR_IMM (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] |= imm;
			regs[destReg] &= LOWER_BITS;
		};
		return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_OR64_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doOR64_IMM (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] |= imm;
		};
		return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_OR_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doOR_REG (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] |= regs[srcReg];
			regs[destReg] &= LOWER_BITS;
		};
		return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_OR64_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doOR64_REG (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] |= regs[srcReg];
		};
		return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_AND_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doAND_IMM (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] &= imm;
			regs[destReg] &= LOWER_BITS;
		};
		return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_AND64_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doAND64_IMM (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] &= imm;
		};
		return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_AND_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doAND_REG (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] &= regs[srcReg];
			regs[destReg] &= LOWER_BITS;
		};
		return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_AND64_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doAND64_REG (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] &= regs[srcReg];
		};
		return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_XOR_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doXOR_IMM (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] ^= imm;
			regs[destReg] &= LOWER_BITS;
		};
		return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_XOR64_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doXOR64_IMM (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] ^= imm;
		};
		return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_XOR_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doXOR_REG (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] ^= regs[srcReg];
			regs[destReg] &= LOWER_BITS;
		};
		return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_XOR64_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doXOR64_REG (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] ^= regs[srcReg];
		};
		return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_MOD_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doMOD_IMM (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] %= imm;
			regs[destReg] &= LOWER_BITS;
		};
		return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_MOD64_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doMOD64_IMM (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] %= imm;
		};
    	return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_MOD_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doMOD_REG (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] %= regs[srcReg];
			regs[destReg] &= LOWER_BITS;
		};
    	return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_MOD64_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doMOD64_REG (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] %= regs[srcReg];
		};
    	return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_ADD_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doADD_IMM (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] += imm;
			regs[destReg] &= LOWER_BITS;
		};
    	return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_ADD64_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doADD64_IMM (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] += imm;
		};
    	return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_ADD_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doADD_REG (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] += regs[srcReg];
			regs[destReg] &= LOWER_BITS;
		};
		return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_ADD64_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doADD64_REG (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] += regs[srcReg];
		};
    	return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_SUB_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doSUB_IMM (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] -= imm;
			regs[destReg] &= LOWER_BITS;
		};
		return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_SUB64_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doSUB64_IMM (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] -= imm;
		};
    	return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_SUB_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doSUB_REG (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] -= regs[srcReg];
			regs[destReg] &= LOWER_BITS;
		};
		return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_SUB64_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doSUB64_REG (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] -= regs[srcReg];
		};
    	return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_DIV_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doDIV_REG (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] = Long.divideUnsigned(regs[destReg], regs[srcReg]);
			regs[destReg] &= LOWER_BITS;
		};
    	return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_DIV64_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doDIV64_REG (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] = Long.divideUnsigned(regs[destReg], regs[srcReg]);
		};
		return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_DIV_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doDIV_IMM (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] = Long.divideUnsigned(regs[destReg], imm);
			regs[destReg] &= LOWER_BITS;
		};
    	return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_DIV64_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doDIV64_IMM (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		RegLambda lambda = (long[] regs) -> {
			regs[destReg] = Long.divideUnsigned(regs[destReg], imm);
		};
		return regOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_JEQ_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doJEQ_IMM (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (Long.compareUnsigned(regs[destReg], imm) == 0) pc += offset;
			return pc;
    	};
    	return jumpOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_JNE_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doJNE_IMM (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (Long.compareUnsigned(regs[destReg], imm) != 0) pc += offset;
			return pc;
		};
		return jumpOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_JNE_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doJNE_REG (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (Long.compareUnsigned(regs[destReg], regs[srcReg]) != 0) pc += offset;
			return pc;
    	};
    	return jumpOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_JEQ_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doJEQ_REG (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (Long.compareUnsigned(regs[destReg], regs[srcReg]) == 0) pc += offset;
			return pc;
    	};
    	return jumpOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_JGE_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doJGE_IMM (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (Long.compareUnsigned(regs[destReg], imm) >= 0) pc += offset;
			return pc;
    	};
    	return jumpOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_JGE_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doJGE_REG (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (Long.compareUnsigned(regs[destReg], regs[srcReg]) >= 0) pc += offset;
			return pc;
    	};
    	return jumpOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_JGT_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doJGT_REG (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (Long.compareUnsigned(regs[destReg], regs[srcReg]) > 0) pc += offset;
			return pc;
    	};
    	return jumpOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_JGT_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doJGT_IMM (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (Long.compareUnsigned(regs[destReg], imm) > 0) pc += offset;
			return pc;
    	};
    	return jumpOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_JSGT_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doJSGT_REG (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (regs[destReg] > regs[srcReg]) pc += offset;
			return pc;
    	};
    	return jumpOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_JSGT_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doJSGT_IMM (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (regs[destReg] > imm) pc += offset;
			return pc;
    	};
    	return jumpOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_JSGE_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doJSGE_IMM (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (regs[destReg] >= imm) pc += offset;
			return pc;
    	};
    	return jumpOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_JSGE_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doJSGE_REG (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (regs[destReg] >= regs[srcReg]) pc += offset;
			return pc;
    	};
    	return jumpOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_JLT_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doJLT_IMM (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (Long.compareUnsigned(regs[destReg], imm) < 0) pc += offset;
			return pc;
    	};
    	return jumpOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_JLT_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doJLT_REG (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (Long.compareUnsigned(regs[destReg], regs[srcReg]) < 0) pc += offset;
			return pc;
    	};
    	return jumpOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_JLE_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doJLE_IMM (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (Long.compareUnsigned(regs[destReg], imm) <= 0) pc += offset;
			return pc;
    	};
    	return jumpOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_JLE_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doJLE_REG (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (Long.compareUnsigned(regs[destReg], regs[srcReg]) <= 0) pc += offset;
			return pc;
    	};
    	return jumpOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_JSLT_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doJSLT_IMM (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (regs[destReg] < imm) pc += offset;
			return pc;
    	};
    	return jumpOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_JSLT_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doJSLT_REG (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (regs[destReg] < regs[srcReg]) pc += offset;
			return pc;
    	};
    	return jumpOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_JSLE_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doJSLE_IMM (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (regs[destReg] <= imm) pc += offset;
			return pc;
    	};
    	return jumpOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_JSLE_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doJSLE_REG (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (regs[destReg] <= regs[srcReg]) pc += offset;
			return pc;
    	};
    	return jumpOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_JSET_REG", rewriteOn = FrameSlotTypeException.class)
	public boolean doJSET_REG (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (Long.compareUnsigned(regs[destReg] & regs[srcReg], 0) > 0) pc += offset;
			return pc;
    	};
    	return jumpOp(lambda, frame);
	}
	
	@Specialization (guards = "opcode == EBPF_OP_JSET_IMM", rewriteOn = FrameSlotTypeException.class)
	public boolean doJSET_IMM (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
		JumpLambda lambda = (long[] regs, int pc) -> {
			if (Long.compareUnsigned(regs[destReg] & imm, 0) > 0) pc += offset;
			return pc;
    	};
    	return jumpOp(lambda, frame);
	}
	
	//The bottom two operations only depend on program counter, so they do not call memOp, regOp, or jumpOp
	
	@Specialization (guards = "opcode == EBPF_OP_JA", rewriteOn = FrameSlotTypeException.class)
	public boolean doJA (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
    	//Loading stored program information from frame
    	FrameSlot pcSlot = frame.getFrameDescriptor().findFrameSlot("pc");
    	int pc = frame.getInt(pcSlot) + 1;
    	pc += offset;
    	//Updating program counter to new value
    	frame.setInt(pcSlot, pc);
    	return true;
	}
	
	@Specialization (guards = "opcode == EBPF_OP_EXIT", rewriteOn = FrameSlotTypeException.class)
	public boolean doEXIT (VirtualFrame frame, byte opcode, byte srcReg, byte destReg, short offset, int imm) throws FrameSlotTypeException {
    	//Loading stored program information from frame
    	FrameSlot pcSlot = frame.getFrameDescriptor().findFrameSlot("pc");
    	int pc = frame.getInt(pcSlot) + 1;
    	//Updating program counter to new value (just goes to next instruction)
    	frame.setInt(pcSlot, pc);
    	return false;
	}
}
