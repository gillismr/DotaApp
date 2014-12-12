package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class AbilitySummon {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int id;
	int numberOfUnits;
	
	@OneToOne
	@JoinColumn(name="SUMMONED_UNIT")
	private Unit summonedUnit;
	
	@ManyToOne
	@JoinColumn(name="SUMMON_ABILITY")
	private Rank source;

	public AbilitySummon() {
		super();
	}

	public AbilitySummon(int id, int numberOfUnits, Unit summonedUnit,
			Rank source) {
		super();
		this.id = id;
		this.numberOfUnits = numberOfUnits;
		this.summonedUnit = summonedUnit;
		this.source = source;
	}


	public AbilitySummon(int numberOfUnits, Unit summonedUnit,
			Rank source) {
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

	public Rank getSource() {
		return source;
	}

	public void setSource(Rank source) {
		this.source = source;
	}
	
}
