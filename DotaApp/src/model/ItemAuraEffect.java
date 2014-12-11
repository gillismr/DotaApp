package model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ItemAuraEffect {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Enumerated(EnumType.STRING)
	private AuraType type;
	private boolean affectsEnemies;
	private double value;
	
	@ManyToOne
	@JoinColumn(name="AURA_ID")
	private ItemAura aura;
	
	public ItemAuraEffect() {
		super();
	}

	public ItemAuraEffect(int id, AuraType type, boolean affectsEnemies,
			double value, ItemAura aura) {
		super();
		this.id = id;
		this.type = type;
		this.affectsEnemies = affectsEnemies;
		this.value = value;
		this.aura = aura;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public AuraType getType() {
		return type;
	}

	public void setType(AuraType type) {
		this.type = type;
	}

	public boolean isAffectsEnemies() {
		return affectsEnemies;
	}

	public void setAffectsEnemies(boolean affectsEnemies) {
		this.affectsEnemies = affectsEnemies;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public ItemAura getAura() {
		return aura;
	}

	public void setAura(ItemAura aura) {
		this.aura = aura;
	}
	
	
	
}
