package application;

import java.util.ArrayList;

public class FrozenDices {
	private ArrayList<MemoryDice> frozenDices;

	public FrozenDices() {
		this.frozenDices = new ArrayList<>();
	}
	
	public ArrayList<MemoryDice> getFrozenDices() {
		return frozenDices;
	}

	public void addFrozenDie(MemoryDice dice) {
		this.frozenDices.add(dice);
	}
	
	public void removeFrozenDie(MemoryDice dice) {
		this.frozenDices.remove(dice);
	}
	
	public void clearDices() {
		this.frozenDices.clear();
	}
	
	public boolean checkSameValue(MemoryDice die) {
		for(MemoryDice dice: frozenDices)
			if(die.getDiceValue() == dice.getDiceValue())
				return true;
		
		return false;
	}
}
