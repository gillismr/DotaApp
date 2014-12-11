package model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Rank {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int id;
	
	private int spellLevel;
	private int requiredLevel;
	
	//VALUES THAT FEED INTO THE MECAHNICS WILL GO HERE
	
	private int manaCost;
	private double coolDown;
	private int range;
	//use -1 for constant duration
	private int duration;
	
	private String otherData;
	
	@ManyToOne
	@JoinColumn(name="ABILITY_ID")
	private HeroAbility owningAbility;
	
	@OneToMany(mappedBy="source")
	List<AbilitySummon> summons;
}
