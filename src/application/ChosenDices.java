package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChosenDices{
	private Map<Integer, MemoryDice> chosenDices;

	public ChosenDices() {
		this.chosenDices = new HashMap<>();
	}
	
	public Map<Integer, MemoryDice> getChosenDices() {
		return chosenDices;
	}
	
	public void clearDices() {
		this.chosenDices.clear();
	}
	
	public int getSize() {
		return this.chosenDices.size();
	}
	
	public MemoryDice getMemoryDice(int index) {
		return this.chosenDices.get(index);
	}
	
	public int getMemoryDiceValue(int index) {
		return this.chosenDices.get(index).getDiceValue();
	}
	
	public int getMemoryDiceIndex(int index) {
		return getMemoryDice(index).getDiceIndex();
	}
	
	public void addMemoryDice(int index, MemoryDice dice) {
		dice.setDisallowedDice();
		this.chosenDices.put(index, dice);
	}
	
	public boolean checkIfValueIsAlreadyInChosenDicesOrNot(int value) {
		for (Integer index : this.chosenDices.keySet()) {
			if(value == chosenDices.get(index).getDiceValue())
				return true;
		}
		
		return false;
	}

	public void removeMemoryDice(Integer indexToRemove) {
		this.chosenDices.remove(indexToRemove);
	}
	
	public ArrayList<MemoryDice> returnListOfChosenValues() {
		ArrayList<MemoryDice> list = new ArrayList<>();
	    
	    for (MemoryDice chosenDice : chosenDices.values()) {
	        list.add(chosenDice);
	    }

	    return list;
	}

}

