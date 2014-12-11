package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class UnitAbility {
	@Id
	int id;
	String name;
	double coolDown;
	int manaCost;
	int spellRange;
	boolean isAoe;
	int radius;
	int duration;
	
	@ManyToOne
	@JoinColumn(name="UNIT_ID")
	private Unit owningUnit;

	public UnitAbility() {
		super();
	}

	public UnitAbility(int id, String name, double coolDown, int manaCost,
			int range, boolean isAoe, int radius, int duration, Unit owningUnit) {
		super();
		this.id = id;
		this.name = name;
		this.coolDown = coolDown;
		this.manaCost = manaCost;
		this.spellRange = range;
		this.isAoe = isAoe;
		this.radius = radius;
		this.duration = duration;
		this.owningUnit = owningUnit;
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

	public double getCoolDown() {
		return coolDown;
	}

	public void setCoolDown(double coolDown) {
		this.coolDown = coolDown;
	}

	public int getManaCost() {
		return manaCost;
	}

	public void setManaCost(int manaCost) {
		this.manaCost = manaCost;
	}

	public int getRange() {
		return spellRange;
	}

	public void setRange(int range) {
		this.spellRange = range;
	}

	public boolean isAoe() {
		return isAoe;
	}

	public void setAoe(boolean isAoe) {
		this.isAoe = isAoe;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Unit getOwningUnit() {
		return owningUnit;
	}

	public void setOwningUnit(Unit owningUnit) {
		this.owningUnit = owningUnit;
	}
	
}
