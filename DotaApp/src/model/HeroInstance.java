package model;

public class HeroInstance {

	private double armor;
	private double magicRes;
	private boolean melee;
	private int attackDamageMin;
	private int attackDamageMax;
	private int attackDamage;
	private double attackRate;
	private double attackAnimationPoint;
	private int attackRange;
	private int projectileSpeed;
	private String primaryAttribute;
	private double strength;
	private double intelligence;
	private double agility;
	private int moveSpeed;
	private double turnRate;
	private int hp;
	private double hpRegen;
	private int mana;
	private double manaRegen;
	private int dayVision;
	private int nightVision;
	
	public HeroInstance() {
		// TODO Auto-generated constructor stub
	}
	
	public HeroInstance(Hero baseHero, int level){
		this.strength = baseHero.getBaseStr() + (level * baseHero.getStrGain());
		this.agility = baseHero.getBaseAgi() + (level * baseHero.getAgiGain());
		this.intelligence = baseHero.getBaseInt() + (level * baseHero.getIntGain());
		if(baseHero.getPrimaryAttribute().equals("DOTA_ATTRIBUTE_STRENGTH")){
			this.primaryAttribute = "str";
			this.attackDamageMin = (int) (baseHero.getAttackDamageMin() + this.strength);
			this.attackDamageMax = (int) (baseHero.getAttackDamageMax() + this.strength);
			this.attackDamage = this.attackDamageMin + this.attackDamageMax / 2;
		}
		else if (baseHero.getPrimaryAttribute().equals("DOTA_ATTRIBUTE_AGILITY")){
			this.primaryAttribute = "agi";
			this.attackDamageMin = (int) (baseHero.getAttackDamageMin() + this.agility);
			this.attackDamageMax = (int) (baseHero.getAttackDamageMax() + this.agility);
			this.attackDamage = this.attackDamageMin + this.attackDamageMax / 2;
		}
		else{ //It's int
			this.primaryAttribute = "int";
			this.attackDamageMin = (int) (baseHero.getAttackDamageMin() + this.intelligence);
			this.attackDamageMax = (int) (baseHero.getAttackDamageMax() + this.intelligence);
			this.attackDamage = this.attackDamageMin + this.attackDamageMax / 2;
		}
		this.armor = baseHero.getBaseArmor() + (this.agility * 0.14);
		this.attackRate = baseHero.getAttackRate() / (1 + (1/(this.agility/100)));
		this.hp = (int) (baseHero.getBaseHealth() + 19*this.strength);
		this.mana = (int)(baseHero.getBaseMana() + 13*this.intelligence);
		this.hpRegen = baseHero.getBaseHealthRegen() + 0.03*this.strength;
		this.manaRegen = baseHero.getBaseManaRegen() + 0.04*this.intelligence;
		this.magicRes = baseHero.getBaseMagicRes();
		this.melee = baseHero.isMelee();
		this.attackAnimationPoint = baseHero.getAttackAnimationPoint();
		this.attackRange = baseHero.getAttackRange();
		this.projectileSpeed = baseHero.getProjectileSpeed();
		this.moveSpeed= baseHero.getMoveSpeed() ;
		this.turnRate = baseHero.getTurnRate();
		this.dayVision = baseHero.getDayVision();
		this.nightVision = baseHero.getNightVision() ;
		
	}

	public double getArmor() {
		return armor;
	}

	public void setArmor(double armor) {
		this.armor = armor;
	}

	public double getMagicRes() {
		return magicRes;
	}

	public void setMagicRes(double magicRes) {
		this.magicRes = magicRes;
	}

	public boolean isMelee() {
		return melee;
	}

	public void setMelee(boolean melee) {
		this.melee = melee;
	}

	public int getAttackDamageMin() {
		return attackDamageMin;
	}

	public void setAttackDamageMin(int attackDamageMin) {
		this.attackDamageMin = attackDamageMin;
	}

	public int getAttackDamageMax() {
		return attackDamageMax;
	}

	public void setAttackDamageMax(int attackDamageMax) {
		this.attackDamageMax = attackDamageMax;
	}

	public int getAttackDamage() {
		return attackDamage;
	}

	public void setAttackDamage(int attackDamage) {
		this.attackDamage = attackDamage;
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

	public String getPrimaryAttribute() {
		return primaryAttribute;
	}

	public void setPrimaryAttribute(String primaryAttribute) {
		this.primaryAttribute = primaryAttribute;
	}

	public double getStrength() {
		return strength;
	}

	public void setStrength(double strength) {
		this.strength = strength;
	}

	public double getIntelligence() {
		return intelligence;
	}

	public void setIntelligence(double intelligence) {
		this.intelligence = intelligence;
	}

	public double getAgility() {
		return agility;
	}

	public void setAgility(double agility) {
		this.agility = agility;
	}

	public int getMoveSpeed() {
		return moveSpeed;
	}

	public void setMoveSpeed(int moveSpeed) {
		this.moveSpeed = moveSpeed;
	}

	public double getTurnRate() {
		return turnRate;
	}

	public void setTurnRate(double turnRate) {
		this.turnRate = turnRate;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public double getHpRegen() {
		return hpRegen;
	}

	public void setHpRegen(double hpRegen) {
		this.hpRegen = hpRegen;
	}

	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

	public double getManaRegen() {
		return manaRegen;
	}

	public void setManaRegen(double manaRegen) {
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

}
