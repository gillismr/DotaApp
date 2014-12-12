package model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.json.JSONObject;

@Entity
public class ItemAura {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int id;
	int radius;
	
	@OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name="ITEM_ID")
	private Item source;
	
	@OneToMany(mappedBy="aura")
	List<ItemAuraEffect> effects;

	public Item getSource() {
		return source;
	}

	public void setSource(Item source) {
		this.source = source;
	}

	public ItemAura() {
		super();
	}

	public ItemAura(int id, int radius, List<ItemAuraEffect> effects) {
		super();
		this.id = id;
		this.radius = radius;
		this.effects = effects;
	}

	public ItemAura(int id, int radius, Item source,
			List<ItemAuraEffect> effects) {
		super();
		this.id = id;
		this.radius = radius;
		this.source = source;
		this.effects = effects;
	}

	public ItemAura(int radius, Item source,
			List<ItemAuraEffect> effects) {
		super();
		this.radius = radius;
		this.source = source;
		this.effects = effects;
	}

	public ItemAura(Item source, JSONObject data) {
		this.source = source;
		//TODO Parse the JSONObject data into its actual fields
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public List<ItemAuraEffect> getEffects() {
		return effects;
	}

	public void setEffects(List<ItemAuraEffect> effects) {
		this.effects = effects;
	}
	
	
}
