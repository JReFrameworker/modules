package jref;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Test {

	public static void main(String[] args) throws IOException {
		File file = new File("secretFile");
		FileWriter fw = new FileWriter(file);
		fw.write("blah");
		fw.close();
		System.out.println("File Exists: " + file.exists());
		file.delete();
	}

}
