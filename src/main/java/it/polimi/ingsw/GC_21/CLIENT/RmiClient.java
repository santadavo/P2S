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

import it.polimi.ingsw.GC_21.fx.ViewType;
import it.polimi.ingsw.GC_21.view.Server;
import it.polimi.ingsw.GC_21.view.ServerInterface;


public class RmiClient extends UnicastRemoteObject implements Serializable, RmiClientInterface, Connections{

	private ArrayList<String> messagesforserver;
	private Stack<String> stackforclient;
	private ViewType view;
	private Object LOCK = new Object(); // just something to lock on

	
	public RmiClient(ViewType view) throws RemoteException {
		super();
		this.view=view;
		this.messagesforserver = new ArrayList<String>();
		this.stackforclient = new Stack<String>();
	}

	
	
	public String sendToServer() throws RemoteException {
		if(view.equals(ViewType.GUI)) {
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
		} else {
		Scanner scanner = new Scanner(System.in);
		return scanner.nextLine();
		}
	}


	public void sendGUI(String mess) {
		messagesforserver.add(mess);
		synchronized (LOCK) {
		    LOCK.notifyAll();
		}
	}

	
	public void clientReceive(String string) {
		if ("music".equals(string)) {
			Music.start();
		} else if(view.equals(ViewType.GUI)) {
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
	

	
}