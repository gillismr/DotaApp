package model;

import java.util.HashMap;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.json.JSONException;
import org.json.JSONObject;

import access.HeroDao;

@Entity
@NamedQueries({
	@NamedQuery(name="HeroAbility.findAll", query="SELECT ha FROM HeroAbility ha"),
	})
public class HeroAbility {
	
	@Id
	int id;
	
	private String name;
	private boolean isUltimate;
	
	//MECHANICS WILL GO HERE
	@Lob
	private String abilityBehavior;
	
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="HERO_ID")
	private Hero owningHero;
	
	@OneToMany(mappedBy="owningAbility", cascade=CascadeType.PERSIST)
	private List<Rank> ranks;
	
	@Transient
	public static HashMap<String, Integer> abilityNameToHero = new HashMap<String, Integer>();

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

	public boolean isUltimate() {
		return isUltimate;
	}

	public void setUltimate(boolean isUltimate) {
		this.isUltimate = isUltimate;
	}

	public String getAbilityBehavior() {
		return abilityBehavior;
	}

	public void setAbilityBehavior(String abilityBehavior) {
		this.abilityBehavior = abilityBehavior;
	}

	public Hero getOwningHero() {
		return owningHero;
	}

	public void setOwningHero(Hero owningHero) {
		this.owningHero = owningHero;
	}

	public List<Rank> getRanks() {
		return ranks;
	}

	public void setRanks(List<Rank> ranks) {
		this.ranks = ranks;
	}

	public HeroAbility(int id, String name, boolean isUltimate,
			String abilityBehavior, Hero owningHero, List<Rank> ranks) {
		super();
		this.id = id;
		this.name = name;
		this.isUltimate = isUltimate;
		this.abilityBehavior = abilityBehavior;
		this.owningHero = owningHero;
		this.ranks = ranks;
	}

	public HeroAbility() {
		super();
	}
	
	public HeroAbility(int id, String name, boolean isUltimate,
			String abilityBehavior) {
		super();
		this.id = id;
		this.name = name;
		this.isUltimate = isUltimate;
		this.abilityBehavior = abilityBehavior;
	}

	public HeroAbility(String abilityName, JSONObject data) throws NumberFormatException, JSONException {
		HeroDao heroDao = HeroDao.getInstance();
		this.id = Integer.parseInt(data.getString("ID"));
		this.name = abilityName;
		Hero owningHero = heroDao.findHero(HeroAbility.abilityNameToHero.get(abilityName));
		this.owningHero = owningHero;
		owningHero.getAbilities().add(this);
		this.abilityBehavior = data.getString("AbilityBehavior");
		this.isUltimate = data.getString("AbilityType").equals("DOTA_ABILITY_TYPE_ULTIMATE");
		this.ranks = Rank.makeRanks(this, data);
	}

	
}
