package application;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;

public class MemoryDice extends Dice {
	private boolean disallowedIndex;
	private ImageView image;

	public MemoryDice(String value, int index) {
		super(value, Integer.toString(index));
	}

	public boolean isEqual(MemoryDice dice) {
		return this.getDiceValue() == (dice.getDiceValue()) ? true : false;
	}

	public boolean isDisallowedDice() {
		return disallowedIndex ? true : false;
	}

	public void setDisallowedDice() {
		this.disallowedIndex = true;
	}

	public void setAllowedDice() {
		this.disallowedIndex = false;
	}

	public ImageView getImageView() {
		return this.image;
	}

	public void fillImageView() {
		if (this.image == null) {
            this.image = new ImageView(getImage());
            this.image.setFitHeight(50);
            this.image.setFitWidth(50);
            this.image.setPreserveRatio(true);
        }
	}

	public void setImageView(ImageView imageView) {
		this.image = imageView;
	}

	// paint image views
	public void setChosenImageView(ImageView imageView) {
		ColorAdjust colorAdjust = new ColorAdjust(); 
		colorAdjust.setSaturation(0.16); 
	    colorAdjust.setHue(337);     
	    colorAdjust.setBrightness(0.16); 	
		imageView.setEffect(colorAdjust);
	}

	public void resetImageViewColor(ImageView imageView) {
		imageView.setEffect(new ColorAdjust(0, 0, 0, 0));
	}

	public void setImageViewSelectedColor(ImageView imageView) {
		imageView.setEffect(new ColorAdjust(0, 0, 0, -0.2));
		imageView.setEffect(new DropShadow());
	}
}
