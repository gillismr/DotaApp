package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.json.JSONException;
import org.json.JSONObject;

@Entity
public class Rank {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int id;
	
	private int spellLevel;
	private int requiredLevel;
	
	//VALUES THAT FEED INTO THE MECAHNICS WILL GO HERE
	
	private int manaCost;
	private double coolDown;
	private int spellRange;
	//use -1 for constant duration
	private double duration;
	private double castPoint;
	private int damage;
	
	@Lob
	private String otherData;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="ABILITY_ID")
	private HeroAbility owningAbility;
	
	@OneToMany(mappedBy="source", cascade=CascadeType.PERSIST)
	List<AbilitySummon> summons;

	public Rank() {
		super();
	}

	public Rank(int id, int spellLevel, int requiredLevel, int manaCost,
			double coolDown, int range, double duration, int damage, String otherData,
			HeroAbility owningAbility, List<AbilitySummon> summons) {
		super();
		this.id = id;
		this.spellLevel = spellLevel;
		this.requiredLevel = requiredLevel;
		this.manaCost = manaCost;
		this.coolDown = coolDown;
		this.spellRange = range;
		this.duration = duration;
		this.damage = damage;
		this.otherData = otherData;
		this.owningAbility = owningAbility;
		this.summons = summons;
	}
	

	public Rank(int spellLevel, int requiredLevel, int manaCost,
			double coolDown, int range, double duration, double castPoint, int damage, String otherData,
			HeroAbility owningAbility, List<AbilitySummon> summons) {
		super();
		this.spellLevel = spellLevel;
		this.requiredLevel = requiredLevel;
		this.manaCost = manaCost;
		this.coolDown = coolDown;
		this.spellRange = range;
		this.duration = duration;
		this.castPoint = castPoint;
		this.damage = damage;
		this.otherData = otherData;
		this.owningAbility = owningAbility;
		this.summons = summons;
	}
	
	public Rank(int spellLevel, int manaCost,
			double coolDown, int range, double duration, double castPoint, int damage, String otherData,
			HeroAbility owningAbility) {
		super();
		this.spellLevel = spellLevel;
		this.manaCost = manaCost;
		this.coolDown = coolDown;
		this.spellRange = range;
		this.duration = duration;
		this.castPoint = castPoint;
		this.damage = damage;
		this.otherData = otherData;
		this.owningAbility = owningAbility;
	}

	public Rank(int spellLevel, double manaCost,
			double coolDown, double range, double duration, double castPoint, double damage, String otherData,
			HeroAbility owningAbility) {
		super();
		this.spellLevel = spellLevel;
		this.manaCost = (int) manaCost;
		this.coolDown = coolDown;
		this.spellRange = (int) range;
		this.duration = duration;
		this.castPoint = castPoint;
		this.damage = (int) damage;
		this.otherData = otherData;
		this.owningAbility = owningAbility;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSpellLevel() {
		return spellLevel;
	}

	public void setSpellLevel(int spellLevel) {
		this.spellLevel = spellLevel;
	}

	public int getRequiredLevel() {
		return requiredLevel;
	}

	public void setRequiredLevel(int requiredLevel) {
		this.requiredLevel = requiredLevel;
	}

	public int getManaCost() {
		return manaCost;
	}

	public void setManaCost(int manaCost) {
		this.manaCost = manaCost;
	}

	public double getCoolDown() {
		return coolDown;
	}

	public void setCoolDown(double coolDown) {
		this.coolDown = coolDown;
	}

	public int getRange() {
		return spellRange;
	}

	public void setRange(int range) {
		this.spellRange = range;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public double getCastPoint() {
		return castPoint;
	}

	public void setCastPoint(double castPoint) {
		this.castPoint = castPoint;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public String getOtherData() {
		return otherData;
	}

	public void setOtherData(String otherData) {
		this.otherData = otherData;
	}

	public HeroAbility getOwningAbility() {
		return owningAbility;
	}

	public void setOwningAbility(HeroAbility owningAbility) {
		this.owningAbility = owningAbility;
	}

	public List<AbilitySummon> getSummons() {
		return summons;
	}

	public void setSummons(List<AbilitySummon> summons) {
		this.summons = summons;
	}

	@SuppressWarnings("unchecked")
	public static List<Rank> makeRanks(HeroAbility source, JSONObject data) throws JSONException {
		RankedValue rankedManaCost, rankedCooldown, rankedRange, rankedDuration, rankedCastPoint, rankedDamage;
		
		rankedManaCost = new RankedValue(data.getString("AbilityManaCost"));
		rankedCooldown = new RankedValue(data.getString("AbilityCooldown"));
		rankedRange = new RankedValue(data.getString("AbilityCastRange"));
		rankedDuration = new RankedValue(data.getString("AbilityDuration"));
		rankedCastPoint = new RankedValue(data.getString("AbilityCastPoint"));
		rankedDamage = new RankedValue(data.getString("AbilityDamage"));

		JSONObject abilitySpecial = new JSONObject();
		//look for more things in AbilitySpecial
		if(data.has("AbilitySpecial")){
			abilitySpecial = data.getJSONObject("AbilitySpecial");
			Iterator<String> effectNumbers = abilitySpecial.keys();
			while(effectNumbers.hasNext()){
				JSONObject oneEffect = abilitySpecial.getJSONObject(effectNumbers.next());
				if(oneEffect.has("duration"))
					rankedDuration = new RankedValue(oneEffect.getString("duration"));
				if(oneEffect.has("damage"))
					rankedDamage = new RankedValue(oneEffect.getString("damage"));
			}
			
		}
		int maxValues = 0;
		ArrayList<RankedValue> rvList = new ArrayList<RankedValue>();
		rvList.add(rankedDamage);
		rvList.add(rankedManaCost);
		rvList.add(rankedCooldown);
		rvList.add(rankedRange);
		rvList.add(rankedDuration);
		rvList.add(rankedCastPoint);
		
		for(int i = 0; i<6;i++){
			if(rvList.get(i).length() > maxValues)
				maxValues = rvList.get(i).length();
		}
		
		ArrayList<Rank> theRanks = new ArrayList<Rank>();
		for(int i = 0;i<maxValues;i++){
			Rank oneRank = new Rank(i+1, rankedManaCost.get(i), rankedCooldown.get(i), rankedRange.get(i), rankedDuration.get(i), rankedCastPoint.get(i), rankedDamage.get(i), abilitySpecial.toString(), source);
			theRanks.add(oneRank);
		}
		return theRanks;
		
	}
}
