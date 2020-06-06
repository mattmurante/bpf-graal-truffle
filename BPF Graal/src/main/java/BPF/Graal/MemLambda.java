package BPF.Graal;

//Interface that provides an abstract lambda expression that can operate on memory
public interface MemLambda {
	
	void function(Memory memory, long[] regs);
	
}
