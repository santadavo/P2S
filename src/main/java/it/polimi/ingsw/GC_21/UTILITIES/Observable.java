package it.polimi.ingsw.GC_21.UTILITIES;

import java.awt.List;

import it.polimi.ingsw.GC_21.CLIENT.MessageToClient;
import it.polimi.ingsw.GC_21.CLIENT.PrivilegeMessage;
import it.polimi.ingsw.GC_21.CONTROLLER.ControllerForm;
import it.polimi.ingsw.GC_21.PLAYER.Color;
import it.polimi.ingsw.GC_21.PLAYER.Player;
import it.polimi.ingsw.GC_21.UTILITIES.*;
import it.polimi.ingsw.GC_21.VIEW.ExcommInput;
import it.polimi.ingsw.GC_21.VIEW.InputForm;
import it.polimi.ingsw.GC_21.VIEW.RemoteView;

import java.util.*;

import javax.naming.spi.DirStateFactory.Result;
import javax.sound.sampled.LineListener;

public abstract class Observable<C> {
	private ArrayList<P2SObserver<C>> observers;
	private CurrentObserver currentObserver;
	private P2SObserver<C> blackObserver;
	private HashMap<P2SObserver<C>, Player> playerObserver = new HashMap<P2SObserver<C>, Player>();
	
	
	public void clear() {
		observers.clear();
		currentObserver = null;
		playerObserver.clear();

	}
	
	public void attachBlack(){
		for (P2SObserver<C> o : this.observers) {
			if (playerObserver.get(o).getPlayerColor() == Color.Black) {
				blackObserver = o;
			}
		}
	}
	

	public void detachBlack(){
		this.blackObserver = null;
	}
			
	public void attachCurrent(CurrentObserver currentObserver){
		this.currentObserver=currentObserver;
	}
	

	public void detachCurrent(){
		this.currentObserver=null;
	}
	
	public Observable() {
		observers = new ArrayList<P2SObserver<C>>();
	}

	public void attach(P2SObserver<C> o) {
		observers.add(o);
	}

	public void detach(P2SObserver<C> o) {
		this.observers.remove(o);
	}
	
	public void attachPlayer(Player player, P2SObserver<C> key){
		playerObserver.put(key, player);
	}
	
	public void notifyCurrent(MessageToClient message){
		currentObserver.updateCurrent(message);
	}
	
	public void notifyBlackTurn(Player player){
		for (P2SObserver<C> o : this.observers) {
			if (player.equals(playerObserver.get(o))) {
				o.updateTurn();
				detachCurrent();		
			}
		}
	}
	
	public void notifyTurn() {
			for (P2SObserver<C> o : this.observers) {
					o.updateTurn();
					detachCurrent();
				}
			}
	
	public void notifyInit() {
		for (P2SObserver<C> o : this.observers) {
				o.updateInit();
			}
		}
	
	public void notifySave(String string) {
		for (P2SObserver<C> o : this.observers) {
				o.updateSave(string);
			}
		}
			
	public void notifyBlackSwitch(Player blackPlayer, Player playerToSwitch){
	for (P2SObserver<C> o : this.observers) {
		if (blackPlayer.equals(playerObserver.get(o))) {
				o.updateBlackSwitch(playerToSwitch);
				playerObserver.put(o, playerToSwitch);
				for (P2SObserver<C> obs : this.observers) {
					if (playerToSwitch.equals(playerObserver.get(obs)) && !obs.equals(o)) {
						playerObserver.put(obs, blackPlayer);
						return;
					}
				}
			}
		}
	}


	public void notifyClose() {
		for (P2SObserver<C> o : this.observers) {
				o.updateClose();
			}
		}
	
	
	public void notifyBlack() {
		for (P2SObserver<C> o : this.observers) {
				o.updateBlack();
			}
		}


	
	public void notifyOrderedTurn(ArrayList<Player> turnOrder, int currentPlayerNumber) {
		for (int i = 0; i < turnOrder.size(); i++) {
			for (P2SObserver<C> o : this.observers) {
				if (turnOrder.get(i).equals(playerObserver.get(o)) && !o.equals(blackObserver)) {
					if (currentPlayerNumber == 0) {
						o.updateTurn();
						detachCurrent();
					}
					else {
						currentPlayerNumber --;//go to the current Player in the saved game
					}
				}
			}
			
		}
	}
	
	
	
	
	
	
	public boolean notifyController(ControllerForm controllerForm) {	
		boolean response=false;
		for (P2SObserver<C> o : this.observers) {
			response = o.updateMessage(controllerForm);
		}
		return response;
	}

	public boolean notifyObservers(C c) {
		for (P2SObserver<C> o : this.observers) {
			boolean result = o.update(c);
			if (!result) {
				return false;
			}
		}
		return true;

	}
	

	
	public void notifyBroadcast(MessageToClient messageToClient) {
		for (P2SObserver<C> o : this.observers) {
			o.updateBroadcast(messageToClient);
		}
	}

}
