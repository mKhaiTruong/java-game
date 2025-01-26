package application;

import java.util.ArrayList;

public class PlayerHand {
	private ArrayList<Brick> playerHand;

	public PlayerHand() {
		playerHand = new ArrayList<>();
	}

	public void addBrickToPlayerHand(Brick brick) {
		playerHand.add(brick);
	}

	public void removeBrickFromHand(int index) {
		playerHand.remove(index);
	}
	
	public Brick getBrick(int index) {
		return playerHand.get(index);
	}

	public int getBrickValue(int index) {
		return playerHand.get(index).getBrickValue();
	}

	public int getPlayerHandSize() {
		return playerHand.size();
	}
}
