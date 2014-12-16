package populate;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
//import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import model.Hero;
import model.HeroAbility;
import model.Item;
import model.Unit;

import org.json.JSONException;
import org.json.JSONObject;

import com.nosoop.json.VDF;


public class VdfConverter {

	//Given a string for the path to a .txt file in ValveDataFormat, execute
	//nosoop's methods to parse that file into a JSONObject
	public static JSONObject vdfToJson(String filePath){
		Scanner scanner;
		JSONObject json = null;
		try {
			scanner = new Scanner(new File(filePath));
			String text = scanner.useDelimiter("\\A").next();
			scanner.close();
			json = VDF.toJSONObject(text, false);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	public static int countKeys(JSONObject json){
		@SuppressWarnings("unchecked")
		Iterator<String> keys = json.keys();
		int count = 0;
		while(keys.hasNext()){
			count++;
			keys.next();
		}
		return count;
	}
	
	//Given a JSONObject with default key/value pairs for the given instance 
	// of a corresponding JSONObject, add those default key/value pairs if the
	// instance doesn't have a specific value for them.
	public static void addDefaults(JSONObject defaults, JSONObject toUpdate){
		@SuppressWarnings("unchecked")
		Iterator<String> defaultKeys = defaults.keys();
		while(defaultKeys.hasNext()){
			String defaultKey = defaultKeys.next();
			if(!toUpdate.has(defaultKey)){
				try {
					toUpdate.put(defaultKey, defaults.get(defaultKey));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			//else toUpdate has a pair for the current defaultKey, so do nothing
		}
		
	}
	
	public static JSONObject parseRawHeroJSON(JSONObject rawHeroJSON) 
			throws JSONException
	{
		JSONObject updatedHeroes = new JSONObject();
		JSONObject rawHeroesJSON = (JSONObject) rawHeroJSON.get("DOTAHeroes");
		JSONObject baseHero = (JSONObject) rawHeroesJSON.get("npc_dota_hero_base");
		@SuppressWarnings("unchecked")
		Iterator<String> heroKeys = rawHeroesJSON.keys();
		while(heroKeys.hasNext()){
			String heroKey = heroKeys.next();
			if(heroKey.equals("Version") || heroKey.equals("npc_dota_hero_base"))
				continue;
			JSONObject toUpdate = (JSONObject) rawHeroesJSON.get(heroKey);
			addDefaults(baseHero, toUpdate);
			updatedHeroes.put(heroKey, toUpdate);
		}
				
		return updatedHeroes;
	}
	
	
	public static List<Hero> getHeroes(String filePath){
		JSONObject rawHeroJSON = vdfToJson(filePath);
		List<Hero> heroes = new ArrayList<Hero>();
		try {
			JSONObject jsonHeroes = (JSONObject) parseRawHeroJSON(rawHeroJSON);
			@SuppressWarnings("unchecked")
			Iterator<String> heroNames = jsonHeroes.keys();
			heroes = new ArrayList<Hero>();
			while(heroNames.hasNext()){
				String heroName = heroNames.next();
				heroes.add(new Hero(heroName, jsonHeroes.getJSONObject(heroName)));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return heroes;
	}
	
	public static JSONObject parseRawUnitJSON(JSONObject rawUnitJSON) 
			throws JSONException
	{
		JSONObject updatedUnits = new JSONObject();
		JSONObject rawUnitsJSON = (JSONObject) rawUnitJSON.get("DOTAUnits");
		JSONObject baseUnit = (JSONObject) rawUnitsJSON.get("npc_dota_unit_base");
		@SuppressWarnings("unchecked")
		Iterator<String> unitKeys = rawUnitsJSON.keys();
		while(unitKeys.hasNext()){
			String unitKey = unitKeys.next();
			if(unitKey.equals("Version") || unitKey.equals("npc_dota_units_base"))
				continue;
			JSONObject toUpdate = (JSONObject) rawUnitsJSON.get(unitKey);
			//Let's just work with neutrals for now!
			if(!toUpdate.has("IsNeutral")) continue;
			addDefaults(baseUnit, toUpdate);
			updatedUnits.put(unitKey, toUpdate);
		}
				
		return updatedUnits;
	}
	
	
	@SuppressWarnings("unchecked")
	public static List<Unit> getUnits(String filePath){
		JSONObject rawUnitJSON = vdfToJson(filePath);
		List<Unit> units = new ArrayList<Unit>();
		try {
			JSONObject jsonUnits = (JSONObject) parseRawUnitJSON(rawUnitJSON);
			Iterator<String> unitNames = jsonUnits.keys();
			units = new ArrayList<Unit>();
			while(unitNames.hasNext()){
				String unitName = unitNames.next();
				units.add(new Unit(unitName, jsonUnits.getJSONObject(unitName)));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return units;
	}
	

	public static JSONObject parseRawAbilityJSON(JSONObject rawAbilityJSON) 
			throws JSONException
	{
		JSONObject updatedAbilities = new JSONObject();
		JSONObject rawAbilitiesJSON = (JSONObject) rawAbilityJSON.get("DOTAAbilities");
		JSONObject baseAbility = (JSONObject) rawAbilitiesJSON.get("ability_base");
		@SuppressWarnings("unchecked")
		Iterator<String> abilityKeys = rawAbilitiesJSON.keys();
		while(abilityKeys.hasNext()){
			
			String abilityKey = abilityKeys.next();
			if(abilityKey.equals("Version") || abilityKey.equals("ability_base") || abilityKey.equals("default_attack") || abilityKey.equals("attribute_bonus"))
				continue;
			JSONObject toUpdate = (JSONObject) rawAbilitiesJSON.get(abilityKey);
			addDefaults(baseAbility, toUpdate);
			updatedAbilities.put(abilityKey, toUpdate);
		}
		return updatedAbilities;
	}
	
	//Requires that we populate the DB with Heroes first, so we can use the static map in the hero class for its ability mapping
	@SuppressWarnings("unchecked")
	public static List<HeroAbility> getHeroAbilities(String filePath){
		JSONObject rawAbilityJSON = vdfToJson(filePath);
		List<HeroAbility> abilities = new ArrayList<HeroAbility>();
		try {
			JSONObject jsonAbilities = (JSONObject) parseRawAbilityJSON(rawAbilityJSON);
			Iterator<String> abilityNames = jsonAbilities.keys();
			while(abilityNames.hasNext()){
				String abilityName = abilityNames.next();
				//If this label corresponds to a HeroAbility...
				if(HeroAbility.abilityNameToHero.containsKey(abilityName)){
					System.out.println("Found an ability whose hero we know, constructing it now");
					abilities.add(new HeroAbility(abilityName, jsonAbilities.getJSONObject(abilityName)));
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return abilities;
	}


	private static JSONObject trimRawItemJSON(JSONObject rawItemJSON)
		throws JSONException
	{
		rawItemJSON = (JSONObject) rawItemJSON.get("DOTAAbilities");
		rawItemJSON.remove("Version");
		JSONObject updatedItems = new JSONObject();
		@SuppressWarnings("unchecked")
		Iterator<String> itemKeys = rawItemJSON.keys();
		while(itemKeys.hasNext()){
			String itemKey = itemKeys.next();
			JSONObject item = rawItemJSON.getJSONObject(itemKey);
			int idOfThisItem = item.getInt("ID");
			if(idOfThisItem < 216 || idOfThisItem == 243 || idOfThisItem == 242)
				updatedItems.put(itemKey, item);
		}
				
		return updatedItems;
	}
	
	

	public static List<Item> getItems(String filePath){
		List<Item> items = new ArrayList<Item>();
		JSONObject rawJSON = vdfToJson(filePath);
		try {
			JSONObject trimmedJSON = trimRawItemJSON(rawJSON);
			@SuppressWarnings("unchecked")
			Iterator<String> itemNames = trimmedJSON.keys();
			while(itemNames.hasNext()){
				String itemName = itemNames.next();
				JSONObject item = trimmedJSON.getJSONObject(itemName);
				items.add(new Item(itemName, item));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return items;
	}
	
	
	//@SuppressWarnings("unchecked")
	public static void main(String[] args){
		List<HeroAbility> abilities = getHeroAbilities("text/npc_abilities.txt");
		/*
		Iterator<String> abilityNames = heroAbilities.keys();
		String abilityName, heroName;
		Set<String> uniqueNames = new HashSet<String>();
		while(abilityNames.hasNext()){
			abilityName = abilityNames.next();
			heroName = abilityName.substring(0, abilityName.indexOf('_'));
			uniqueNames.add(heroName);
		}
		Iterator<String> nameIter = uniqueNames.iterator();
		while(nameIter.hasNext())
			System.out.println(nameIter.next());
		 */
	}
	
	/*
	//Prints all unique labels within AbilitySpecial
	@SuppressWarnings("unchecked")
	public static void main(String[] args){
		HashSet<String> effectLabels = new HashSet<String>();
		JSONObject rawItemJSON = vdfToJson("text/items.txt");
		try {
			JSONObject trimmedItemJSON = trimRawItemJSON(rawItemJSON);
			//System.out.println(trimmedItemJSON.toString().substring(0,  500));
			Iterator<String> itemKeys = trimmedItemJSON.keys();
			while(itemKeys.hasNext()){
				JSONObject oneItem = trimmedItemJSON.getJSONObject(itemKeys.next());
				if(oneItem.has("AbilitySpecial")){
					JSONObject abilitySpecial = oneItem.getJSONObject("AbilitySpecial");
					Iterator<String> effectNumbers = abilitySpecial.keys();
					while(effectNumbers.hasNext()){
						JSONObject oneEffect = abilitySpecial.getJSONObject(effectNumbers.next());
						if(oneEffect.has("var_type"))
							oneEffect.remove("var_type");
						Iterator<String> effects = oneEffect.keys();
						while(effects.hasNext()){
							effectLabels.add(effects.next());
						}
					}
				}
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Iterator<String> effectIter = effectLabels.iterator();
		int i = 0;
		while(effectIter.hasNext()){
			i++;
			System.out.println(i + "\t" + effectIter.next());
		}
	}
	*/
	
	/*
	//Prints the number of non-recipe items and the number of those items that have the field "AbilityBehavior"
	@SuppressWarnings("unchecked")
	public static void main(String[] args){
		JSONObject rawItemJSON = vdfToJson("text/items.txt");
		try {
			JSONObject trimmedItemJSON = trimRawItemJSON(rawItemJSON);
			Iterator<String> itemKeys = trimmedItemJSON.keys();
			int itemCount = 0, abilityBehaviorCount = 0;
			while(itemKeys.hasNext()){
				JSONObject oneItem = trimmedItemJSON.getJSONObject(itemKeys.next());
				if(oneItem.has("ItemRecipe"))
					continue;
				itemCount++;
				if(oneItem.has("ItemShopTags"))
					abilityBehaviorCount++;
			}
			System.out.println("Items: " + itemCount);
			System.out.println("Items with AbilityBehavior: " + abilityBehaviorCount);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	*/
}
