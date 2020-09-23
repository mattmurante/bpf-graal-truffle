package deprecated;

import com.oracle.truffle.api.dsl.CachedLanguage;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.bpf.BPFLanguage;
import com.oracle.truffle.bpf.nodes.BaseNode;
import com.oracle.truffle.bpf.nodes.util.Memory;

@NodeInfo(language = "BPF", description = "Retrieves current state of memory")
public abstract class ReadMemory extends BaseNode {
	
	@Specialization
	public Memory readMem(VirtualFrame frame, @CachedLanguage BPFLanguage language) {
		return language.getMemory();
	}
	
}
