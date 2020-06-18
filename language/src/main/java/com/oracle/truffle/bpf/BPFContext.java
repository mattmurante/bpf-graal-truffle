package com.oracle.truffle.bpf;

import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.TruffleLanguage.Env;

//Context class to track elements of language - need to populate more for better polyglot interaction
public final class BPFContext {
	
	private final Env env;
	private final BPFLanguage language;
	
	public BPFContext(BPFLanguage language, TruffleLanguage.Env env) {
		this.language = language;
		this.env = env;
	}
	
	public Env getEnv() {
		return env;
	}
	
	public static BPFContext getCurrent() {
		return BPFLanguage.getCurrentContext();
	}
	
}
