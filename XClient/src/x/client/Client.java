package x.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import x.client.frame.LoginFrame;

public class Client {

	public static void main(String[] args) throws FileNotFoundException {
		System.setOut(new PrintStream(new File("D:\\client.log")));
		LoginFrame login=new LoginFrame();
		login.setVisible(true);
	}

}
