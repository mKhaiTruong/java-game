package application;

import javafx.scene.image.Image;

public class Player implements Comparable<Player> {
	protected String name;
	protected Image image;
	private PlayerHand playerHand;
	protected String path;
	private int tried;
	private int wormCount;
	private int wormCollected = 0;
	
	//display point to the player's turn
	protected int point;

	public Player(String name, int tried, int wormCount, int wormCollected) {
		this.name = name;
		this.tried = tried;
		this.wormCount = wormCount;
		this.wormCollected = wormCollected;
		playerHand = new PlayerHand();
	}

	public Player() {
		playerHand = new PlayerHand();
	}
	
	public PlayerHand getPlayerHand() {
		return this.playerHand;
	}
	
	public void addBrickToPlayerHand(Brick brick) {
		playerHand.addBrickToPlayerHand(brick);
		System.out.println("Worm count = " + brick.getWormCount());
		addPoint(brick.getWormCount());
	}
	
	public void addInfoToFinalTable(Player player, ScoreController sc) {
		for (int i = 0; i < player.getPlayerHand().getPlayerHandSize(); i++)
			sc.setData(i + 1, player.getName(), player.getPlayerHand().getBrick(i).getWormCount());
		
		sc.update();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTried() {
		return tried;
	}
	
	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}
	
	public void addPoint(int point) {
		this.point += point;
	}

	public void minusPoint(int point) {
		this.point -= point;
	}
	
	public void setTried(int tried) {
		this.tried = tried;
	}

	public int getWormCount() {
		return wormCount;
	}

	public void setWormCount(int wormCount) {
		this.wormCount = wormCount;
	}

	public int getWormCollected() {
		return this.wormCollected;
	}
	
	public void setWormCollected(int wormCollected) {
		this.wormCollected = wormCollected;
	}
	
	public void addWormCollected(int n) {
		this.wormCollected += n;
	}
	
	public void minusWormCollected(int n) {
		this.wormCollected -= n;
	}

	@Override
	public String toString() {
		return "This Player's name is " + name + "."
				+ " and currently " + name + " has " + wormCollected;
	}
	
	public Image getImage() {
		return this.image;
	}
	
	public void setImage(Image image) {
		this.image = image;
	}

	//interact with the brick that is on top of player's hand
	public Brick getOnTopBrick() {
		if(playerHand.getPlayerHandSize() != 0)
			return playerHand.getBrick(playerHand.getPlayerHandSize() - 1);
		else 
			return null;
	}
	
	public void removeOnTopBrick() {
		playerHand.removeBrickFromHand(playerHand.getPlayerHandSize() - 1);
	}
	
	public String getOnTopBrickValueStr() {
		return Integer.toString(getOnTopBrick().getBrickValue());
	}
	
	public int getOnTopBrickValueInt() {
		if(getOnTopBrick() == null)
			return 0;
		else 
			return getOnTopBrick().getBrickValue();
	}
	
	public Image getOnTopBrickImage() {
		return getOnTopBrick().getWormImage();
	}
	
	@Override
    public int compareTo(Player otherPlayer) {
        // Compare players based on points
        return Integer.compare(this.point, otherPlayer.point);
    }
}
