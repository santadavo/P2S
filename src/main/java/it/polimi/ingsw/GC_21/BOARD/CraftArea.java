package it.polimi.ingsw.GC_21.BOARD;

import it.polimi.ingsw.GC_21.EFFECT.Effect;
import it.polimi.ingsw.GC_21.EFFECT.Immediate;
import it.polimi.ingsw.GC_21.PLAYER.FamilyMember;

public class CraftArea {
	
	private final CraftType craftType;
	private final SingleActionSpace singleActionSpace;
	private final MultipleActionSpace multipleActionSpace;
	
	public CraftArea(CraftType craftType, Immediate MalusOnCraft) {
		super();
		this.craftType = craftType;
		this.singleActionSpace = new SingleActionSpace(1, null);
		this.multipleActionSpace = new MultipleActionSpace(1, MalusOnCraft);
	}

	public void checkFamilyMember() {
		// TODO - implement CraftArea.checkFamilyMember
		throw new UnsupportedOperationException();
	}

	public void callBoardCraftEffect() {
		// TODO - implement CraftArea.callBoardCraftEffect
		throw new UnsupportedOperationException();
	}

	public CraftType getCraftType() {
		return craftType;
	}

	public SingleActionSpace getSingleActionSpace() {
		return singleActionSpace;
	}


	public MultipleActionSpace getMultipleActionSpace() {
		return multipleActionSpace;
	}


}