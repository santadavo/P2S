package it.polimi.ingsw.GC_21.CLIENT;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import it.polimi.ingsw.GC_21.fx.ViewType;


public class InputThread extends Thread {
	private PrintWriter out;
	private Scanner in;
	private SocketClient socketClient;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	public InputThread(PrintWriter out, Scanner in, SocketClient socketClient) {
		this.out = out;
		this.in = in;
		this.socketClient = socketClient;
	}


	/*@Override
	public void run() {
		try {
			Thread inputObjectThread = new InputObjectThread(oos, ois, socketClient);     
	        inputObjectThread.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while(true) {
			String messaggioricevuto = in.nextLine(); //arriva dal socket server
			if (messaggioricevuto.equals("music")) {
				Music.start();
			}
            if (messaggioricevuto.equals("addio")){
                out.println("sei uscito");
                break;
            }
			if(socketClient.getView() == ViewType.GUI){
				System.out.println("thread: "+messaggioricevuto);
				socketClient.setMessForGui(messaggioricevuto);
			} else {
            System.out.println(messaggioricevuto);
            
		}
	
		}
	}*/
}