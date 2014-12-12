package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class ItemSummon {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	

	private int numberOfUnits;
	
	@OneToOne
	@JoinColumn(name="SUMMONED_UNIT")
	private Unit summonedUnit;
	
	@ManyToOne
	@JoinColumn(name="SUMMON_ABILITY")
	private ItemAbility source;
	
	public ItemSummon() {
		super();
	}

	public ItemSummon(int id, int numberOfUnits, Unit summonedUnit,
			ItemAbility source) {
		super();
		this.id = id;
		this.numberOfUnits = numberOfUnits;
		this.summonedUnit = summonedUnit;
		this.source = source;
	}

	public ItemSummon(int numberOfUnits, Unit summonedUnit, ItemAbility source) {
		super();
		this.numberOfUnits = numberOfUnits;
		this.summonedUnit = summonedUnit;
		this.source = source;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumberOfUnits() {
		return numberOfUnits;
	}

	public void setNumberOfUnits(int numberOfUnits) {
		this.numberOfUnits = numberOfUnits;
	}

	public Unit getSummonedUnit() {
		return summonedUnit;
	}

	public void setSummonedUnit(Unit summonedUnit) {
		this.summonedUnit = summonedUnit;
	}

	public ItemAbility getSource() {
		return source;
	}

	public void setSource(ItemAbility source) {
		this.source = source;
	}
	
	
}
