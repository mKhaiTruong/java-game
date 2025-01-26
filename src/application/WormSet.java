package application;

import java.util.ArrayList;
import java.util.Set;

public class WormSet{

	protected Set<Worm> wormSet;
	
	protected WormSet() {}
	
	public boolean isEmpty() {
        return wormSet.isEmpty();
    }
	
	public boolean hasWorm(int value) {
//        for (Worm worm : wormSet) 
//            if (worm.getValue() == value) 
//                return true;
            
        return false;
    }
	
//	public Worm takeWorm(int value) {
//        for (Worm worm : wormSet) 
//            if (worm.getValue() == value) {
//                wormSet.remove(worm);
//                return worm;
//            }
//        
//        return null;
//    }
	
	public void addWorm(Worm worm) {
        wormSet.add(worm);
    }
	
	@Override
    public String toString() {
        String setValues = "";
        for (Worm worm : wormSet) {
            setValues += worm.getValue() + " " + worm.getWormNumber() + "\n";
        }
        return setValues;
    }
}
