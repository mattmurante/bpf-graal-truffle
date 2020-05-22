package BPF.Graal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.oracle.truffle.api.Truffle;

//Class to execute virtual machine on BPF program file

public class VM {
	
    public static void main(String args[]) {
    	//Only argument should be BPF file
    	if (args.length != 1) {
    		return;
    	}
    	//Reads in file to program bytearray
    	File infile = new File(args[0]);
    	FileInputStream fis = null;
    	byte[] program = new byte[(int) infile.length()];
    	try {
			fis = new FileInputStream(infile);
			fis.read(program);
			//Creates Truffle tree and executes
			TruffleTreeGenerator ttg = new TruffleTreeGenerator();
			Program progTree = ttg.genTree(program);
	        Truffle.getRuntime().createCallTarget(progTree).call(new Object[] {new String[0]});
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	finally {
    		try {
    			if (fis != null) {
    				fis.close();
    			}
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }
}
