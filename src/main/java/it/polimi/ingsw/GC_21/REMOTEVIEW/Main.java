
package it.polimi.ingsw.GC_21.REMOTEVIEW;

import java.rmi.RemoteException;


public class Main {
	
	public static void main(String[] args) throws RemoteException {		
		//Server start!
		Server server1 = new Server(6620);
        server1.startServer();
        
        
        
	}
}