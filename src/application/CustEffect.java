package application;

import javafx.scene.effect.Effect;

public abstract class CustEffect {
	protected double BLUR_AMOUNT;
	
	public CustEffect(double amount) {
		BLUR_AMOUNT = amount;
	}
	
	protected abstract Effect getEffect();
}
