This is a Maven project based off of GraalVM's simple-language that uses the Truffle framework to implement an (e)BPF VM. This repository also includes sample programs that can be compiled to BPF and run on the VM for testing.

To rebuild the project, first ensure the $JAVA_HOME is pointing to the GraalVM Contents/Home directory. Then, in the root directory, execute the command:

mvn package

to recreate the bpf language jar, the native-image executable, and the bpf component which can be installed into GraalVM. To add the component to GraalVM, execute:

gu -L install {path to bpf-component.jar}

GraalVM releases can be found at https://github.com/graalvm/graalvm-ce-builds/releases. Graal 20.1.0 for Java 8 was used for this project.

The code for simplelanguage can be found at https://github.com/graalvm/simplelanguage.

Potential Improvements:
- Add (e)BPF call instruction
- Improve use of Truffle framework and interaction with Graal compiler for improved performance