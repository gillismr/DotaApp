package populate;

import java.util.ArrayList;
import java.util.List;

import access.HeroDao;
import access.ItemDao;
import access.RecipeDao;
import model.Hero;
import model.Item;
import model.Recipe;

public class MyDatabasePopulater {

	public static void main(String[] args) {
		List<Hero> heroes = VdfConverter.getHeroes("text/npc_heroes.txt");
		HeroDao heroDao = HeroDao.getInstance();
		heroDao.initHeroes(heroes);
		/*
		List<Item> items = new ArrayList<Item>();
		List<Recipe> recipes = new ArrayList<Recipe>();
		//List<ItemAbility> itemAbilities = new ArrayList<ItemAbility>();
		//Populate the three given list with their proper elements found in the given text file
		//VdfConverter.getItemsAndMore("text/items.txt", items, recipes/*itemAbilities);
		
		items = VdfConverter.getItems("text/items.txt");
		ItemDao itemDao = ItemDao.getInstance();
		itemDao.initItems(items);
		
		recipes = VdfConverter.getRecipes("text/items.txt");
		RecipeDao recipeDao = RecipeDao.getInstance();
		recipeDao.initRecipes(recipes);
		*/
	}

}
