package BPF.Graal;

import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.TruffleLanguage.Env;

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
