package model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
	private int duration;
	
	private String otherData;
	
	@ManyToOne
	@JoinColumn(name="ABILITY_ID")
	private HeroAbility owningAbility;
	
	@OneToMany(mappedBy="source")
	List<AbilitySummon> summons;

	public Rank() {
		super();
	}

	public Rank(int id, int spellLevel, int requiredLevel, int manaCost,
			double coolDown, int range, int duration, String otherData,
			HeroAbility owningAbility, List<AbilitySummon> summons) {
		super();
		this.id = id;
		this.spellLevel = spellLevel;
		this.requiredLevel = requiredLevel;
		this.manaCost = manaCost;
		this.coolDown = coolDown;
		this.spellRange = range;
		this.duration = duration;
		this.otherData = otherData;
		this.owningAbility = owningAbility;
		this.summons = summons;
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

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
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
}
