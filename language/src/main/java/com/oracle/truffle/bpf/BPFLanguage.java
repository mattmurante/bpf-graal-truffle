package com.oracle.truffle.bpf;

import java.io.IOException;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.TruffleLanguage.ContextPolicy;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.bpf.nodes.util.FunctionList;
import com.oracle.truffle.bpf.nodes.util.FunctionList.Function;
import com.oracle.truffle.bpf.nodes.util.Memory;

//Class to define constructs of the BPF language
@TruffleLanguage.Registration(id = BPFLanguage.ID, name = "BPF", defaultMimeType = BPFLanguage.MIME_TYPE, byteMimeTypes = BPFLanguage.MIME_TYPE, contextPolicy = ContextPolicy.SHARED)
public class BPFLanguage extends TruffleLanguage<BPFContext> {
	
	public static volatile int counter;
	private int pc;
	private long[] registers;
	private Memory memory = new Memory();
	private FunctionList extFns;
	
	private final int STACK_SIZE = 512;
	private final long STACK_ADDR = (16384 * 8 - STACK_SIZE);
	public static final String ID = "bpf";
	public static final String MIME_TYPE = "application/x-bpf";
	
	final Function malloc = new Function() {
		public long execute(long reg1, long reg2, long reg3, long reg4, long reg5) {
			return 0;
		}
	};
	final Function free = new Function() {
		public long execute(long reg1, long reg2, long reg3, long reg4, long reg5) {
			return 1;
		}
	};
	
	public BPFLanguage() {
		counter++;
	}
	
	@Override
	protected BPFContext createContext(Env env) {
		// Initializing stack and hardware elements
		pc = 0;
		registers = new long[11];
		registers[10] = 16384 * 8;
		registers[1]=4192;
		registers[2]=10000;
		registers[3]=100000;
		memory.addRegion(STACK_ADDR, STACK_SIZE);
		memory.addRegion(4192, 4096);
		extFns = new FunctionList();
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
	
	public FunctionList getExtFns() {
		return extFns;
	}
	
	public void dumpRegs() {
		for (int i = 0; i < registers.length; i++) {
			System.out.printf("Reg %d: 0x%16x\t", i, registers[i]);
		}
		System.out.println();
	}
	
}