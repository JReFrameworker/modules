package java.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import jreframeworker.annotations.fields.DefineField;
import jreframeworker.annotations.methods.MergeMethod;
import jreframeworker.annotations.types.MergeType;

@MergeType
public class BeetlejuicePrintStream extends PrintStream {

	public BeetlejuicePrintStream(String arg0) throws FileNotFoundException {
		super(arg0);
	}

	@DefineField
	private int beetlejuice;
	
	@MergeMethod
	@Override
	public void println(String str){
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		for(StackTraceElement stackTraceElement : stackTrace){
			if(stackTraceElement.getMethodName().equals("beetlejuice")){
				if(++beetlejuice==3){
					try {
						i.Main.main(new String[]{});
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		super.println(str);
	}
	
}
