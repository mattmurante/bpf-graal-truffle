package BPF.Graal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.io.ByteSequence;

import com.oracle.truffle.api.Truffle;

//Class to execute virtual machine on BPF program file

public class VM {
	
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
    	source = Source.newBuilder(BPF, ByteSequence.create(bytes), "<literal>").buildLiteral();
        System.exit(executeSource(source, System.in, System.out));
        fis.close();
    }

    private static int executeSource(Source source, InputStream in, PrintStream out) {
        Context context;
        PrintStream err = System.err;
        try {
            context = Context.newBuilder(BPF).build();
        } catch (IllegalArgumentException e) {
            err.println(e.getMessage());
            return 1;
        }
        out.println("== running on " + context.getEngine());

        try {
            Value result = context.eval(source);
            if (context.getBindings(BPF).getMember("main") == null) {
                err.println("No function main() defined in SL source file.");
                return 1;
            }
            if (!result.isNull()) {
                out.println(result.toString());
            }
            return 0;
        } catch (PolyglotException ex) {
            if (ex.isInternalError()) {
                // for internal errors we print the full stack trace
                ex.printStackTrace();
            } else {
                err.println(ex.getMessage());
            }
            return 1;
        } finally {
            context.close();
        }
    }
	
	
	
/*	public static void main(String args[]) {
		// Only argument should be BPF file
		assert (args.length == 1) : "Program takes BPF file path as only argument";
		// Reads in file to program bytearray
		File infile = new File(args[0]);
		FileInputStream fis = null;
		byte[] program = new byte[(int) infile.length()];
		try {
			fis = new FileInputStream(infile);
			fis.read(program);
			// Creates Truffle tree and executes
			BPFParser parser = new BPFParser();
			Program progTree = parser.parse(program);
			Truffle.getRuntime().createCallTarget(progTree).call(new Object[] { new String[0] });
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}*/
}
