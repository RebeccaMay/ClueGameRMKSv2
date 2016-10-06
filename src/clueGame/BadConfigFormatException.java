package clueGame;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class BadConfigFormatException extends RuntimeException {
	private String msg;

	public BadConfigFormatException() {
		super("Bad config file format");
	}

	public BadConfigFormatException(String msg){
		super("Bad format: " + msg);
		this.msg = msg;
		
		try
		{
			String outputFile = "logfile.txt";
			FileWriter fw = new FileWriter(outputFile, true);
		    BufferedWriter bw = new BufferedWriter(fw);
		    PrintWriter out = new PrintWriter(bw);
			
			out.println("Bad format: " + msg);
			out.close();
		}
		catch (IOException e) {
		    System.out.println(e.getMessage());
		}
	}
	
	@Override
	public String toString() {
		return "Bad config file format: " + msg;
	}

	
}
