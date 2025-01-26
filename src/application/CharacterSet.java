package application;

import java.util.ArrayList;

public class CharacterSet {
	protected ArrayList<Character> cs;

	public CharacterSet() {
		createCharacterSet();
	}
	
	public int getNumOfChracters() { return cs.size(); }
	
	public void createCharacterSet() {
		cs = new ArrayList<>();
	}
	public void addCharacter(Character c) {
		cs.add(c);
	}
	public Character getCharacter(int i) {
		return cs.get(i);
	}
	public void clearCharacters() { cs.clear(); }
}
