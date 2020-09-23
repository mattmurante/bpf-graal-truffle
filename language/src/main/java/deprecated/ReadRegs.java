package deprecated;

import com.oracle.truffle.api.dsl.CachedLanguage;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.bpf.BPFLanguage;
import com.oracle.truffle.bpf.nodes.BaseNode;

@NodeInfo(language = "BPF", description = "Retrieves current state of registers")
public abstract class ReadRegs extends BaseNode {
	
	@Specialization
	public long[] readRegs(VirtualFrame frame, @CachedLanguage BPFLanguage language) {
		return language.getRegisters();
	}
	
}
