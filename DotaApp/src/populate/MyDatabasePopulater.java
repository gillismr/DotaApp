package populate;

import java.util.ArrayList;
import java.util.List;

import access.HeroAbilityDao;
import access.HeroDao;
import access.ItemDao;
import model.Hero;
import model.HeroAbility;
import model.Item;

public class MyDatabasePopulater {

	public static void main(String[] args) {
		List<Hero> heroes = VdfConverter.getHeroes("text/npc_heroes.txt");
		HeroDao heroDao = HeroDao.getInstance();
		heroDao.initHeroes(heroes);
		
		List<Item> items = new ArrayList<Item>();
		
		items = VdfConverter.getItems("text/items.txt");
		ItemDao itemDao = ItemDao.getInstance();
		itemDao.initItems(items);
		//Find all non-item recipes and drop them after setting relevant references
		//Also set relevant references for items with recipes 
		Item.setRecipes(itemDao.findAllItems());
		
		List<HeroAbility> heroAbilities = new ArrayList<HeroAbility>();
		heroAbilities = VdfConverter.getHeroAbilities("text/npc_abilities.txt");
		
		
	}

}
