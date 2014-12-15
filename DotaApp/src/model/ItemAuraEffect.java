package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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


@Entity
public class ItemAuraEffect {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Enumerated(EnumType.STRING)
	private AuraType type;
	private boolean affectsEnemies;
	private double value;
	
	@ManyToOne
	@JoinColumn(name="AURA_ID")
	private ItemAura aura;
	
	public ItemAuraEffect() {
		super();
	}

	public ItemAuraEffect(int id, AuraType type, boolean affectsEnemies,
			double value, ItemAura aura) {
		super();
		this.id = id;
		this.type = type;
		this.affectsEnemies = affectsEnemies;
		this.value = value;
		this.aura = aura;
	}
	
	public ItemAuraEffect(AuraType type, boolean affectsEnemies,
			double value, ItemAura aura) {
		super();
		this.type = type;
		this.affectsEnemies = affectsEnemies;
		this.value = value;
		this.aura = aura;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public AuraType getType() {
		return type;
	}

	public void setType(AuraType type) {
		this.type = type;
	}

	public boolean isAffectsEnemies() {
		return affectsEnemies;
	}

	public void setAffectsEnemies(boolean affectsEnemies) {
		this.affectsEnemies = affectsEnemies;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public ItemAura getAura() {
		return aura;
	}

	public void setAura(ItemAura aura) {
		this.aura = aura;
	}

	@SuppressWarnings("unchecked")
	public static List<ItemAuraEffect> makeEffects(
			ItemAura source,
			JSONObject data) throws JSONException {	
		List<ItemAuraEffect> effects = new ArrayList<ItemAuraEffect>();
		Iterator<String> effectNumbers = data.keys();
		while(effectNumbers.hasNext()){
			JSONObject oneEffect = data.getJSONObject(effectNumbers.next());
			if(oneEffect.has("vampiric_aura"))
				effects.add(new ItemAuraEffect(AuraType.LIFESTEAL, false, Double.parseDouble(oneEffect.getString("vampiric_aura")), source));
			if(oneEffect.has("damage_aura"))
				effects.add(new ItemAuraEffect(AuraType.DMG_PERC, false, Double.parseDouble(oneEffect.getString("damage_aura")), source));
			
			if(oneEffect.has("armor_aura"))
				effects.add(new ItemAuraEffect(AuraType.ARMOR, false, Double.parseDouble(oneEffect.getString("armor_aura")), source));
			if(oneEffect.has("aura_bonus_armor"))
				effects.add(new ItemAuraEffect(AuraType.ARMOR, false, Double.parseDouble(oneEffect.getString("aura_bonus_armor")), source));
			if(oneEffect.has("aura_positive_armor"))
				effects.add(new ItemAuraEffect(AuraType.ARMOR, false, Double.parseDouble(oneEffect.getString("aura_positive_armor")), source));
			if(oneEffect.has("aura_negative_armor"))
				effects.add(new ItemAuraEffect(AuraType.ARMOR, true, Double.parseDouble(oneEffect.getString("aura_negative_armor")), source));
						
			if(oneEffect.has("mana_regen_aura"))
				effects.add(new ItemAuraEffect(AuraType.MANA_REGEN_FLAT, false, Double.parseDouble(oneEffect.getString("mana_regen_aura")), source));
			if(oneEffect.has("aura_mana_regen"))
				effects.add(new ItemAuraEffect(AuraType.MANA_REGEN_FLAT, false, Double.parseDouble(oneEffect.getString("aura_mana_regen")), source));
			
			if(oneEffect.has("aura_health_regen"))
				effects.add(new ItemAuraEffect(AuraType.HP_REGEN_FLAT, false, Double.parseDouble(oneEffect.getString("aura_health_regen")), source));
			
			
			if(oneEffect.has("aura_attack_speed")){
				double value = Double.parseDouble(oneEffect.getString("aura_attack_speed"));
				if(value > 0)
					effects.add(new ItemAuraEffect(AuraType.AS, false, value, source));
				else
					effects.add(new ItemAuraEffect(AuraType.AS, true, value, source));
			}
			if(oneEffect.has("bonus_aura_attack_speed_pct"))
				effects.add(new ItemAuraEffect(AuraType.AS, false, Double.parseDouble(oneEffect.getString("bonus_aura_attack_speed_pct")), source));
			
			if(oneEffect.has("aura_damage"))
				effects.add(new ItemAuraEffect(AuraType.DPS, true, Double.parseDouble(oneEffect.getString("aura_damage")), source));
			
			if(oneEffect.has("bonus_aura_movement_speed_pct"))
				effects.add(new ItemAuraEffect(AuraType.MS_PERC, false, Double.parseDouble(oneEffect.getString("bonus_aura_movement_speed_pct")), source));
		}
		
		return effects;
	}
	
	
	
}
