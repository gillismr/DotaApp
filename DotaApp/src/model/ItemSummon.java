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
	
}
