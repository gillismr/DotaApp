package model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class ItemAbility {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String name;
	private String rawEffects;
	private String abilityTags;
	private String behaviorType;
	private int manaCost;
	//use -1 for global
	private int range;
	private boolean aoe;
	private int radius;
	private double cooldown;
	private double duration;
	
	@OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name="ITEM_ID")
	private Item source;
	
	
	@OneToMany(mappedBy="source")
	private List<ItemSummon> summons;
	
	public ItemAbility() {
		super();
	}

	public ItemAbility(int id, String name, String rawEffects,
			String abilityTags, String behaviorType, int manaCost, int range,
			boolean aoe, int radius, double cooldown, double duration,
			Item source) {
		super();
		this.id = id;
		this.name = name;
		this.rawEffects = rawEffects;
		this.abilityTags = abilityTags;
		this.behaviorType = behaviorType;
		this.manaCost = manaCost;
		this.range = range;
		this.aoe = aoe;
		this.radius = radius;
		this.cooldown = cooldown;
		this.duration = duration;
		this.source = source;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRawEffects() {
		return rawEffects;
	}

	public void setRawEffects(String rawEffects) {
		this.rawEffects = rawEffects;
	}

	public String getAbilityTags() {
		return abilityTags;
	}

	public void setAbilityTags(String abilityTags) {
		this.abilityTags = abilityTags;
	}

	public String getBehaviorType() {
		return behaviorType;
	}

	public void setBehaviorType(String behaviorType) {
		this.behaviorType = behaviorType;
	}

	public int getManaCost() {
		return manaCost;
	}

	public void setManaCost(int manaCost) {
		this.manaCost = manaCost;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
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

