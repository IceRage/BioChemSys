package ro.ubb.biochem.temp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class OutputWriter {

	private static PrintWriter writer = null;
	private static String filepath = "output.txt";

	/**
	 * Print a new message in the file specified by filepath
	 * @param message
	 */
	public static void println(String message) {
		if (writer == null) {
			init();
		}
		
		writer.println(message);
	}
	
	/**
	 * Set the output file and close the writer if it is open
	 */
	public static void setOutputFile(String filepath) {
		OutputWriter.filepath = filepath;
		
		writer.close();
		
		writer = null;
	}

	private static void init() {
		try {
			writer = new PrintWriter(new File(filepath));
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
	}

}
