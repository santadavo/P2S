package it.polimi.ingsw.GC_21.UTILITIES;

import it.polimi.ingsw.GC_21.ACTION.Action;
import it.polimi.ingsw.GC_21.CLIENT.MessageToClient;
import it.polimi.ingsw.GC_21.CLIENT.PrivilegeMessage;
import it.polimi.ingsw.GC_21.CONTROLLER.ControllerForm;
import it.polimi.ingsw.GC_21.PLAYER.Player;
import it.polimi.ingsw.GC_21.VIEW.ExcommInput;
import it.polimi.ingsw.GC_21.VIEW.InputForm;

public interface P2SObserver<C> {
	

	public  boolean update(C change);
	public boolean updateMessage(ControllerForm controllerForm);
	public void updateTurn();
	public void updateCurrent(MessageToClient message);
	public void updateBroadcast(MessageToClient message);
	public void updateClose();
	public void updateInit();
	public void updateSave(String string);
	public void updateBlack();
	public void updateBlackSwitch(Player playerToSwitch);





		
}
