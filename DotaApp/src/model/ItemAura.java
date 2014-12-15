package model;

import java.util.Iterator;
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

import org.json.JSONException;
import org.json.JSONObject;

@Entity
public class ItemAura {
	//These are the items with auras:
	//mek, vlad, basi, headdress, drum, pipe, AC, rad, shivas, aquila
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int id;
	int radius;
	
	@OneToOne(optional = false, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name="ITEM_ID")
	private Item source;
	
	@OneToMany(mappedBy="aura", cascade=CascadeType.PERSIST)
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

	@SuppressWarnings("unchecked")
	public ItemAura(Item source, JSONObject data) throws JSONException {
		this.source = source;
		Iterator<String> effectNumbers = data.keys();
		while(effectNumbers.hasNext()){
			JSONObject oneEffect = data.getJSONObject(effectNumbers.next());
			//radius is stored as aura_radius in everything except drum
			if(oneEffect.has("aura_radius"))
				this.radius = Integer.parseInt(oneEffect.getString("aura_radius").trim());
			else if(oneEffect.has("radius")) 
				this.radius = Integer.parseInt(oneEffect.getString("radius"));
		}
		this.effects = ItemAuraEffect.makeEffects(this, data);
				
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
