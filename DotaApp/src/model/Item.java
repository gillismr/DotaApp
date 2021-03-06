package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.json.JSONException;
import org.json.JSONObject;

import access.ItemDao;

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
	private boolean isRecipe;
	
	//All of the subcomponents. This Item is listed as the result (toMake) attribute in the 
	// referencing table (Component)
	@OneToMany(mappedBy="toMake", cascade=CascadeType.PERSIST)
	private List<Component> madeFrom;
	
	//All of the recipes this item is used in. This Item is listed as the true component (isUsed) attribute in the referencing table (Component)
	@OneToMany(mappedBy="isUsed", cascade=CascadeType.PERSIST)
	private List<Component> usedIn;
	
	@Transient
	private String itemRequirements;
	
	@Transient
	private String itemResult;
	
	@OneToMany(mappedBy="source", cascade=CascadeType.PERSIST)
	private List<ItemFlatBonus> bonuses;
	
	@OneToOne(optional=true, mappedBy="source", cascade=CascadeType.PERSIST)
	private ItemAura aura;
	
	@OneToOne(optional=true, mappedBy="source", cascade=CascadeType.PERSIST)
	private ItemAbility ability;
	
	//An easy way to look up the ID of an item if you only have its name
	@Transient
	public static HashMap<String, Integer> itemNameToId = new HashMap<String, Integer>();

	
	public Item() {
		super();
	}

	
	public Item(int id, String name, int itemCost,
			String itemShopTags, boolean isRecipe,
			List<Component> madeFrom, List<Component> usedIn) {
		super();
		this.id = id;
		this.name = name;
		this.itemCost = itemCost;
		this.itemShopTags = itemShopTags;
		this.isRecipe = isRecipe;
		this.madeFrom = madeFrom;
		this.usedIn = usedIn;
	}


	public Item(String itemName, JSONObject data) throws NumberFormatException, JSONException {
		this.id = Integer.parseInt(data.getString("ID"));
		this.name = itemName;
		this.itemCost = Integer.parseInt(data.getString("ItemCost"));
		
		//If it's a recipe, deal with it and return
		if(data.has("ItemRecipe")){
			this.isRecipe = true;
			this.itemResult = data.getString("ItemResult");
			JSONObject itemRequirements = data.getJSONObject("ItemRequirements");
			if(itemRequirements.has("01"))
				this.itemRequirements = itemRequirements.getString("01");
			else this.itemRequirements = itemRequirements.getString("02");
			if (this.itemCost > 0)
				Item.itemNameToId.put(this.name, this.id);
			return;
		}
		
		
		//If it's not a recipe, it has theses fields. Set them
		this.abilityBehavior = data.getString("AbilityBehavior");
		this.itemShopTags = data.getString("ItemShopTags");
		
		//If it has an active ability... (exclude courier)
		if(!data.getString("AbilityBehavior").equals("DOTA_ABILITY_BEHAVIOR_PASSIVE") && this.id != 45){
			this.ability = new ItemAbility(this, data);
		}
		//If it has one of these IDs, it's an aura
		//mek, vlad, basi, headdress, drum, pipe, AC, rad, shivas, aquila
		if(this.id == 79 || this.id == 81 || this.id == 88 || this.id == 90 || this.id == 94 || this.id == 112 || this.id == 119 || this.id == 137 || this.id == 185 || this.id == 212){
			this.aura = new ItemAura(this, data.getJSONObject("AbilitySpecial"));
		}
		if(data.has("AbilitySpecial"))
			this.bonuses = ItemFlatBonus.makeBonuses(this, data.getJSONObject("AbilitySpecial"));
		
		
		
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

	public boolean isRecipe() {
		return isRecipe;
	}


	public void setRecipe(boolean isRecipe) {
		this.isRecipe = isRecipe;
	}


	public String getItemRequirements() {
		return itemRequirements;
	}


	public void setItemRequirements(String itemRequirements) {
		this.itemRequirements = itemRequirements;
	}


	public String getItemResult() {
		return itemResult;
	}


	public void setItemResult(String itemResult) {
		this.itemResult = itemResult;
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

	
	//Not sure if this should go here, but it made sense to have the Item class modify the fields of Items
	public static List<Item> setRecipes(List<Item> origItems) {
		ArrayList<Item> updatedItems = new ArrayList<Item>();
		ItemDao itemDao = ItemDao.getInstance();
		for(Item item:origItems){
			if(item.isRecipe()){
				Item resultItem = itemDao.findItem(Item.itemNameToId.get(item.getItemResult()));
				for(String oneRequirement:item.getItemRequirements().split(";")){
					Item componentItem = itemDao.findItem(Item.itemNameToId.get(oneRequirement));
					Component newComponent = new Component(componentItem, resultItem);
					componentItem.getUsedIn().add(newComponent);
					resultItem.getMadeFrom().add(newComponent);
				}
				if(item.getItemCost() == 0)
					itemDao.removeItem(item);
				else updatedItems.add(item);
				
			}
			else
				updatedItems.add(item);
		}
		return updatedItems;
	}


	
}
