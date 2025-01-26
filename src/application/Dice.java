package application;

import java.util.Arrays;
import java.util.List;

import javafx.scene.image.Image;

public class Dice {
	private String value;
	private String path;
	private String index;

	public Dice(String value, String index) {
		setValue(value);
		setIndex(index);
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public void setValue(String value) {
		if(getValidValue().contains(value))
			this.value = value;
		else throw new IllegalArgumentException(value + " is not valid, must be one of the " + getValidValue());
	}

	public static List<String> getValidValue() {
		return Arrays.asList("1", "2", "3", "4", "5", "6");
	}
	
	public static List<String> getValidIndex() {
		return Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7");
	}
	
	public String toString() {
		return "Value " + this.value + " at " + this.index + " has been rolled";
	}
	
	public int getDiceValue() {
		return getValidValue().indexOf(this.value) + 1;
	}
	
	public int getDiceIndex() {
		return getValidIndex().indexOf(this.index);
	}
	
	public Image getImage() {
		if(Integer.parseInt(value) != 6)
			path = "/images/dice-" + this.value + ".png";
		else 
			path = "/images/One-WormTile.png";
		
		return new Image(getClass().getResource(path).toExternalForm());
	}
}
