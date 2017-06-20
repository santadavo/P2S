package it.polimi.ingsw.GC_21.VIEW;

import java.util.Scanner;

import it.polimi.ingsw.GC_21.ACTION.ExcommAction;

public class ExcommInput extends InputForm {
	private String choice;

	@Override
	public void execute(RemoteView remoteView) {
		super.execute(remoteView);
		switch (choice) {
		case "Y" : remoteView.notifyObservers(new ExcommAction(remoteView.getPlayer(), remoteView.getGame(), true));
					break;
		case "N" :	remoteView.notifyObservers(new ExcommAction(remoteView.getPlayer(), remoteView.getGame(), false));
					break;
		default: adapterConnection.out("Sorry, Sua Eccellenza didn't get your answer... could you repeat please?");//TODO error message
					remoteView.notifyObservers(new ExcommAction(remoteView.getPlayer(), remoteView.getGame(), true));
		}		
	}


@Override
public void inputFromCli(Scanner keyboard) {
	 choice = keyboard.nextLine();
	}
}