package model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Unit {
	@Id
	int id;
	String name;
	int level;
	boolean isAncient;
	boolean isNeutralUnit;
	int armor;
	boolean isMelee;
	int minDamage;
	int maxDamage;
	double attackRate;
	double attackAnimationPoint;
	int aquisitionRange;
	int attackRange;
	int projectileSpeed;
	
	int bountyXP;
	int goldMin;
	int goldMax;
	
	int moveSpeed;
	
	int health;
	int healthRegen;
	int mana;
	int manaRegen;
	
	int dayVision;
	int nightVision;
	@OneToMany(mappedBy="owningUnit")
	List<UnitAbility> abilities;
	
	@OneToOne(mappedBy="summonedUnit")
	private ItemSummon summonedByItem;
	
	@OneToOne(mappedBy="summonedUnit")
	private AbilitySummon summonedByAbility;
	
}
