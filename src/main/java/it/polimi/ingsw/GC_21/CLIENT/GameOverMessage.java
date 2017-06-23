package it.polimi.ingsw.GC_21.CLIENT;

import java.util.ArrayList;
import java.util.Scanner;

import it.polimi.ingsw.GC_21.BOARD.Board;
import it.polimi.ingsw.GC_21.GAMEMANAGEMENT.GameEndState;
import it.polimi.ingsw.GC_21.PLAYER.Player;
import it.polimi.ingsw.GC_21.VIEW.InputForm;

public class GameOverMessage extends MessageToClient{
	private Board board;
	private ArrayList<Player> players;
	private ArrayList<Player> victoryPointsRanking;
	
	public GameOverMessage(boolean result, String description, Board board, ArrayList<Player> players,
			ArrayList<Player> victoryPointsRanking) {
		super(result, false, description);
		this.board = board;
		this.players = players;
		this.victoryPointsRanking = victoryPointsRanking;
		this.gameEndState = GameEndState.Over;
	}
	


}