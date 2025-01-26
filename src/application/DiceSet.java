package application;

import java.util.ArrayList;
import java.util.Collections;

public class DiceSet {
	protected ArrayList<MemoryDice> diceSet;

	public DiceSet() {
		createMemoryDiceSet();
	}
	
	public void shuffle() { Collections.shuffle(diceSet); }
	
	public Dice getTopDice() { 
		if(diceSet.size() > 0)
			return diceSet.remove(0); 
		else return null;
	}
	
	public int getNumOfDiceFaces() { return diceSet.size(); }
	
	public void createMemoryDiceSet() {
		diceSet = new ArrayList<>();
	}
	public void addMemoryDice(MemoryDice dice) {
		diceSet.add(dice);
	}
	public void clearMemoryDices() { diceSet.clear(); }
}
