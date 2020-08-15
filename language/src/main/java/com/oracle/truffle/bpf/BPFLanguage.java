package com.oracle.truffle.bpf;

import java.io.IOException;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.TruffleLanguage.ContextPolicy;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.bpf.nodes.util.Memory;
import com.oracle.truffle.bpf.nodes.util.MemoryRegion;

//Class to define constructs of the BPF language
@TruffleLanguage.Registration(id = BPFLanguage.ID, name = "BPF", defaultMimeType = BPFLanguage.MIME_TYPE, byteMimeTypes = BPFLanguage.MIME_TYPE, contextPolicy = ContextPolicy.SHARED)
public class BPFLanguage extends TruffleLanguage<BPFContext> {
	
	public static volatile int counter;
	private int pc;
	private long[] registers;
	private Memory memory;
	
	private final int STACK_SIZE = 512;
	public static final String ID = "bpf";
	public static final String MIME_TYPE = "application/x-bpf";
	
	public BPFLanguage() {
		counter++;
	}
	
	@Override
	protected BPFContext createContext(Env env) {
		// Initializing stack and hardware elements
		pc = 0;
		registers = new long[11];
		registers[10] = 16384 * 8;
		memory = new Memory();
		try {
			//Storing stack in memory
			memory.addRegion(new MemoryRegion((16384 * 8 - STACK_SIZE), new byte[STACK_SIZE]));
		} catch (Exception e) {
			System.err.println("Stack could not be allocated successfully");
		}
		return new BPFContext(this, env);
	}

	@Override
	protected CallTarget parse(ParsingRequest request) throws IOException {
		Source source = request.getSource();
		RootNode program = BPFParser.parse(this, source);
		return Truffle.getRuntime().createCallTarget(program);
	}
	
	@Override
	protected boolean isObjectOfLanguage(Object object) {
		if (Long.class.isInstance(object)) {
			return true;
		}
		return false;
	}
	
	public static BPFContext getCurrentContext() {
		return getCurrentContext(BPFLanguage.class);
	}
	
	public void setPc(int pc) {
		this.pc = pc;
	}
	
	public int getPc() {
		return pc;
	}
	
	public void incPc() {
		pc++;
	}
	
	public long[] getRegisters() {
		return registers;
	}
	
	public Memory getMemory() {
		return memory;
	}

}