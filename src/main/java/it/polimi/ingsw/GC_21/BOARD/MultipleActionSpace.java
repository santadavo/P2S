package it.polimi.ingsw.GC_21.BOARD;

import java.io.Serializable;
import java.util.ArrayList;

import it.polimi.ingsw.GC_21.EFFECT.Effect;
import it.polimi.ingsw.GC_21.GAMEMANAGEMENT.Game;
import it.polimi.ingsw.GC_21.PLAYER.Color;
import it.polimi.ingsw.GC_21.PLAYER.FamilyMember;

public class MultipleActionSpace extends ActionSpace implements Serializable{
	private ArrayList<FamilyMember> familyMembersLocated = new ArrayList<FamilyMember>();

	public MultipleActionSpace(int requiredDice, Effect effect, Game game) {
		super(requiredDice, effect, game);
	}

	public ArrayList<FamilyMember> getFamilyMembers() {
		return familyMembersLocated;
	}
	
	@Override
	public void placeFamilyMember(FamilyMember familyMember) {
		if (familyMember.getOwnerPlayer().getPlayerColor() == Color.Black) {
			setBlack(true);
		}
		else {
			familyMembersLocated.add(familyMember);
		}
	}


	public void setFamilyMembers(ArrayList<FamilyMember> familyMember) {
		this.familyMembersLocated = familyMember;
	}

	
	@Override
	public String toString() {
		if (familyMembersLocated!=null){
			String string = "[familyMembers=";
			for (int i = 0; i < familyMembersLocated.size(); i++) {
				string = string  + " {" + familyMembersLocated.get(i).getOwnerPlayer().toString() + "//" + familyMembersLocated.get(i).getFamilyMemberColor().toString() + " }";
			}
			return string + "]";
		}
		else {
			return "[familyMembers=" + "//" + "]";
		}
	}

}