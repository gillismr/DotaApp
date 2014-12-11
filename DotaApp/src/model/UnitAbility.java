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
	int range;
	boolean isAoe;
	int radius;
	int duration;
	
	@ManyToOne
	@JoinColumn(name="UNIT_ID")
	private Unit owningUnit;
	
}
