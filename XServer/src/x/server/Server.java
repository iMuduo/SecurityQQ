package x.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Server {
	
	public static void main(String[] args) throws FileNotFoundException {
		System.setOut(new PrintStream(new File("D:\\server.log")));
		ServerFrame serverFrame=new ServerFrame();
		serverFrame.setVisible(true);
	}
	
}
