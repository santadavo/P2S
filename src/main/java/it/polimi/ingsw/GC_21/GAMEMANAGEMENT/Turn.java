package it.polimi.ingsw.GC_21.GAMEMANAGEMENT;

import java.util.ArrayList;

import javax.swing.text.View;

import it.polimi.ingsw.GC_21.view.RemoteView;

public class Turn {

	private int turnNumber;
	private Game game;

	public Turn(int turnNumber, Game game) {
		this.turnNumber = turnNumber;
	}
	
	public void executeView() {
		for (int i = 0; i < game.getController().getRemoteViews().size(); i++) {
			game.getController().getRemoteViews().get(i).input();
		}
	}

	
}