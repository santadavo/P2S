package it.polimi.ingsw.GC_21.BOARD;

import java.util.ArrayList;

import it.polimi.ingsw.GC_21.GAMECOMPONENTS.Card;
import it.polimi.ingsw.GC_21.GAMECOMPONENTS.DevCardType;
import it.polimi.ingsw.GC_21.GAMECOMPONENTS.DevDeck;
import it.polimi.ingsw.GC_21.GAMECOMPONENTS.FamilyMemberColor;

public class Board {

	private Dice[] dices;
	private Tower[] towers;
	private MarketArea marketArea;
	private CouncilPalace councilPalace;
	private CraftArea[] craftAreas;
	
	public void generateBoard(DevDeck territoryDeck, DevDeck buildingDeck, DevDeck characterDeck, DevDeck ventureDeck){
		dices = Dice.generateDices();
		towers = Tower.generateTowers();
		towers[0].pickCards(territoryDeck);
		towers[1].pickCards(buildingDeck);
		towers[2].pickCards(characterDeck);
		towers[3].pickCards(ventureDeck);
	}



	public void refreshBoard() {
		for (int i = 0; i < towers.length; i++) {
			for (int j = 0; j < towers[i].getFloors().length; j++) {
				towers[i].getFloors()[j].getSingleActionSpace().setFamilyMember(null);;
			}
		}
		for (int i = 0; i < towers.length; i++) {
			for (int j = 0; j < towers[i].getFloors().length; j++) {
				towers[i].getFloors()[j].getDevCardPlace().setCard(null);
			}
		}	
		for (int j = 0; j < craftAreas.length; j++) {
			for (int i = 0; i < craftAreas[i].getMultipleActionSpace().getFamilyMember().size(); i++) {
				craftAreas[i].getMultipleActionSpace().getFamilyMember().remove(i);		

			}
			craftAreas[j].getSingleActionSpace().setFamilyMember(null);
		}
		for (int i = 0; i < councilPalace.getMultipleActionSpace().getFamilyMember().size(); i++) {
			councilPalace.getMultipleActionSpace().getFamilyMember().remove(i);
			
		}
		for (int i = 0; i < marketArea.getSingleActionSpace().length; i++) {
			marketArea.getSingleActionSpace()[i].setFamilyMember(null);
		}
		
		for (int j = 0; j < dices.length; j++) {
			//<todo>
		}
		
	
	}

	
}