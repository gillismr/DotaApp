package model;

import java.util.Iterator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.json.JSONException;
import org.json.JSONObject;

@Entity
public class ItemAbility {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Lob
	private String otherData;
	private String abilityTags;
	private String abilityBehavior; //
	private int manaCost; // 
	//use -1 for global
	private int castRange; //
	private boolean aoe;
	private int radius;
	private double cooldown; //
	private double duration;
	
	@OneToOne(optional = false, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name="ITEM_ID")
	private Item source;
	
	
	@OneToMany(mappedBy="source")
	private List<ItemSummon> summons;
	
	public ItemAbility() {
		super();
	}

	public ItemAbility(int id, String rawEffects,
			String abilityTags, String behaviorType, int manaCost, int range,
			boolean aoe, int radius, double cooldown, double duration,
			Item source) {
		super();
		this.id = id;
		this.otherData = rawEffects;
		this.abilityTags = abilityTags;
		this.abilityBehavior = behaviorType;
		this.manaCost = manaCost;
		this.castRange = range;
		this.aoe = aoe;
		this.radius = radius;
		this.cooldown = cooldown;
		this.duration = duration;
		this.source = source;
	}


	public ItemAbility(String rawEffects,
			String abilityTags, String behaviorType, int manaCost, int range,
			boolean aoe, int radius, double cooldown, double duration,
			Item source) {
		super();
		this.otherData = rawEffects;
		this.abilityTags = abilityTags;
		this.abilityBehavior = behaviorType;
		this.manaCost = manaCost;
		this.castRange = range; 
		this.aoe = aoe;
		this.radius = radius; 
		this.cooldown = cooldown; 
		this.duration = duration;
		this.source = source; 
	}

	
	@SuppressWarnings("unchecked")
	public ItemAbility(Item source, JSONObject data) throws JSONException {
		this.source = source;
		this.abilityBehavior = data.getString("AbilityBehavior");
		JSONObject abilitySpecial = data.getJSONObject("AbilitySpecial");
		
		//cooldown, manaCost, and castRange are all accessible at the item level
		
		if(data.has("AbilityCooldown")){
			//Dagon
			if(source.getId() == 104)
				this.cooldown = 35.0;
			else if(source.getId() == 201)
				this.cooldown = 30.0;
			else if(source.getId() == 202)
				this.cooldown = 25.0;
			else if(source.getId() == 203)
				this.cooldown = 20.0;
			else if(source.getId() == 204)
				this.cooldown = 15.0;
			//BKB with it's average cooldown
			//TODO Implement multiple BKB items/level?
			else if(source.getId() == 116)
				this.cooldown = 67.5;
			else this.cooldown = Double.parseDouble(data.getString("AbilityCooldown"));
		}
		else this.cooldown = 0;
		
		if(data.has("AbilityManaCost"))
			this.manaCost= Integer.parseInt(data.getString("AbilityManaCost"));
		else this.manaCost = 0;
		
		if(data.has("AbilityCastRange")){
			//Dagon
			if(source.getId() == 104)
				this.castRange = 600;
			else if(source.getId() == 201)
				this.castRange = 650;
			else if(source.getId() == 202)
				this.castRange = 700;
			else if(source.getId() == 203)
				this.castRange = 750;
			else if(source.getId() == 204)
				this.castRange = 800;
			else this.castRange = Integer.parseInt(data.getString("AbilityCastRange"));
		}
		else this.castRange = 0;
		
		//TODO
		//Still need to set otherData and abilityTags 
		
		//aoe, radius, and duration are found at the ability special level, if they exist		
		//default them to 0 and false
		this.duration = 0;
		this.aoe = false;
		this.radius = 0;
		Iterator<String> effectNumbers = abilitySpecial.keys();
		while(effectNumbers.hasNext()){
			JSONObject oneEffect = abilitySpecial.getJSONObject(effectNumbers.next());
			
			//duration
			if(oneEffect.has("bonus_aoe_duration"))
				this.duration = Double.parseDouble(oneEffect.getString("bonus_aoe_duration"));
			else if(oneEffect.has("phase_duration")){
				this.duration = Double.parseDouble(oneEffect.getString("phase_duration"));
				this.abilityTags = this.abilityTags + ";haste";

			}
			else if(oneEffect.has("summon_duration")){
				this.duration = Double.parseDouble(oneEffect.getString("summon_duration"));
				this.abilityTags = this.abilityTags + ";summon";
			}
			else if(oneEffect.has("stun_duration")){
				this.duration = Double.parseDouble(oneEffect.getString("stun_duration"));
				this.abilityTags = this.abilityTags + ";stun";
			}
			else if(oneEffect.has("maim_duration")){
				this.duration = Double.parseDouble(oneEffect.getString("maim_duration"));
				this.abilityTags = this.abilityTags + ";slow";
			}
			else if(oneEffect.has("unholy_duration"))
				this.duration = Double.parseDouble(oneEffect.getString("unholy_duration"));
			
			else if(oneEffect.has("sheep_duration")){
				this.duration = Double.parseDouble(oneEffect.getString("sheep_duration"));
				this.abilityTags = this.abilityTags + ";hex";
			}
			//Two effects of Skadi...
			else if(oneEffect.has("cold_duration_ranged")){
				this.duration = Double.parseDouble(oneEffect.getString("cold_duration_ranged"));
				this.abilityTags = this.abilityTags + ";slow";
			}
			/*
			if(oneEffect.has("cold_duration_melee")){
				this.duration = Double.parseDouble(oneEffect.getString("cold_duration_melee"));
				this.abilityTags = this.abilityTags + ";slow";
			}
			*/
			
			else if(oneEffect.has("blast_debuff_duration")){
				this.duration = Double.parseDouble(oneEffect.getString("blast_debuff_duration"));
				this.abilityTags = this.abilityTags + ";slow";
			}
			else if(oneEffect.has("resist_debuff_duration")){
				this.duration = Double.parseDouble(oneEffect.getString("resist_debuff_duration"));
				this.abilityTags = this.abilityTags + ";mgc_amp";
			}
			else if(oneEffect.has("cyclone_duration")){
				this.duration = Double.parseDouble(oneEffect.getString("cyclone_duration"));
				this.abilityTags = this.abilityTags + ";banish";
			}
			else if(oneEffect.has("buff_duration")){
				this.duration = Double.parseDouble(oneEffect.getString("buff_duration"));
				this.abilityTags = this.abilityTags + ";potion";
			}
			else if(oneEffect.has("windwalk_duration")){
				this.duration = Double.parseDouble(oneEffect.getString("windwalk_duration"));
				this.abilityTags = this.abilityTags + ";invis;haste";
			}
			else if(oneEffect.has("heal_armor_duration")){
				this.duration = Double.parseDouble(oneEffect.getString("heal_armor_duration"));
				this.abilityTags = this.abilityTags + ";heal;armor";
			}
			else if(oneEffect.has("soul_damage_duration")){
				this.duration = Double.parseDouble(oneEffect.getString("soul_damage_duration"));
				this.abilityTags = this.abilityTags + ";heal;nuke";
			}
			else if(oneEffect.has("silence_duration")){
				this.duration = Double.parseDouble(oneEffect.getString("silence_duration"));
				this.abilityTags = this.abilityTags + ";silence;dmg_amp";
			}
			else if(oneEffect.has("tooltip_illusion_duration")){
				this.duration = Double.parseDouble(oneEffect.getString("tooltip_illusion_duration"));
				this.abilityTags = this.abilityTags + ";illusion";
			}
			//buckler lasts 5 seconds longer on creeps...
			else if(oneEffect.has("bonus_aoe_duration_hero")){
				this.duration = Double.parseDouble(oneEffect.getString("bonus_aoe_duration_hero"));
				this.abilityTags = this.abilityTags + ";armor";
			}
			else if(oneEffect.has("barrier_duration")){
				this.duration = Double.parseDouble(oneEffect.getString("barrier_duration"));
				//this.abilityTags = this.abilityTags + ";barrier";
			}
			else if(oneEffect.has("purge_slow_duration")){
				this.duration = Double.parseDouble(oneEffect.getString("purge_slow_duration"));
				this.abilityTags = this.abilityTags + ";purge;slow";
			}
			/*
			//Ethereal blade lasts one second longer on allies
			if(oneEffect.has("ally_duration")){
				this.duration = Double.parseDouble(oneEffect.getString("ally_duration"));
				this.abilityTags = this.abilityTags + ";ethereal;nuke";
			}
			*/
			else if(oneEffect.has("duration")){
				//BKB with it's average duration
				//TODO Implement multiple BKB items/levels?
				if(source.getId() == 116)
					this.duration = 7.5;
				else this.duration = Double.parseDouble(oneEffect.getString("duration"));
			}
			else if(oneEffect.has("static_duration")){
				this.duration = Double.parseDouble(oneEffect.getString("static_duration"));
			}
			else if(oneEffect.has("berserk_duration")){
				this.duration = Double.parseDouble(oneEffect.getString("berserk_duration"));
				this.abilityTags = this.abilityTags + ";haste;steroid;dmg_amp";
			}
			
			//AOE and RADIUS
			
			if(oneEffect.has("application_radius")){
				this.aoe = true;
				this.radius = Integer.parseInt(oneEffect.getString("application_radius"));
			}
			else if(oneEffect.has("radius")){
				this.aoe = true;
				this.radius = Integer.parseInt(oneEffect.getString("radius"));
			}
			else if(oneEffect.has("bonus_aoe_radius")){
				this.aoe = true;
				this.radius = Integer.parseInt(oneEffect.getString("bonus_aoe_radius"));
			}
			else if(oneEffect.has("heal_radius")){
				this.aoe = true;
				this.radius = Integer.parseInt(oneEffect.getString("heal_radius"));
			}
			else if(oneEffect.has("barrier_radius")){
				this.aoe = true;
				this.radius = Integer.parseInt(oneEffect.getString("barrier_radius"));
			}
			else if(oneEffect.has("debuff_radius")){
				this.aoe = true;
				this.radius = Integer.parseInt(oneEffect.getString("debuff_radius"));
			}
			else if(oneEffect.has("blast_radius")){
				this.aoe = true;
				this.radius = Integer.parseInt(oneEffect.getString("blast_radius"));
			}
			
			
		}
		
		
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRawEffects() {
		return otherData;
	}

	public void setRawEffects(String rawEffects) {
		this.otherData = rawEffects;
	}

	public String getAbilityTags() {
		return abilityTags;
	}

	public void setAbilityTags(String abilityTags) {
		this.abilityTags = abilityTags;
	}

	public String getBehaviorType() {
		return abilityBehavior;
	}

	public void setBehaviorType(String behaviorType) {
		this.abilityBehavior = behaviorType;
	}

	public int getManaCost() {
		return manaCost;
	}

	public void setManaCost(int manaCost) {
		this.manaCost = manaCost;
	}

	public int getRange() {
		return castRange;
	}

	public void setRange(int range) {
		this.castRange = range;
	}

	public boolean isAoe() {
		return aoe;
	}

	public void setAoe(boolean aoe) {
		this.aoe = aoe;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public double getCooldown() {
		return cooldown;
	}

	public void setCooldown(double cooldown) {
		this.cooldown = cooldown;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public Item getSource() {
		return source;
	}

	public void setSource(Item source) {
		this.source = source;
	}
	
	
	
}

