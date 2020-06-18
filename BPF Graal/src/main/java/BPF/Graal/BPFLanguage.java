package BPF.Graal;

import java.io.IOException;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.TruffleLanguage.ContextPolicy;
import com.oracle.truffle.api.source.Source;

@TruffleLanguage.Registration(id = BPFLanguage.ID, name = "BPF", defaultMimeType = BPFLanguage.MIME_TYPE, characterMimeTypes = BPFLanguage.MIME_TYPE, contextPolicy = ContextPolicy.SHARED)
public class BPFLanguage extends TruffleLanguage<BPFContext> {
	
	public static volatile int counter;
	
	public static final String ID = "bpf";
	public static final String MIME_TYPE = "application/x-bpf";
	
	public BPFLanguage() {
		counter++;
	}
	
	@Override
	protected BPFContext createContext(Env env) {
		return new BPFContext(this, env);
	}

	@Override
	protected CallTarget parse(ParsingRequest request) throws IOException {
		Source source = request.getSource();
		Program program = BPFParser.parse(this, source);
		return Truffle.getRuntime().createCallTarget(program);
	}
	
	@Override
	protected boolean isObjectOfLanguage(Object object) {
		if (object instanceof Long) {
			return true;
		}
		return false;
	}
	
	public static BPFContext getCurrentContext() {
		return getCurrentContext(BPFLanguage.class);
	}

}