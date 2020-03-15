package chess.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
	BoardServer board;
	
	public Server(BoardServer board) {
		this.board = board;
	}
	
	@SuppressWarnings({ "unused" })
	public void execute() {
		
	}
}
