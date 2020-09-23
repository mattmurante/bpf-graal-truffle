package deprecated;

import com.oracle.truffle.api.dsl.CachedLanguage;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.bpf.BPFLanguage;
import com.oracle.truffle.bpf.nodes.BaseNode;

@NodeInfo(language = "BPF", description = "Retrieves current state of program counter")
public abstract class ReadPC extends BaseNode {
	
	@Specialization
	public int readPc(VirtualFrame frame, @CachedLanguage BPFLanguage language) {
		return language.getPc() + 1;
	}
	
}
