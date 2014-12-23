package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.json.JSONException;
import org.json.JSONObject;

@Entity
@NamedQueries({
	@NamedQuery(name="Hero.findAll", query="SELECT h FROM Hero h"),
	@NamedQuery(name="Hero.dropAll", query="DELETE FROM Hero h")})
@XmlRootElement
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Hero implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;
	@XmlAttribute
	private String name;
		
	@OneToMany(mappedBy="owningHero", cascade=CascadeType.PERSIST)
	private List<HeroAbility> abilities;
	
	private double baseArmor;
	private double baseMagicRes;
	private boolean melee;
	private int attackDamageMin;
	private int attackDamageMax;
	private double attackRate;
	private double attackAnimationPoint;
	private int attackRange;
	private int projectileSpeed;
	private String primaryAttribute;
	private int baseStr;
	private double strGain;
	private int baseInt;
	private double intGain;
	private int baseAgi;
	private double agiGain;
	private int moveSpeed;
	private double turnRate;
	private int baseHealth;
	private double baseHealthRegen;
	private int baseMana;
	private double baseManaRegen;
	private int dayVision;
	private int nightVision;
	
	//An easy way to look up the ID of an item if you only have its name
	@Transient
	public static HashMap<String, Integer> heroNameToId = new HashMap<String, Integer>();
	
	public Hero() {
		super();
	}

	public Hero(String key, JSONObject data) throws NumberFormatException, JSONException{
		this.id = Integer.parseInt(data.getString("HeroID"));
		this.name = key.substring(14, key.length());
		this.baseArmor = Double.parseDouble(data.getString("ArmorPhysical"));
		this.baseMagicRes = Double.parseDouble(data.getString("MagicalResistance"));
		this.melee = data.getString("AttackCapabilities").equals("DOTA_UNIT_CAP_MELEE_ATTACK");
		this.attackDamageMin = Integer.parseInt(data.getString("AttackDamageMin"));
		this.attackDamageMax = Integer.parseInt(data.getString("AttackDamageMax"));		 
		this.attackRate = Double.parseDouble(data.getString("AttackRate")) ;
		this.attackAnimationPoint = Double.parseDouble(data.getString("AttackAnimationPoint"));
		this.attackRange = Integer.parseInt(data.getString("AttackRange"));
		if(data.getString("ProjectileSpeed").equals(""))
			this.projectileSpeed = 0;	
		else 
			this.projectileSpeed = Integer.parseInt(data.getString("ProjectileSpeed"));
		this.primaryAttribute = data.getString("AttributePrimary").substring(15);
		this.baseStr = Integer.parseInt(data.getString("AttributeBaseStrength"));
		this.strGain = Double.parseDouble(data.getString("AttributeStrengthGain")) ;
		this.baseInt = Integer.parseInt(data.getString("AttributeBaseIntelligence"));
		this.intGain = Double.parseDouble(data.getString("AttributeIntelligenceGain")) ;
		this.baseAgi = Integer.parseInt(data.getString("AttributeBaseAgility"));
		this.agiGain = Double.parseDouble(data.getString("AttributeAgilityGain")) ;
		this.moveSpeed = Integer.parseInt(data.getString("MovementSpeed")) ;
		this.turnRate = Double.parseDouble(data.getString("MovementTurnRate"));
		this.baseHealth = Integer.parseInt(data.getString("StatusHealth")) ;
		this.baseHealthRegen = Double.parseDouble(data.getString("StatusHealthRegen")) ;
		this.baseMana = Integer.parseInt(data.getString("StatusMana")) ;
		this.baseManaRegen= Double.parseDouble(data.getString("StatusManaRegen")) ;
		this.dayVision = Integer.parseInt(data.getString("VisionDaytimeRange")) ;
		this.nightVision = Integer.parseInt(data.getString("VisionNighttimeRange")) ;
		this.abilities = new ArrayList<HeroAbility>();
		heroNameToId.put(this.name, this.id);
		
		//THIS IS SUPER ESSENTIAL TO HOW we populate our HeroAbilities
		for(int i = 1;data.has("Ability"+i);i++){
			String abilityName = data.getString("Ability"+i);
			if(!abilityName.equals("attribute_bonus"))
				HeroAbility.abilityNameToHero.put(abilityName, this.id);
		}
		
	}
	
	public Hero(int id, String name, double baseArmor, double baseMagicRes,
			boolean melee, int attackDamageMin, int attackDamageMax,
			double attackRate, double attackAnimationPoint, int attackRange,
			int projectileSpeed, String primaryAttribute, int baseStr,
			double strGain, int baseInt, double intGain, int baseAgi,
			double agiGain, int moveSpeed, double turnRate, int baseHealth,
			double baseHealthRegen, int baseMana, double baseManaRegen,
			int dayVision, int nightVision) {
		super();
		this.id = id;
		this.name = name;
		this.baseArmor = baseArmor;
		this.baseMagicRes = baseMagicRes;
		this.melee = melee;
		this.attackDamageMin = attackDamageMin;
		this.attackDamageMax = attackDamageMax;
		this.attackRate = attackRate;
		this.attackAnimationPoint = attackAnimationPoint;
		this.attackRange = attackRange;
		this.projectileSpeed = projectileSpeed;
		this.primaryAttribute = primaryAttribute;
		this.baseStr = baseStr;
		this.strGain = strGain;
		this.baseInt = baseInt;
		this.intGain = intGain;
		this.baseAgi = baseAgi;
		this.agiGain = agiGain;
		this.moveSpeed = moveSpeed;
		this.turnRate = turnRate;
		this.baseHealth = baseHealth;
		this.baseHealthRegen = baseHealthRegen;
		this.baseMana = baseMana;
		this.baseManaRegen = baseManaRegen;
		this.dayVision = dayVision;
		this.nightVision = nightVision;
		heroNameToId.put(this.name, this.id);		

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		heroNameToId.remove(this.name);
		this.id = id;
		heroNameToId.put(this.name, this.id);
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		heroNameToId.remove(this.name);
		this.name = name;
		heroNameToId.put(this.name, this.id);
	}
	public double getBaseArmor() {
		return baseArmor;
	}
	public void setBaseArmor(double baseArmor) {
		this.baseArmor = baseArmor;
	}
	public double getBaseMagicRes() {
		return baseMagicRes;
	}
	public void setBaseMagicRes(double baseMagicRes) {
		this.baseMagicRes = baseMagicRes;
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
	public int getBaseStr() {
		return baseStr;
	}
	public void setBaseStr(int baseStr) {
		this.baseStr = baseStr;
	}
	public double getStrGain() {
		return strGain;
	}
	public void setStrGain(double strGain) {
		this.strGain = strGain;
	}
	public int getBaseInt() {
		return baseInt;
	}
	public void setBaseInt(int baseInt) {
		this.baseInt = baseInt;
	}
	public double getIntGain() {
		return intGain;
	}
	public void setIntGain(double intGain) {
		this.intGain = intGain;
	}
	public int getBaseAgi() {
		return baseAgi;
	}
	public void setBaseAgi(int baseAgi) {
		this.baseAgi = baseAgi;
	}
	public double getAgiGain() {
		return agiGain;
	}
	public void setAgiGain(double agiGain) {
		this.agiGain = agiGain;
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
	public int getBaseHealth() {
		return baseHealth;
	}
	public void setBaseHealth(int baseHealth) {
		this.baseHealth = baseHealth;
	}
	public double getBaseHealthRegen() {
		return baseHealthRegen;
	}
	public void setBaseHealthRegen(double baseHealthRegen) {
		this.baseHealthRegen = baseHealthRegen;
	}
	public int getBaseMana() {
		return baseMana;
	}
	public void setBaseMana(int baseMana) {
		this.baseMana = baseMana;
	}
	public double getBaseManaRegen() {
		return baseManaRegen;
	}
	public void setBaseManaRegen(double baseManaRegen) {
		this.baseManaRegen = baseManaRegen;
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

	public List<HeroAbility> getAbilities() {
		return abilities;
	}

	public void setAbilities(List<HeroAbility> abilities) {
		this.abilities = abilities;
	}
	
	
}
