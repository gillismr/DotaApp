package populate;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
//import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import model.Hero;
import model.Item;

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
				//System.out.println(item.getInt("ID"));
				if(!item.has("ItemRecipe"))
					items.add(new Item(itemName, item));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return items;
	}
	
	/*
	public static List<Recipe> getRecipes(String filePath){
		List<Recipe> recipes = new ArrayList<Recipe>();
		JSONObject rawJSON = vdfToJson(filePath);
		try {
			JSONObject trimmedJSON = trimRawItemJSON(rawJSON);
			@SuppressWarnings("unchecked")
			Iterator<String> itemNames = trimmedJSON.keys();
			while(itemNames.hasNext()){
				String itemName = itemNames.next();
				JSONObject item = trimmedJSON.getJSONObject(itemName);
				if(item.has("ItemRecipe"))
					recipes.add(new Recipe(itemName, item));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return recipes;
	}
	*/
	
	/*
	//Not used currently
	public static void getItemsAndMore(
			String filePath, 
			List<Item> items, 
			List<Recipe> recipes, List<ItemAbility> itemAbilities)
	{
		JSONObject rawJSON = vdfToJson(filePath);
		try {
			JSONObject trimmedJSON = (JSONObject) trimRawItemJSON(rawJSON);
			@SuppressWarnings("unchecked")
			Iterator<String> itemNames = trimmedJSON.keys();
			while(itemNames.hasNext()){
				String itemName = itemNames.next();
				JSONObject item = trimmedJSON.getJSONObject(itemName);
				//System.out.println(item.getInt("ID"));
				if(item.has("ItemRecipe"))
					recipes.add(new Recipe(itemName, item));
				else
					items.add(new Item(itemName, item));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	*/
	
	
	
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
