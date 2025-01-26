package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bot extends Player{
	public Image getImage() {
		switch (name) {
		case "bot":
			path = "/images/bot.png";
			break;
		}

		return new Image(getClass().getResource(path).toExternalForm());
	}
	
	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// Bot's functions
	private boolean lose = true;
	private boolean otherPlayersHaveBrick = false;
	private boolean steal = false;
	private ArrayList<Integer> diceValues;
	private ArrayList<Integer> notAllowedValue;
	private ArrayList<Integer> onTopBrickValueOfOtherPlayers;
	private Map<Integer, Integer> cumulativePoints;
	private Map<Integer, Integer> potentialPointsToStealBrickFromOthers;

	public void updateOtherPlayerOnTopBrickValue(ArrayList<Integer> others) {
		//update the bot's knowledge about other players' bricks
		onTopBrickValueOfOtherPlayers = others;
		steal = false;
		
		//make sure that other players having one ontop brick first
		for (int value : onTopBrickValueOfOtherPlayers) {
            if(value != 0) 
            	otherPlayersHaveBrick = true;     	
            
        }
	}

	public void updateBotDiceValue(TableOfDices table, ArrayList<Integer> notAllowedValue) {
		this.notAllowedValue = notAllowedValue;
		steal = false;
		diceValues = new ArrayList<>();
		
		for (int i = 0; i < table.getSize(); i++) {
			MemoryDice currentDice = table.getMemoryDice(i);

			if (!currentDice.isDisallowedDice()) 
				diceValues.add(currentDice.getDiceValue());
			
		}

		//calculate both cumulativePoints and potential points to steal
		cumulativePoints = calculateCumulativeValues(diceValues);
		potentialPointsToStealBrickFromOthers = calculatePotentialPointsToStealBrickFromOthers(cumulativePoints,
				this.point);

		//in case of dice having been frozen, remove said dice
		for (int j = 0; j < this.notAllowedValue.size(); j++) {
			cumulativePoints.put(this.notAllowedValue.get(j), -1);
			potentialPointsToStealBrickFromOthers.put(this.notAllowedValue.get(j), -1);
		}

		//check if the robot loses or not
		this.lose = checkFail();
	}

	private boolean checkFail() {
		boolean lose = true;
		for (Map.Entry<Integer, Integer> entry : cumulativePoints.entrySet()) {
//			System.out.println("Value " + entry.getKey() + ": Cumulative: " + entry.getValue());
			if (entry.getValue() != -1 && entry.getValue() != 0)
				lose = false;
		}

		if(lose) System.out.println("So ashamed of myself");
		else System.out.println();
		
		return lose;
	}

	private static Map<Integer, Integer> calculateCumulativeValues(ArrayList<Integer> diceValues) {
		Map<Integer, Integer> cumulativeValues = new HashMap<>();

		int sum = 0;
		for (int i = 0; i < 6; i++) {
			sum = 0;
			for (int value : diceValues) {
				if (value == i + 1) {
					sum += value;
					if (value == 6)
						sum--;
				}
			}
			cumulativeValues.put(i + 1, sum);
		}

		return cumulativeValues;
	}

	private static Map<Integer, Integer> calculatePotentialPointsToStealBrickFromOthers(
			Map<Integer, Integer> cumulative, int point) {
		Map<Integer, Integer> potentialPointsToStealBrick = new HashMap<>();

		int sum = 0;
		for (int i = 0; i < 6; i++) {
			sum = 0;
			sum = point + cumulative.get(i + 1);

			potentialPointsToStealBrick.put(i + 1, sum);
		}

		return potentialPointsToStealBrick;
	}

	public int chooseDice() {
		if (this.lose) 
			return -1;

		//case1: other players having onTopBrick, then the priority is to take that from them
		int chosenDie = -1;
		if (otherPlayersHaveBrick) {
			for (int value : onTopBrickValueOfOtherPlayers) {
				for (Map.Entry<Integer, Integer> entry : potentialPointsToStealBrickFromOthers.entrySet()) {
					System.out.println("Value " + entry.getKey() + ": potentialPoints: " + entry.getValue());
					if (value == entry.getValue()) {
						chosenDie = entry.getKey();
						steal = true;
						break;
					}
				}
				
				if (chosenDie != -1) {
					break;
				}
			}
		}

		//case 2: there is no onTop brick from others, then the robot simply searches for 
		//highest cumulative points
		if (chosenDie == -1) {
			int maxCumVal = Integer.MIN_VALUE;
			for (int i = 0; i < 6; i++) {
				maxCumVal = Math.max(maxCumVal, cumulativePoints.get(i + 1));
			}
			for (int i = 0; i < 6; i++) {
				if (maxCumVal == cumulativePoints.get(i + 1))
					chosenDie = i + 1;
			}
		}

//		System.out.println("Chosen Die is " + chosenDie);
//		System.out.println();
		
		return chosenDie;
	}
	
	public boolean getSteal() {
		return steal;
	}
}
