package application;

import java.util.Arrays;
import java.util.List;

import javafx.scene.image.Image;

public class Character {
	private String name;
	private String path;
	private String index;

	public Character(String index) {
		setIndex(index);
		setName(getValidCharacter().get(Integer.parseInt(index)));
	}
	
	public String getIndex() {
		return index;
	}
	
	public void setIndex(String index) {
		if(getValidIndex().contains(index))
			this.index = index;
		else throw new IllegalArgumentException(index + " is not valid, must be one of the " + getValidIndex());
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		if(getValidCharacter().contains(name))
			this.name = name;
		else throw new IllegalArgumentException(name + " is not valid name, must be one of the " + getValidCharacter());
	
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	public static List<String> getValidCharacter() {
		return Arrays.asList("chick 1", "chick 2", "chick 3", "yanfei");
	}
	
	public static List<String> getValidIndex() {
		return Arrays.asList("0", "1", "2", "3");
	}
	
	public String toString() {
		return "Character's name " + this.name + " has been chosen";
	}
	
	public Image getImage() {
		switch(index) {
			case "0":
				path = "/images/PickominoBoxCoverImageFirstPlayer.png";
				break;
			case "1":
				path = "/images/PickominoBoxCoverImageSecondPlayer.png";
				break;
			case "2":
				path = "/images/PickominoBoxCoverImageThirdPlayer.png";
				break;
			case "3":
				path = "/images/yanfei.jpg";
				break;
		}
		
		return new Image(getClass().getResource(path).toExternalForm());
	}
}
