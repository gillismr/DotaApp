package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.json.JSONException;
import org.json.JSONObject;

import tools.AdditiveMap;

@Entity
public class ItemFlatBonus {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Enumerated(EnumType.STRING)
	private FlatBonusType type;
	private double value;
	
	@ManyToOne
	@JoinColumn(name="ITEM_ID")
	private Item source;

	public ItemFlatBonus() {
		super();
	}

	public ItemFlatBonus(int id, FlatBonusType type, double value, Item source) {
		super();
		this.id = id;
		this.type = type;
		this.value = value;
		this.source = source;
	}
	
	public ItemFlatBonus(FlatBonusType type, double value, Item source) {
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

	public FlatBonusType getType() {
		return type;
	}

	public void setType(FlatBonusType type) {
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
	
	@SuppressWarnings("unchecked")
	public static List<ItemFlatBonus> makeBonuses(Item source, JSONObject abilitySpecial) throws JSONException {
		List<ItemFlatBonus> bonuses = new ArrayList<ItemFlatBonus>();
		AdditiveMap keyedBonuses = new AdditiveMap();
		Iterator<String> effectNumbers = abilitySpecial.keys();
		while(effectNumbers.hasNext()){
				//There are 245 unique labels for effects across all AbilitySpecial sub-elements
				//Here we account for the ones representing flat bonuses
			
				//Need special consideration for dagon and necro and diffusal, maybe just ignore those items for now
			
				JSONObject oneBonus = abilitySpecial.getJSONObject(effectNumbers.next());
				if(oneBonus.has("bonus_damage"))
					keyedBonuses.put(FlatBonusType.DMG, Double.parseDouble(oneBonus.getString("bonus_damage")));
				
				if(oneBonus.has("bonus_armor"))
					keyedBonuses.put(FlatBonusType.ARMOR, Double.parseDouble(oneBonus.getString("bonus_armor")));
				
				if(oneBonus.has("bonus_magical_armor"))
					keyedBonuses.put(FlatBonusType.MAGIC_RES, Double.parseDouble(oneBonus.getString("bonus_magical_armor")));
				if(oneBonus.has("magic_resistance"))
					keyedBonuses.put(FlatBonusType.MAGIC_RES, Double.parseDouble(oneBonus.getString("magic_resistance")));
				if(oneBonus.has("bonus_spell_resist"))
					keyedBonuses.put(FlatBonusType.MAGIC_RES, Double.parseDouble(oneBonus.getString("bonus_spell_resist")));
				
				if(oneBonus.has("bonus_evasion"))
					keyedBonuses.put(FlatBonusType.EVASION, Double.parseDouble(oneBonus.getString("bonus_evasion")));
								
				if(oneBonus.has("bonus_agility")){
					//Diffusal
					if(source.getId() == 174)
						keyedBonuses.put(FlatBonusType.AGI, 20);
					else if (source.getId() == 196)
						keyedBonuses.put(FlatBonusType.AGI, 35);
					else keyedBonuses.put(FlatBonusType.AGI, Double.parseDouble(oneBonus.getString("bonus_agility")));
				}
				
				if(oneBonus.has("bonus_intellect")){
					//Necro & Diffusal & Dagon
					if(source.getId() == 106)
						keyedBonuses.put(FlatBonusType.INT, 15);
					else if (source.getId() == 174)
						keyedBonuses.put(FlatBonusType.INT, 6);
					else if (source.getId() == 193)
						keyedBonuses.put(FlatBonusType.INT, 21);
					else if (source.getId() == 194)
						keyedBonuses.put(FlatBonusType.INT, 24);
					else if (source.getId() == 196)
						keyedBonuses.put(FlatBonusType.INT, 10);
					else if(source.getId() == 104)
						keyedBonuses.put(FlatBonusType.INT, 13);
					else if(source.getId() == 201)
						keyedBonuses.put(FlatBonusType.INT, 16);
					else if(source.getId() == 202)
						keyedBonuses.put(FlatBonusType.INT, 19);
					else if(source.getId() == 203)
						keyedBonuses.put(FlatBonusType.INT, 22);
					else if(source.getId() == 204)
						keyedBonuses.put(FlatBonusType.INT, 25);
					else keyedBonuses.put(FlatBonusType.INT, Double.parseDouble(oneBonus.getString("bonus_intellect")));
				}
				if(oneBonus.has("bonus_int"))
					keyedBonuses.put(FlatBonusType.INT, Double.parseDouble(oneBonus.getString("bonus_int")));
				
				if(oneBonus.has("bonus_strength")){
					//Necro
					if(source.getId() == 106)
						keyedBonuses.put(FlatBonusType.STR, 8);
					else if (source.getId() == 193)
						keyedBonuses.put(FlatBonusType.STR, 12);
					else if (source.getId() == 194)
						keyedBonuses.put(FlatBonusType.STR, 16);
					else keyedBonuses.put(FlatBonusType.STR, Double.parseDouble(oneBonus.getString("bonus_strength")));
				}
				
				if(oneBonus.has("bonus_all_stats")){
					keyedBonuses.put(FlatBonusType.AGI, Double.parseDouble(oneBonus.getString("bonus_all_stats")));
					keyedBonuses.put(FlatBonusType.INT, Double.parseDouble(oneBonus.getString("bonus_all_stats")));
					keyedBonuses.put(FlatBonusType.STR, Double.parseDouble(oneBonus.getString("bonus_all_stats")));
				}
				if(oneBonus.has("bonus_stats")){
					keyedBonuses.put(FlatBonusType.AGI, Double.parseDouble(oneBonus.getString("bonus_stats")));
					keyedBonuses.put(FlatBonusType.INT, Double.parseDouble(oneBonus.getString("bonus_stats")));
					keyedBonuses.put(FlatBonusType.STR, Double.parseDouble(oneBonus.getString("bonus_stats")));
				}
				
				//Powertreads
				if(oneBonus.has("bonus_stat")){
					if(source.getName().equals("item_power_treads_agi"))
						keyedBonuses.put(FlatBonusType.AGI, Double.parseDouble(oneBonus.getString("bonus_stat")));
					if(source.getName().equals("item_power_treads_int"))
						keyedBonuses.put(FlatBonusType.INT, Double.parseDouble(oneBonus.getString("bonus_stat")));
					if(source.getName().equals("item_power_treads_str"))
						keyedBonuses.put(FlatBonusType.STR, Double.parseDouble(oneBonus.getString("bonus_stat")));
				}
								
				if(oneBonus.has("bonus_attack_speed"))
					keyedBonuses.put(FlatBonusType.AS, Double.parseDouble(oneBonus.getString("bonus_attack_speed")));
				//Quarterstaff uses this label
				if(oneBonus.has("bonus_speed"))
					keyedBonuses.put(FlatBonusType.AS, Double.parseDouble(oneBonus.getString("bonus_speed")));
				
				if(oneBonus.has("bonus_movement_speed")){
					//Manta style uses this label even though it is a percentage effect
					if(source.getId() == 147)
						keyedBonuses.put(FlatBonusType.MS_PERC, Double.parseDouble(oneBonus.getString("bonus_movement_speed")));
					else
						keyedBonuses.put(FlatBonusType.MS_FLAT, Double.parseDouble(oneBonus.getString("bonus_movement_speed")));
				}
				//This label used in mana boots
				if(oneBonus.has("bonus_movement"))
					keyedBonuses.put(FlatBonusType.MS_FLAT, Double.parseDouble(oneBonus.getString("bonus_movement")));
				
				//Bottle is labeled with this effect which only applies to the courier (SnY and Yasha use it appropriately)
				if(oneBonus.has("movement_speed_percent_bonus") && source.getId() != 41)
					keyedBonuses.put(FlatBonusType.MS_PERC, Double.parseDouble(oneBonus.getString("movement_speed_percent_bonus")));
				
				//health
				if(oneBonus.has("bonus_health"))
					keyedBonuses.put(FlatBonusType.HP, Double.parseDouble(oneBonus.getString("bonus_health")));
				
				//health regen
				if(oneBonus.has("bonus_regen"))
					keyedBonuses.put(FlatBonusType.HP_REGEN_FLAT, Double.parseDouble(oneBonus.getString("bonus_regen")));
				if(oneBonus.has("bonus_health_regen"))
					keyedBonuses.put(FlatBonusType.HP_REGEN_FLAT, Double.parseDouble(oneBonus.getString("bonus_health_regen")));
				if(oneBonus.has("health_regen"))
					keyedBonuses.put(FlatBonusType.HP_REGEN_FLAT, Double.parseDouble(oneBonus.getString("health_regen")));
				if(oneBonus.has("hp_regen"))
					keyedBonuses.put(FlatBonusType.HP_REGEN_FLAT, Double.parseDouble(oneBonus.getString("hp_regen")));
				
				//health regen percentage (given value is 2, needs to be converted to % or understood)
				if(oneBonus.has("health_regen_rate"))
					keyedBonuses.put(FlatBonusType.HP_REGEN_PERC, Double.parseDouble(oneBonus.getString("health_regen_rate")));
				
				//mana
				if(oneBonus.has("bonus_mana"))
					keyedBonuses.put(FlatBonusType.MANA, Double.parseDouble(oneBonus.getString("bonus_mana")));
				
				//percentage mana regen
				if(oneBonus.has("bonus_mana_regen"))
					keyedBonuses.put(FlatBonusType.MANA_REGEN_PERC, Double.parseDouble(oneBonus.getString("bonus_mana_regen")));
				if(oneBonus.has("bonus_mana_regen_pct"))
					keyedBonuses.put(FlatBonusType.MANA_REGEN_PERC, Double.parseDouble(oneBonus.getString("bonus_mana_regen_pct")));
				if(oneBonus.has("mana_regen"))
					keyedBonuses.put(FlatBonusType.MANA_REGEN_PERC, Double.parseDouble(oneBonus.getString("mana_regen")));
				
				//flat mana bonus is just from basilius aura (and super-auras)
				
				//Damage block
				if(oneBonus.has("block_damage_melee"))
					keyedBonuses.put(FlatBonusType.BLOCK_MELEE, Double.parseDouble(oneBonus.getString("block_damage_melee")));
				if(oneBonus.has("damage_block_melee"))
					keyedBonuses.put(FlatBonusType.BLOCK_MELEE, Double.parseDouble(oneBonus.getString("damage_block_melee")));
				
				if(oneBonus.has("block_damage_ranged"))
					keyedBonuses.put(FlatBonusType.BLOCK_RANGED, Double.parseDouble(oneBonus.getString("block_damage_ranged")));
				if(oneBonus.has("damage_block_ranged"))
					keyedBonuses.put(FlatBonusType.BLOCK_RANGED, Double.parseDouble(oneBonus.getString("damage_block_ranged")));
				
				//Block chance...
				//TODO Implement a second value for ItemFlatBonus to hold block chance AND value...
				if(oneBonus.has("block_chance"))
					keyedBonuses.put(FlatBonusType.BLOCK_CHANCE, Double.parseDouble(oneBonus.getString("block_chance")));
		}
		
		for(Map.Entry<FlatBonusType, Double> onePair : keyedBonuses.entrySet()){
			double value = onePair.getValue();
			if(value !=0)
				bonuses.add(new ItemFlatBonus(onePair.getKey(), value, source));
		}
		return bonuses;
	}
	
	
}
