package chess.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	private Socket client;
	private PrintStream output;
	private BufferedReader input;
	
	public void printOutput(String message) {
		output.println(message);
		output.flush();
	}
	
	public void init() {
		client = null;
		
		output = null;
		input = null;
		
		try {
			client = new Socket("localhost", 1234);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		try {
			output = new PrintStream(client.getOutputStream());
			input = new BufferedReader(new InputStreamReader(client.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public void close() {
		try {
			input.close();
			output.close();
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
