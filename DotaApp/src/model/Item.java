package model;

import java.util.HashMap;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.json.JSONException;
import org.json.JSONObject;

@Entity
@NamedQueries({
	@NamedQuery(name="Item.findAll", query="SELECT i FROM Item i"),
	@NamedQuery(name="Item.dropAll", query="DELETE FROM Item i")})
public class Item {
	@Id
	private int id;
	private String name;
	private String abilityBehavior;
	private int itemCost;
	private String itemShopTags;
	@Lob
	private String abilitySpecial;
	private boolean isRecipe;
	
	//All of the subcomponents. This Item is listed as the result (toMake) attribute in the 
	// referencing table (Component)
	@OneToMany(mappedBy="toMake")
	private List<Component> madeFrom;
	
	//All of the recipes this item is used in. This Item is listed as the true component (isUsed) attribute in the referencing table (Component)
	@OneToMany(mappedBy="isUsed")
	private List<Component> usedIn;
	
	@OneToMany(mappedBy="source")
	private List<ItemFlatBonus> bonuses;
	
	@OneToOne(optional=true, mappedBy="source")
	private ItemAura aura;
	
	@OneToOne(optional=true, mappedBy="source")
	private ItemAbility ability;
	
	//An easy way to look up the ID of an item if you only have its name
	@Transient
	public static HashMap<String, Integer> itemNameToId = new HashMap<String, Integer>();

	
	public Item() {
		super();
	}

	
	public Item(int id, String name, String abilityBehavior, int itemCost,
			String itemShopTags, String abilitySpecial, boolean isRecipe,
			List<Component> madeFrom, List<Component> usedIn) {
		super();
		this.id = id;
		this.name = name;
		this.abilityBehavior = abilityBehavior;
		this.itemCost = itemCost;
		this.itemShopTags = itemShopTags;
		this.abilitySpecial = abilitySpecial;
		this.isRecipe = isRecipe;
		this.madeFrom = madeFrom;
		this.usedIn = usedIn;
	}


	public Item(String itemName, JSONObject data) throws NumberFormatException, JSONException {
		this.id = Integer.parseInt(data.getString("ID"));
		this.name = itemName;
		this.abilityBehavior = data.getString("AbilityBehavior");
		this.itemCost = Integer.parseInt(data.getString("ItemCost"));
		this.itemShopTags = data.getString("ItemShopTags");
		if(data.has("AbilitySpecial"))
			this.abilitySpecial = data.getJSONObject("AbilitySpecial").toString();
		else
			this.abilitySpecial = "";
		Item.itemNameToId.put(this.name, this.id);

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		Item.itemNameToId.remove(this.name);
		this.id = id;
		Item.itemNameToId.put(this.name, this.id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		Item.itemNameToId.remove(this.name);
		this.name = name;
		Item.itemNameToId.put(this.name, this.id);
	}

	public String getAbilityBehavior() {
		return abilityBehavior;
	}

	public void setAbilityBehavior(String abilityBehavior) {
		this.abilityBehavior = abilityBehavior;
	}

	public int getItemCost() {
		return itemCost;
	}

	public void setItemCost(int itemCost) {
		this.itemCost = itemCost;
	}

	public String getItemShopTags() {
		return itemShopTags;
	}

	public void setItemShopTags(String itemShopTags) {
		this.itemShopTags = itemShopTags;
	}

	public String getAbilitySpecial() {
		return abilitySpecial;
	}

	public void setAbilitySpecial(String abilitySpecial) {
		this.abilitySpecial = abilitySpecial;
	}


	public boolean isRecipe() {
		return isRecipe;
	}


	public void setRecipe(boolean isRecipe) {
		this.isRecipe = isRecipe;
	}


	public List<Component> getMadeFrom() {
		return madeFrom;
	}


	public void setMadeFrom(List<Component> madeFrom) {
		this.madeFrom = madeFrom;
	}


	public List<Component> getUsedIn() {
		return usedIn;
	}


	public void setUsedIn(List<Component> usedIn) {
		this.usedIn = usedIn;
	}


	
}
