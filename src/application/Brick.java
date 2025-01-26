package application;

import java.util.Arrays;
import java.util.List;

import javafx.scene.image.Image;

public class Brick {
	private String value;
	private String index;
	private Worm worm;

	public Brick(String value, Worm worm, String index) {
		setValue(value);
		setWorm(worm);
		setIndex(index);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		value = value.toLowerCase();
		if (getValidValue().contains(value))
			this.value = value;
		else
			throw new IllegalArgumentException(value + " is not valid, must be one of the " + getValidValue());
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		if (getValidIndex().contains(index))
			this.index = index;
		else
			throw new IllegalArgumentException(index + " is not valid, must be one of the " + getValidIndex());
	}

	public Worm getWorm() {
		return worm;
	}

	public void setWorm(Worm worm) {
		this.worm = worm;
	}

	public static List<String> getValidWorm() {
		return Arrays.asList("1", "2", "3", "4");
	}

	public static List<String> getValidIndex() {
		return Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15");
	}

	public static List<String> getValidValue() {
		return Arrays.asList("21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35",
				"36");
	}

	public int getBrickValue() {
		return getValidValue().indexOf(value) + 21;
	}

	public int getBrickIndex() {
		return getValidIndex().indexOf(index);
	}

	public int getWormCount() {
		return getValidWorm().indexOf(String.valueOf(this.worm.getWormNumber())) + 1;
	}

	public Image getWormImage() {
		switch (getWormCount()) {
		case 1:
		case 2:
		case 3:
		case 4:
			return worm.getImage();
		}

		return null;
	}
}
