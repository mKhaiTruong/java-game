package application;

import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Effect;

public class FrostEffect extends CustEffect{
	private static Effect frostEffect;
	
	public FrostEffect(double amount) {
		super(amount);
		frostEffect = new BoxBlur(BLUR_AMOUNT, BLUR_AMOUNT, 5);
	}

	@Override
	protected Effect getEffect() {
		return frostEffect;
	}
}
