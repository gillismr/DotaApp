package model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class HeroAbility {
	
	@Id
	int id;
	private String name;
	private boolean isUltimate;
	
	//MECHANICS WILL GO HERE
	private String abilityBehavior;
	
	
	@ManyToOne
	@JoinColumn(name="HERO_ID")
	private Hero owningHero;
	
	@OneToMany(mappedBy="owningAbility")
	private List<Rank> ranks;
	
	
}
