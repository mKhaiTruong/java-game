package application;

import javafx.scene.image.Image;

public class Worm implements Comparable<Worm>{
	public static final int WORM_INCREMENT = 4;
    public static final int MIN_BRICK = 21;
    public static final int MAX_BRICK = 36;
	private String value;
	private String path;
	private int wormNumber;
	private int sum = 0;
	private boolean outOfGame = false;
	
	public Worm(String value) {
		setValue(value);
		wormNumber = ((Integer.parseInt(value) - MIN_BRICK)/WORM_INCREMENT) + 1;
	}

	public String getValue() {
		return value;
	}
	public int getWormNumber() {
		return wormNumber;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public boolean isOutOfGame() {
        return outOfGame;
    }

    public void setOutOfGame(boolean outOfGame) {
        this.outOfGame = outOfGame;
    }
    
	public int getSum() {
		return sum;
	}
	
	public void addWorm(int sum) {
		this.sum += sum;
	}

	@Override
	public int compareTo(Worm o) {
		if (equals(o)) {
            return 0;
        }
        return Integer.compare(Integer.parseInt(value), Integer.parseInt(o.getValue()));
	}
	
    @Override
    public String toString() {
        return "Worm{" +
                "value=" + value +
                ", wormCount=" + wormNumber +
                '}';
    }
    
    public Image getImage() {
		switch(wormNumber) {
			case 1:
				path = "/images/One-WormTile.png";
				break;
			case 2:
				path = "/images/Two-WormTile.png";
				break;
			case 3:
				path = "/images/Three-WormTile.png";
				break;
			case 4:
				path = "/images/Four-WormTile.png";
				break;
		}
		
		return new Image(getClass().getResource(path).toExternalForm());
	}
}
