package access;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Hero;

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
	
	public Hero findSite(int id){
		Hero hero = null;
		em.getTransaction().begin();
		hero = em.find(Hero.class, id);
		em.getTransaction().commit();
		return hero;
	}
	
	@SuppressWarnings("unchecked")
	public List<Hero> findAllSites(){
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
	
	public void initHeroes(List<Hero> heroes){
		em.getTransaction().begin();
		Query q = em.createNamedQuery("Hero.dropAll");
		q.executeUpdate();
		em.getTransaction().commit();
		for(Hero hero:heroes)
			createHero(hero);
	}
	
}
