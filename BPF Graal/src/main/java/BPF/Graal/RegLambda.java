package BPF.Graal;

//Interface that provides an abstract lambda expression that can operate on registers
public interface RegLambda {

	void function(long[] regs);

}
