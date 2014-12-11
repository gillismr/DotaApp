package populate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import datacontainer.RawJsonAbility;
import datacontainer.RawJsonHero;
import datacontainer.RawJsonItem;

public class JsonFromHttp {
		
	public static void main(String[] args) {
		JSONObject itemData = getJSONFromUrl("http://www.dota2.com/jsfeed/itemdata");
		List<RawJsonItem> items = parseJsonItemData(itemData);
		
		JSONObject abilityData = getJSONFromUrl("http://www.dota2.com/jsfeed/abilitydata");
		List<RawJsonAbility> abilities = parseJsonAbilityData(abilityData);
		
		JSONObject heroData = getJSONFromUrl("http://www.dota2.com/jsfeed/heropediadata?feeds=herodata");
		List<RawJsonHero> heroes = parseJsonHeroData(heroData);
				
		
		int itemLength = items.size();
		System.out.println("Number of items: " + itemLength);
		int abilityLength = abilities.size();
		System.out.println("Number of abilities: " + abilityLength);
		int heroLength = heroes.size();
		System.out.println("Number of heroes: " + heroLength);
		
		
		
	
	}
	
	public static JSONObject getJSONFromUrl(String urlString) {
		JSONObject json = new JSONObject();
		try {
			URL url;
			url = new URL(urlString);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			InputStream stream = connection.getInputStream();
			InputStreamReader reader = new InputStreamReader(stream);
			BufferedReader buffer = new BufferedReader(reader);
			
			String theData = buffer.readLine();
			JSONParser parser = new JSONParser();
			json = (JSONObject)parser.parse(theData);
			
		} catch (MalformedURLException e) {
			System.out.println("Problem with the URL");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Problem reading the data from the HttpUrlConnection");
			e.printStackTrace();
		} catch (ParseException e) {
			System.out.println("Problem parsing the data String into a JSONObject");
			e.printStackTrace();
		}
		
		return json;

	}
	
	public static List<RawJsonItem> parseJsonItemData(JSONObject itemData){
		List<RawJsonItem> rjiList = new ArrayList<RawJsonItem>();
		JSONObject items = (JSONObject)itemData.get("itemdata");
		
		//Better way to do this with only one reference to the Map?
		//Need to be able to iterate over entrySet rather than keySet
		for(Iterator<?> keyIter = items.keySet().iterator(); keyIter.hasNext();){
			String internalName = (String)keyIter.next();
			JSONObject oneItemData = (JSONObject) items.get(internalName);
			RawJsonItem oneRji = new RawJsonItem(internalName, oneItemData);
			rjiList.add(oneRji);
		}
		
		
		return rjiList;
	}
	
	public static List<RawJsonAbility> parseJsonAbilityData(JSONObject abilityData){
		List<RawJsonAbility> rjaList = new ArrayList<RawJsonAbility>();
		JSONObject abilities = (JSONObject)abilityData.get("abilitydata");
		
		//Better way to do this with only one reference to the Map?
		//Need to be able to iterate over entrySet rather than keySet
		for(Iterator<?> keyIter = abilities.keySet().iterator(); keyIter.hasNext();){
			String internalName = (String)keyIter.next();
			JSONObject oneAbilityData = (JSONObject) abilities.get(internalName);
			RawJsonAbility oneRja = new RawJsonAbility(internalName, oneAbilityData);
			rjaList.add(oneRja);
		}
				
		return rjaList;
		
	}
	
	public static List<RawJsonHero> parseJsonHeroData(JSONObject heroData){
		List<RawJsonHero> rjhList = new ArrayList<RawJsonHero>();
		JSONObject heroes = (JSONObject) heroData.get("herodata");
		
		//Better way to do this with only one reference to the Map?
		//Need to be able to iterate over entrySet rather than keySet
		for(Iterator<?> heroIter = heroes.keySet().iterator(); heroIter.hasNext();){
			String internalName = (String)heroIter.next();
			JSONObject oneHeroData = (JSONObject) heroes.get(internalName);
			RawJsonHero oneRjh = new RawJsonHero(internalName, oneHeroData);
			rjhList.add(oneRjh);
		}
		
		return rjhList;
	}

}
