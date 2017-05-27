package it.polimi.ingsw.GC_21.GAMECOMPONENTS;

import it.polimi.ingsw.GC_21.BOARD.CardPlace;
import it.polimi.ingsw.GC_21.BOARD.DevCardPlace;

public class DevelopmentCard extends Card {
	
	private int age;
	private DevCardType devCardType;
	private int craftValue;

	
	public DevelopmentCard(String name) {
		super(name);
	
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}


	public int getCraftValue() {
		return craftValue;
	}


	public void setCraftValue(int craftValue) {
		this.craftValue = craftValue;
	}


	public DevCardType getDevCardType() {
		return devCardType;
	}


	public void setDevCardType(DevCardType devCardType) {
		this.devCardType = devCardType;
	}



	@Override
	public String toString() {
		super.toString();
		return "DevelopmentCard [age=" + age + ", devCardType=" + devCardType + "]";
	}
	


}