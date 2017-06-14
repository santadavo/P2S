package it.polimi.ingsw.GC_21.GAMEMANAGEMENT;

import it.polimi.ingsw.GC_21.EFFECT.Convert;
import it.polimi.ingsw.GC_21.GAMECOMPONENTS.PossesionCreator;
import it.polimi.ingsw.GC_21.GAMECOMPONENTS.Possession;

public class ConvertMessage extends Message {
	private Possession toPay;
	private Possession toTake;
	
	
	public ConvertMessage(Possession toPay, Possession toTake) {
		super();
		this.toPay = toPay;
		this.toTake = toTake;
	}
	
	@Override
	public boolean convert() {
		Convert convert = new Convert(controller.getModelGame(), new Possession(), toPay, toTake, new Possession(), new Possession(), 0);
		convert.activateEffect(controller.getRemoteView().getPlayer(), null);
		return true;
	}
	

}