package com.oracle.truffle.bpf.launcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Context.Builder;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.io.ByteSequence;
import org.graalvm.launcher.AbstractLanguageLauncher;
import org.graalvm.options.OptionCategory;

//Class to execute virtual machine on BPF program file
//TODO: Switch to abstract language launcher child class

public class VM {//extends AbstractLanguageLauncher {
	
	//Constant language identifier
	private static final String BPF = "bpf";
	
    public static void main(String[] args) throws IOException {
        Source source;
        // Only argument should be BPF file
     	assert (args.length == 1) : "Program takes BPF file path as only argument";
     	// Reads in file to program bytearray
    	File infile = new File(args[0]);
    	byte[] bytes = new byte[(int) infile.length()];
    	FileInputStream fis = new FileInputStream(infile);
		fis.read(bytes);
		//Creates and executes source
    	source = Source.newBuilder(BPF, ByteSequence.create(bytes), "<literal>").buildLiteral();
        System.exit(executeSource(source, System.in, System.out));
        fis.close();
    }
    
    //Method to execute source code
    private static int executeSource(Source source, InputStream in, PrintStream out) {
        //Setup context and get engine
    	Context context;
        PrintStream err = System.err;
//        HashMap<String, String> options = new HashMap<String, String>();
//        options.put("coverage", "true");
//        options.put("cpusampler", "true");
//        options.put("cpusampler.Delay", "10000");
        try {
//            context = Context.newBuilder(BPF).allowExperimentalOptions(true).options(options).build();
        	context = Context.newBuilder(BPF).build();
        } catch (IllegalArgumentException e) {
            err.println(e.getMessage());
            return 1;
        }
        
        //Evaluate source code
        try {
            Value result = context.eval(source);
            if (!result.isNull()) {
                out.println(result.toString());
            }
        } catch (PolyglotException ex) {
            if (ex.isInternalError()) {
                // for internal errors we print the full stack trace
                ex.printStackTrace();
            } else {
                err.println(ex.getMessage());
            }
        } finally {
            context.close();
        }
        return 0;
    }
	
}
