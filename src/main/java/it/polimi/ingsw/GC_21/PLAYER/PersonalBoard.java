package it.polimi.ingsw.GC_21.PLAYER;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import it.polimi.ingsw.GC_21.ACTION.ExcommAction;
import it.polimi.ingsw.GC_21.BOARD.CraftType;
import it.polimi.ingsw.GC_21.EFFECT.Effect;
import it.polimi.ingsw.GC_21.EFFECT.ToCallAfterFinalCount;
import it.polimi.ingsw.GC_21.EFFECT.ToCallBeforeCraft;
import it.polimi.ingsw.GC_21.EFFECT.ToCallBeforeFinalCount;
import it.polimi.ingsw.GC_21.EFFECT.ToCallBeforePlacement;
import it.polimi.ingsw.GC_21.EFFECT.ToCallWhenEarning;
import it.polimi.ingsw.GC_21.GAMECOMPONENTS.*;
import it.polimi.ingsw.GC_21.GAMEMANAGEMENT.Game;

public class PersonalBoard implements Serializable{
	private Game game;
	private final OwnedCards[] myOwnedCards;
	private final Effect productionBonusTile;
	private final Effect harvestBonusTile;
	private Possession myPossession;
	private final Player player;
	private ArrayList<ToCallBeforeCraft> toCallBeforeCraftEffects;
	private ArrayList<ToCallBeforePlacement> toCallBeforePlacementEffects;
	private ArrayList<ToCallWhenEarning> toCallWhenEarningEffects;
	private ArrayList<ToCallBeforeFinalCount> toCallBeforeFinalCountEffects;
	private ArrayList<ToCallAfterFinalCount> toCallAfterFinalCountEffects;
	private ArrayList<LeaderCard> leaderCards;
	private ArrayList<OncePerTurnLeaderCard> playedOncePerTurnLeaderCards;

	public ArrayList<ToCallWhenEarning> getToCallWhenEarningEffects() {
		return toCallWhenEarningEffects;
	}

	public void setToCallWhenEarningEffects(ArrayList<ToCallWhenEarning> toCallWhenEarningEffects) {
		this.toCallWhenEarningEffects = toCallWhenEarningEffects;
	}

	public PersonalBoard(Player player, Game game) {
		this.myOwnedCards = OwnedCards.factoryOwnedCards();
		this.myPossession = new Possession(0, 0, 0, 0, 0, 0, 0);
		this.productionBonusTile = new Effect(new Possession(2, 0, 0, 0, 0, 1, 0), 0, game);
		this.harvestBonusTile = new Effect(new Possession(0, 1, 1, 1, 0, 0, 0), 0, game);
		this.game=game;
		//this.infinity();
		this.player = player;
		this.toCallBeforeCraftEffects= new ArrayList<ToCallBeforeCraft>();
		this.toCallBeforePlacementEffects = new ArrayList<ToCallBeforePlacement>();
		this.toCallWhenEarningEffects = new ArrayList<ToCallWhenEarning>();
		this.toCallAfterFinalCountEffects = new ArrayList<ToCallAfterFinalCount>();
		this.toCallBeforeFinalCountEffects = new ArrayList<ToCallBeforeFinalCount>();
		this.leaderCards = new ArrayList<LeaderCard>();
		this.playedOncePerTurnLeaderCards = new ArrayList<OncePerTurnLeaderCard>();
		if(player.getPlayerColor()!=Color.Black) {
			this.pickLeaderCards(game.getLeaderDeck());
		}
	}
	
	public void addDevCard(DevelopmentCard devCard) {
		OwnedCards tmpCardType = getSpecificOwnedCards(devCard.getDevCardType());
		tmpCardType.add(devCard);
		}
	 
	public void pickLeaderCards(LeaderDeck leaderDeck) {
		this.leaderCards.add((LeaderCard) leaderDeck.getSingleCard());
		this.leaderCards.add((LeaderCard) leaderDeck.getSingleCard());
		this.leaderCards.add((LeaderCard) leaderDeck.getSingleCard());
		this.leaderCards.add((LeaderCard) leaderDeck.getSingleCard());
	}
	

	public void infinity(){
		this.myPossession = new Possession(50, 50, 50, 50, 50, 50, 50);
		getSpecificOwnedCards(DevCardType.Venture).setOwnedCardsnumber(5);
		getSpecificOwnedCards(DevCardType.Building).setOwnedCardsnumber(5);
		getSpecificOwnedCards(DevCardType.Territory).setOwnedCardsnumber(5);
		getSpecificOwnedCards(DevCardType.Character).setOwnedCardsnumber(5);
	}
	
	public void addPermanentEffect(Effect effect){
		if (effect instanceof ToCallBeforeCraft){
			toCallBeforeCraftEffects.add((ToCallBeforeCraft) effect);
		}
		else if (effect instanceof ToCallBeforePlacement){
			toCallBeforePlacementEffects.add((ToCallBeforePlacement)effect);
		}
		else if (effect instanceof ToCallWhenEarning){
			toCallWhenEarningEffects.add((ToCallWhenEarning) effect);
		}
		else if (effect instanceof ToCallBeforeFinalCount){
			toCallBeforeFinalCountEffects.add((ToCallBeforeFinalCount) effect);
		}
		else if (effect instanceof ToCallAfterFinalCount){
			toCallAfterFinalCountEffects.add((ToCallAfterFinalCount) effect);
		}
	}
	
	public void finalEarning(){
		this.callBeforeFinalCountEffects();
		earnByCharacters();
		earnByVentures();
		earnByTerritories();
		earnByResources();
		earnByMP();
		this.callAfterFinalCountEffects();
	}
	
	
	private void earnByMP() {
		if (this.player.equals(game.getMilitaryPointsRanking().get(0))){
			myPossession.addItemToPossession(new VictoryPoints(5));
		}
		if (this.player.equals(game.getMilitaryPointsRanking().get(1))){
			myPossession.addItemToPossession(new VictoryPoints(2));
		}
	}

	public void payPossession(Possession possession){
		if (possession!= null){
			myPossession.subtract(possession);
		}
	}
	
	public void earnByResources(){
		int servantsNumber = player.getMyPersonalBoard().getMyPossession().getServants().getValue();
		int coinsNumber = player.getMyPersonalBoard().getMyPossession().getCoins().getValue();
		int woodsNumber = player.getMyPersonalBoard().getMyPossession().getWoods().getValue();
		int stonesNumber = player.getMyPersonalBoard().getMyPossession().getStones().getValue();
		int sumOfResources = servantsNumber + coinsNumber + woodsNumber + stonesNumber;
		myPossession.addItemToPossession(new VictoryPoints(sumOfResources/5));
	}
	
	public void earnByCharacters(){
		if (getSpecificOwnedCards(DevCardType.Character).getOwnedCardsnumber()!=0) {
			int[] vPoints = new int[6];
			JSONParser parser = new JSONParser(); //loading by file faithPointsTracking
			java.net.URL path = PersonalBoard.class.getResource("personalBoardBonus.json");
			JSONObject obj;
			try {
				FileReader file = new FileReader(path.getPath());
				obj = (JSONObject) parser.parse(file);
			    JSONArray victoryPoints= (JSONArray) obj.get("bonusByChar");
			    for (int i = 0; i < vPoints.length; i++) {
					vPoints[i]=Integer.parseInt(victoryPoints.get(i).toString());
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
			int vPointsToTake = vPoints[getSpecificOwnedCards(DevCardType.Character).getOwnedCardsnumber() - 1];
			myPossession.addItemToPossession(new VictoryPoints(vPointsToTake));
		}
	}
	
	public void earnByTerritories(){
		int[] vPoints = new int[4];
		JSONParser parser = new JSONParser(); //loading by file faithPointsTracking
		java.net.URL path = PersonalBoard.class.getResource("personalBoardBonus.json");
		JSONObject obj;
		try {
			FileReader file = new FileReader(path.getPath());
			obj = (JSONObject) parser.parse(file);
		    JSONArray victoryPoints= (JSONArray) obj.get("bonusByTerritories");
		    for (int i = 0; i < vPoints.length; i++) {
				vPoints[i]=Integer.parseInt(victoryPoints.get(i).toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		if (getSpecificOwnedCards(DevCardType.Territory).getOwnedCardsnumber() >= 3){
			int finalVP = vPoints[getSpecificOwnedCards(DevCardType.Territory).getOwnedCardsnumber() - 3];
			myPossession.addItemToPossession(new VictoryPoints(finalVP));
		}
	}
	
	public void callBeforeFinalCountEffects() {
		for (int i = 0; i < toCallBeforeFinalCountEffects.size(); i++) {
			((Effect) toCallBeforeFinalCountEffects.get(i)).activateEffect(player, null);
		}
	}
	
	public void callAfterFinalCountEffects() {
		for (int i = 0; i < toCallAfterFinalCountEffects.size(); i++) {
			((Effect) toCallAfterFinalCountEffects.get(i)).activateEffect(player, null);
		}
	}
	
	public void earnByVentures(){
		if (getSpecificOwnedCards(DevCardType.Venture).getOwnedCardsnumber()!=0) {
			int finalVP = 0;
			for (int i = 0; i < getSpecificOwnedCards(DevCardType.Venture).getOwnedCardsnumber(); i++) {
				Card tmp = getSpecificOwnedCards(DevCardType.Venture).getMyDevCards()[i].getCard();
				if (((DevelopmentCard) tmp).getFinalVictoryPoints()!=null) {
					finalVP = finalVP + ((DevelopmentCard) tmp).getFinalVictoryPoints().getValue();
				}
			}
			myPossession.addItemToPossession(new VictoryPoints(finalVP));
		}
	}

	public void activateCraft(CraftType craftType, int actionValue) {
		if(craftType.equals(CraftType.Production)) {
			productionBonusTile.activateEffect(player, null);
			OwnedCards ownedBuildingCardsCards = getSpecificOwnedCards(DevCardType.Building);
			for (int i = 0; i < ownedBuildingCardsCards.getOwnedCardsnumber(); i++) {
				CraftCard tmp = (CraftCard) ownedBuildingCardsCards.getMyDevCards()[i].getCard();
				if(tmp!=null && actionValue >=  tmp.getRequiredValueForCraft()) {
					tmp.callCraftEffect(player);
				}
			}
		} else if (craftType.equals(CraftType.Harvest)) {
			harvestBonusTile.activateEffect(player, null);
			OwnedCards ownedTerritoryCards = getSpecificOwnedCards(DevCardType.Territory);
			for (int i = 0; i < ownedTerritoryCards.getOwnedCardsnumber(); i++) {
				CraftCard tmp = (CraftCard) ownedTerritoryCards.getMyDevCards()[i].getCard();
				if(tmp!=null && actionValue >=  tmp.getRequiredValueForCraft() ) {
					tmp.callCraftEffect(player);
				}
			}
		}
	}
	
	public OwnedCards getSpecificOwnedCards(DevCardType devCardType){ //get a specific OwnedCards of the same type of the devCard 
		int i = 0;
		while (!myOwnedCards[i].getOwnedCardsType().equals(devCardType) && i < myOwnedCards.length){
			i++;
		}
		return myOwnedCards[i];	
	}


	public Player getPlayer() {
		return player;
	}


	public ArrayList<ToCallBeforeCraft> getToCallBeforeCraftEffects() {
		return toCallBeforeCraftEffects;
	}

	public void setToCallBeforeCraftEffects(ArrayList<ToCallBeforeCraft> toCallBeforeCraftEffects) {
		this.toCallBeforeCraftEffects = toCallBeforeCraftEffects;
	}

	public ArrayList<ToCallBeforePlacement> getToCallBeforePlacementEffects() {
		return toCallBeforePlacementEffects;
	}

	public void setToCallBeforePlacementEffects(ArrayList<ToCallBeforePlacement> toCallBeforePlacementEffects) {
		this.toCallBeforePlacementEffects = toCallBeforePlacementEffects;
	}

	public OwnedCards[] getMyOwnedCards() {
		return myOwnedCards;
	}


	public Possession getMyPossession() {
		return myPossession;
	}

	public void setMyPossession(Possession myPossession) {
		this.myPossession = myPossession;
	}

	
	
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public ArrayList<LeaderCard> getLeaderCards() {
		return leaderCards;
	}

	public void setLeaderCards(ArrayList<LeaderCard> leaderCards) {
		this.leaderCards = leaderCards;
	}

	public ArrayList<OncePerTurnLeaderCard> getPlayedOncePerTurnLeaderCards() {
		return playedOncePerTurnLeaderCards;
	}

	public void setPlayedOncePerTurnLeaderCards(ArrayList<OncePerTurnLeaderCard> playedOncePerTurnLeaderCards) {
		this.playedOncePerTurnLeaderCards = playedOncePerTurnLeaderCards;
	}

	@Override
	public String toString() {
		String myOwnedCardString = "";
		for (int i = 0; i < myOwnedCards.length; i++) {
			myOwnedCardString = myOwnedCardString + myOwnedCards[i].toString() + "\n";
		}
		return player.getName() + " PersonalBoard \n{myPossession=" + myPossession.toString() + "\nmy Development Cards: \n" + myOwnedCardString + "\nmy Leader Cards: " + this.leaderCards.toString() +"}";
	}

	public void refreshOncePerTurnLeaders() {
		for (int i = 0; i < playedOncePerTurnLeaderCards.size(); i++) {
			playedOncePerTurnLeaderCards.get(i).setPlayedThisTurn(false);
		}
	}
	
	


}