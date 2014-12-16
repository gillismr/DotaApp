package model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.json.JSONObject;

@Entity
public class Unit {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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
	
	@Transient
	public static HashMap<String, Integer> unitNameToId = new HashMap<String, Integer>();
	

	public Unit() {
		super();
	}
	
	public Unit(int id, String name, int level, boolean isAncient,
			boolean isNeutralUnit, int armor, boolean isMelee, int minDamage,
			int maxDamage, double attackRate, double attackAnimationPoint,
			int aquisitionRange, int attackRange, int projectileSpeed,
			int bountyXP, int goldMin, int goldMax, int moveSpeed, int health,
			int healthRegen, int mana, int manaRegen, int dayVision,
			int nightVision, List<UnitAbility> abilities,
			ItemSummon summonedByItem, AbilitySummon summonedByAbility) {
		super();
		this.id = id;
		this.name = name;
		this.level = level;
		this.isAncient = isAncient;
		this.isNeutralUnit = isNeutralUnit;
		this.armor = armor;
		this.isMelee = isMelee;
		this.minDamage = minDamage;
		this.maxDamage = maxDamage;
		this.attackRate = attackRate;
		this.attackAnimationPoint = attackAnimationPoint;
		this.aquisitionRange = aquisitionRange;
		this.attackRange = attackRange;
		this.projectileSpeed = projectileSpeed;
		this.bountyXP = bountyXP;
		this.goldMin = goldMin;
		this.goldMax = goldMax;
		this.moveSpeed = moveSpeed;
		this.health = health;
		this.healthRegen = healthRegen;
		this.mana = mana;
		this.manaRegen = manaRegen;
		this.dayVision = dayVision;
		this.nightVision = nightVision;
		this.abilities = abilities;
		this.summonedByItem = summonedByItem;
		this.summonedByAbility = summonedByAbility;
		unitNameToId.put(this.name, this.id);
	}

	public Unit(String name, int level, boolean isAncient,
			boolean isNeutralUnit, int armor, boolean isMelee, int minDamage,
			int maxDamage, double attackRate, double attackAnimationPoint,
			int aquisitionRange, int attackRange, int projectileSpeed,
			int bountyXP, int goldMin, int goldMax, int moveSpeed, int health,
			int healthRegen, int mana, int manaRegen, int dayVision,
			int nightVision, List<UnitAbility> abilities,
			ItemSummon summonedByItem, AbilitySummon summonedByAbility) {
		super();
		this.name = name;
		this.level = level;
		this.isAncient = isAncient;
		this.isNeutralUnit = isNeutralUnit;
		this.armor = armor;
		this.isMelee = isMelee;
		this.minDamage = minDamage;
		this.maxDamage = maxDamage;
		this.attackRate = attackRate;
		this.attackAnimationPoint = attackAnimationPoint;
		this.aquisitionRange = aquisitionRange;
		this.attackRange = attackRange;
		this.projectileSpeed = projectileSpeed;
		this.bountyXP = bountyXP;
		this.goldMin = goldMin;
		this.goldMax = goldMax;
		this.moveSpeed = moveSpeed;
		this.health = health;
		this.healthRegen = healthRegen;
		this.mana = mana;
		this.manaRegen = manaRegen;
		this.dayVision = dayVision;
		this.nightVision = nightVision;
		this.abilities = abilities;
		this.summonedByItem = summonedByItem;
		this.summonedByAbility = summonedByAbility;
		unitNameToId.put(this.name, this.id);
	}

	@SuppressWarnings("unchecked")
	public Unit(String unitName, JSONObject data) {
		this.name = unitName;
		Iterator<String> attributeNames = data.keys();
		while(attributeNames.hasNext()){
			String attributeName = attributeNames.next();
			if(data.has(attributeName));
			
		}
			
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		unitNameToId.remove(this.name);
		this.id = id;
		unitNameToId.put(this.name, this.id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		unitNameToId.remove(this.name);
		this.name = name;
		unitNameToId.put(this.name, this.id);
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isAncient() {
		return isAncient;
	}

	public void setAncient(boolean isAncient) {
		this.isAncient = isAncient;
	}

	public boolean isNeutralUnit() {
		return isNeutralUnit;
	}

	public void setNeutralUnit(boolean isNeutralUnit) {
		this.isNeutralUnit = isNeutralUnit;
	}

	public int getArmor() {
		return armor;
	}

	public void setArmor(int armor) {
		this.armor = armor;
	}

	public boolean isMelee() {
		return isMelee;
	}

	public void setMelee(boolean isMelee) {
		this.isMelee = isMelee;
	}

	public int getMinDamage() {
		return minDamage;
	}

	public void setMinDamage(int minDamage) {
		this.minDamage = minDamage;
	}

	public int getMaxDamage() {
		return maxDamage;
	}

	public void setMaxDamage(int maxDamage) {
		this.maxDamage = maxDamage;
	}

	public double getAttackRate() {
		return attackRate;
	}

	public void setAttackRate(double attackRate) {
		this.attackRate = attackRate;
	}

	public double getAttackAnimationPoint() {
		return attackAnimationPoint;
	}

	public void setAttackAnimationPoint(double attackAnimationPoint) {
		this.attackAnimationPoint = attackAnimationPoint;
	}

	public int getAquisitionRange() {
		return aquisitionRange;
	}

	public void setAquisitionRange(int aquisitionRange) {
		this.aquisitionRange = aquisitionRange;
	}

	public int getAttackRange() {
		return attackRange;
	}

	public void setAttackRange(int attackRange) {
		this.attackRange = attackRange;
	}

	public int getProjectileSpeed() {
		return projectileSpeed;
	}

	public void setProjectileSpeed(int projectileSpeed) {
		this.projectileSpeed = projectileSpeed;
	}

	public int getBountyXP() {
		return bountyXP;
	}

	public void setBountyXP(int bountyXP) {
		this.bountyXP = bountyXP;
	}

	public int getGoldMin() {
		return goldMin;
	}

	public void setGoldMin(int goldMin) {
		this.goldMin = goldMin;
	}

	public int getGoldMax() {
		return goldMax;
	}

	public void setGoldMax(int goldMax) {
		this.goldMax = goldMax;
	}

	public int getMoveSpeed() {
		return moveSpeed;
	}

	public void setMoveSpeed(int moveSpeed) {
		this.moveSpeed = moveSpeed;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getHealthRegen() {
		return healthRegen;
	}

	public void setHealthRegen(int healthRegen) {
		this.healthRegen = healthRegen;
	}

	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

	public int getManaRegen() {
		return manaRegen;
	}

	public void setManaRegen(int manaRegen) {
		this.manaRegen = manaRegen;
	}

	public int getDayVision() {
		return dayVision;
	}

	public void setDayVision(int dayVision) {
		this.dayVision = dayVision;
	}

	public int getNightVision() {
		return nightVision;
	}

	public void setNightVision(int nightVision) {
		this.nightVision = nightVision;
	}

	public List<UnitAbility> getAbilities() {
		return abilities;
	}

	public void setAbilities(List<UnitAbility> abilities) {
		this.abilities = abilities;
	}

	public ItemSummon getSummonedByItem() {
		return summonedByItem;
	}

	public void setSummonedByItem(ItemSummon summonedByItem) {
		this.summonedByItem = summonedByItem;
	}

	public AbilitySummon getSummonedByAbility() {
		return summonedByAbility;
	}

	public void setSummonedByAbility(AbilitySummon summonedByAbility) {
		this.summonedByAbility = summonedByAbility;
	}
	
	
}
