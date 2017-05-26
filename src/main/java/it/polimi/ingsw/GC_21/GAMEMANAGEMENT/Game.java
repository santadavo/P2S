package it.polimi.ingsw.GC_21.GAMEMANAGEMENT;

import java.util.ArrayList;
import it.polimi.ingsw.GC_21.BOARD.Board;
import it.polimi.ingsw.GC_21.GAMECOMPONENTS.DevCardType;
import it.polimi.ingsw.GC_21.GAMECOMPONENTS.DevDeck;
import it.polimi.ingsw.GC_21.PLAYER.Player;
import it.polimi.ingsw.GC_21.controller.Controller;
import it.polimi.ingsw.GC_21.view.RemoteView;

public class Game {
	
	private Controller controller;
	private static int currentNumberOfGame = 0; 
	private int id;
	private int numberOfPlayers;
	private Board board;
	private ArrayList<Player> players;
	private Age currentAge;
		

	public Game(int numberOfPlayers, ArrayList<Player> players) {
		super();
		this.id = currentNumberOfGame + 1;
		currentNumberOfGame++;
		this.numberOfPlayers = numberOfPlayers;
		this.board = new Board();
		this.players = players;	
		this.controller = new Controller(this); //TODO observer pattern
		executeGame();
	}
	
	public void executeGame() {
		for (int i = 0; i < 3; i++) {
			currentAge = new Age(i, this);
			currentAge.executeAge();
		}
		
	}
	

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}

	public void setNumberOfPlayers(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public static int getCurrentNumberOfGame() {
		return currentNumberOfGame;
	}

	public static void setCurrentNumberOfGame(int currentNumberOfGame) {
		Game.currentNumberOfGame = currentNumberOfGame;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public Age getCurrentAge() {
		return currentAge;
	}

	public void setCurrentAge(Age currentAge) {
		this.currentAge = currentAge;
	}
	

	
	
}