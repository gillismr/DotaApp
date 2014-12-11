package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class AbilitySummon {
	@Id
	int id;
	int numberOfUnits;
	
	@OneToOne
	@JoinColumn(name="SUMMONED_UNIT")
	private Unit summonedUnit;
	
	@ManyToOne
	@JoinColumn(name="SUMMON_ABILITY")
	private Rank source;
	
}
