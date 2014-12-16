package access;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Hero;
import model.HeroInstance;

public class HeroDao {
	private EntityManagerFactory factory = Persistence.createEntityManagerFactory("DotaApp");
	private EntityManager em = null;
	private static HeroDao instance = null;
	
	protected HeroDao(){
		em = factory.createEntityManager();
	}
	
	public static HeroDao getInstance(){
		if(instance == null)
			instance = new HeroDao();
		return instance;
	}
	
	public Hero findHero(int id){
		Hero hero = null;
		em.getTransaction().begin();
		hero = em.find(Hero.class, id);
		em.getTransaction().commit();
		return hero;
	}
	
	public Hero findHero(String heroName){
		Hero hero = null;
		em.getTransaction().begin();
		hero = em.find(Hero.class, heroName);
		em.getTransaction().commit();
		return hero;
	}
	
	@SuppressWarnings("unchecked")
	public List<Hero> findAllHeroes(){
		List<Hero> heroes = new ArrayList<Hero>();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("Site.findAll");
		heroes = q.getResultList();
		em.getTransaction().commit();
		return heroes;
	}
	
	public void createHero(Hero hero){
		em.getTransaction().begin();
		em.persist(hero);
		em.getTransaction().commit();
	}
	
	public List<HeroInstance> makeHeroInstances(int level){
		List<Hero> baseHeroes = this.findAllHeroes();
		List<HeroInstance> heroesForLevel = new ArrayList<HeroInstance>();
		for(Hero hero:baseHeroes)
			heroesForLevel.add(new HeroInstance(hero, level));
		return heroesForLevel;
	}
	
	public HeroInstance makeHeroInstanceForName(String heroName, int level){
		Hero hero = this.findHero(heroName);
		return new HeroInstance(hero, level);
	}
	
	public HeroInstance makeHeroInstanceForId(int heroId, int level){
		Hero hero = this.findHero(heroId);
		return new HeroInstance(hero, level);
	}
	
	
	
	
	
	
	
	public void initHeroes(List<Hero> heroes){
		em.getTransaction().begin();
		Query q = em.createNamedQuery("Hero.dropAll");
		q.executeUpdate();
		em.getTransaction().commit();
		for(Hero hero:heroes)
			createHero(hero);
	}
	
}
