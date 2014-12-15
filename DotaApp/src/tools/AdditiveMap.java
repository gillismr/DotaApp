package tools;

import java.util.HashMap;
import java.util.Map;

import model.FlatBonusType;

public class AdditiveMap extends HashMap<FlatBonusType, Double> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AdditiveMap() {
		// TODO Auto-generated constructor stub
	}

	public AdditiveMap(int arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public AdditiveMap(Map<? extends FlatBonusType, ? extends Double> arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public AdditiveMap(int arg0, float arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}
	
	public Double put(FlatBonusType key, Double value){
		if(this.containsKey(key)){
			double initValue = super.remove(key);
			initValue += value;
			return super.put(key, initValue);
		}
		else
			return super.put(key, value);
		
	}
	
	public Double put(FlatBonusType key, int value){
		if(this.containsKey(key)){
			double initValue = super.remove(key);
			initValue += value;
			return super.put(key, initValue);
		}
		else
			return super.put(key, (double) value);
		
	}
	/*
	public static void main(String[] args) {
		//Testing:
		AdditiveMap myMap = new AdditiveMap();
		myMap.put(FlatBonusType.AGI, 10);
		myMap.put(FlatBonusType.STR, 10);
		myMap.put(FlatBonusType.INT, 10);
		
		myMap.put(FlatBonusType.AGI, 10);
		myMap.put(FlatBonusType.STR, 10);
		myMap.put(FlatBonusType.INT, 10);
		
		System.out.println("Strength: " + myMap.get(FlatBonusType.STR));
		System.out.println("Agility: " + myMap.get(FlatBonusType.AGI));
		System.out.println("Intelligence: " + myMap.get(FlatBonusType.INT));
	}
	*/

}
