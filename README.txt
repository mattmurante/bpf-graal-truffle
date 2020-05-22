This project uses the Truffle framework to make an (e)BPF VM, which can then be JIT compiled using Graal. The code in this repository is a dump of a gradle project made in Eclipse, along with sample programs that can be compiled to BPF for testing.

To run the project using gradle, first ensure the $JAVA_HOME is pointing to the Graal Contents/Home directory. Then, in the BPF Graal directory, executing the command:

./gradlew run --args="someBPFprogram.bpf"

will result in the VM printing and returning the result of the given (e)BPF program.