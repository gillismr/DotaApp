package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.json.JSONException;
import org.json.JSONObject;

@Entity
public class ItemFlatBonus {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String type;
	private double value;
	
	@ManyToOne
	@JoinColumn(name="ITEM_ID")
	private Item source;

	public ItemFlatBonus() {
		super();
	}

	public ItemFlatBonus(int id, String type, double value, Item source) {
		super();
		this.id = id;
		this.type = type;
		this.value = value;
		this.source = source;
	}
	
	public ItemFlatBonus(String type, double value, Item source) {
		super();
		this.type = type;
		this.value = value;
		this.source = source;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public Item getSource() {
		return source;
	}

	public void setSource(Item source) {
		this.source = source;
	}

	public static List<ItemFlatBonus> makeBonuses(Item source, JSONObject abilitySpecial) throws JSONException {
		List<ItemFlatBonus> bonuses = new ArrayList<ItemFlatBonus>();
		HashMap<String, Double> keyedBonuses = new HashMap<String, Double>();
		@SuppressWarnings("unchecked")
		Iterator<String> effectNumbers = abilitySpecial.keys();
		while(effectNumbers.hasNext()){
				JSONObject oneBonus = abilitySpecial.getJSONObject(effectNumbers.next());
				if(oneBonus.has("bonus_damage"))
					keyedBonuses.put("DMG", Double.parseDouble(oneBonus.getString("bonus_damage")));
				if(oneBonus.has("bonus_armor"))
					keyedBonuses.put("ARMOR", Double.parseDouble(oneBonus.getString("bonus_armor")));
				
				if(oneBonus.has("bonus_magic_armor"))
					keyedBonuses.put("MAGIC_RES", Double.parseDouble(oneBonus.getString("bonus_magic_armor")));
				if(oneBonus.has("magic_resistance"))
					keyedBonuses.put("MAGIC_RES", Double.parseDouble(oneBonus.getString("magic_resistance")));
				if(oneBonus.has("bonus_spell_resist"))
					keyedBonuses.put("MAGIC_RES", Double.parseDouble(oneBonus.getString("bonus_spell_resist")));
				
				//TODO Implement some kind of additive hashmap that will add to the value of a certain key if that key already exists with a value
				
				if(oneBonus.has("bonus_attack_speed"))
					keyedBonuses.put("AS", Double.parseDouble(oneBonus.getString("bonus_attack_speed")));
				if(oneBonus.has("bonus_movement_speed"))
					keyedBonuses.put("MS", Double.parseDouble(oneBonus.getString("bonus_movement_speed")));
				//Etc...
				/*
				if(oneBonus.has("bonus_damage"))
					keyedBonuses.put("DMG", Double.parseDouble(oneBonus.getString("bonus_damage")));
				if(oneBonus.has("bonus_damage"))
					keyedBonuses.put("DMG", Double.parseDouble(oneBonus.getString("bonus_damage")));
				if(oneBonus.has("bonus_damage"))
					keyedBonuses.put("DMG", Double.parseDouble(oneBonus.getString("bonus_damage")));
				*/
		}
		
		for(Map.Entry<String, Double> onePair : keyedBonuses.entrySet())
			bonuses.add(new ItemFlatBonus(onePair.getKey(), onePair.getValue(), source));
		return bonuses;
			
	}
	
	
}
