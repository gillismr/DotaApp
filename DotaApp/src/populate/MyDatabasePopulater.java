package populate;

import java.util.ArrayList;
import java.util.List;

import access.HeroDao;
import access.ItemDao;
import model.Hero;
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
		
	}

}
