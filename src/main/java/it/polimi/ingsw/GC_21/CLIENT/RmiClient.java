package it.polimi.ingsw.GC_21.CLIENT;

import java.awt.image.TileObserver;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

import it.polimi.ingsw.GC_21.GAMEMANAGEMENT.Message;
import it.polimi.ingsw.GC_21.fx.ViewType;
import it.polimi.ingsw.GC_21.view.InputFromView;
import it.polimi.ingsw.GC_21.view.Server;
import it.polimi.ingsw.GC_21.view.ServerInterface;


public class RmiClient extends UnicastRemoteObject implements Serializable, RmiClientInterface, Connections{

	private ArrayList<String> messagesforserver;
	private Stack<String> stackforclient;
	private ViewType view;
	private Object LOCK = new Object(); // just something to lock on
	private Object LOCK2 = new Object(); // just something to lock on

	private InputFromView inputToSend = null;
	private Message receivedMessage;

	
	public RmiClient(ViewType view) throws RemoteException {
		super();
		this.view=view;
		this.messagesforserver = new ArrayList<String>();
		this.stackforclient = new Stack<String>();
	
		
	}
	
	public  Message getReceivedMessage() {
		synchronized (LOCK) {
		while (receivedMessage == null) {
			try { LOCK.wait(); 
			}
	        catch (InterruptedException e) {
	            // treat interrupt as exit request
	            break;
	        }
		}
		}
		Message message = receivedMessage;
		receivedMessage = null;
		if(view.equals(ViewType.CLI)) {
			message.convert();
		}
		System.out.println("In get Received: " + receivedMessage);
		return message;
	}

	
	
	public String sendToServer() throws RemoteException {
		/*if(view.equals(ViewType.GUI)) {
			synchronized (LOCK) {
			    while (messagesforserver.isEmpty()) {
			        try { LOCK.wait(); }
			        catch (InterruptedException e) {
			            // treat interrupt as exit request
			            break;
			        }
			    }
			}
		String toserver = new String(messagesforserver.get(0));
		System.out.println(toserver);
		messagesforserver.remove(0);
		System.out.println(toserver);
		return toserver;
		} else {*/
		Scanner scanner = new Scanner(System.in);
		return scanner.nextLine();
		//}
	}


	public void sendGUI(String mess) {
		messagesforserver.add(mess);
		
	}

	
	public void clientReceive(String string) {
		if ("music".equals(string)) {
			Music.start();
		} 
		if(view.equals(ViewType.GUI)) {
			this.stackforclient.push(string);
			System.out.println(string);
		} else {
			System.out.println(string);
		}
		
	}
	
	@Override
	public String getMessage() {
		while(stackforclient.isEmpty()) {
		}
		return stackforclient.pop();
	}



	@Override
	public void receiveObject(Message message) {
		this.receivedMessage = message;
		synchronized (LOCK) {
		    LOCK.notifyAll();
		}
	}



	@Override
	public InputFromView sendObjectToServer() throws RemoteException {
		while (inputToSend == null) {	
			try { LOCK2.wait(); }
	        catch (InterruptedException e) {
	            // treat interrupt as exit request
	            break;
	        }
		
		}
		InputFromView inputFromView = inputToSend;
		inputToSend = null;
		return inputFromView;
	}



	public InputFromView getInputToSend() {
		return inputToSend;
	}



	public void setInputToSend(InputFromView inputToSend) {
		this.inputToSend = inputToSend;
		synchronized (LOCK2) {
		    LOCK.notifyAll();
		}
	}
	

	
}
