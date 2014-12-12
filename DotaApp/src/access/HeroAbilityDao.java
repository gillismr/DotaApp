package access;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.HeroAbility;

public class HeroAbilityDao {
	private EntityManagerFactory factory = Persistence.createEntityManagerFactory("DotaApp");
	private EntityManager em = null;
	private static HeroAbilityDao instance = null;
	
	protected HeroAbilityDao(){
		em = factory.createEntityManager();
	}
	
	public static HeroAbilityDao getInstance(){
		if(instance == null)
			instance = new HeroAbilityDao();
		return instance;
	}
	
	public HeroAbility findHeroAbility(int id){
		HeroAbility heroAbility = null;
		em.getTransaction().begin();
		heroAbility = em.find(HeroAbility.class, id);
		em.getTransaction().commit();
		return heroAbility;
	}
	
	@SuppressWarnings("unchecked")
	public List<HeroAbility> findAllHeroAbilities(){
		List<HeroAbility> heroAbilities = new ArrayList<HeroAbility>();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("Site.findAll");
		heroAbilities = q.getResultList();
		em.getTransaction().commit();
		return heroAbilities;
	}
	
	public void createHeroAbility(HeroAbility heroAbility){
		em.getTransaction().begin();
		em.persist(heroAbility);
		em.getTransaction().commit();
	}
	
	public void initHeroAbilityes(List<HeroAbility> heroAbilities){
		em.getTransaction().begin();
		Query q = em.createNamedQuery("HeroAbility.dropAll");
		q.executeUpdate();
		em.getTransaction().commit();
		for(HeroAbility heroAbility:heroAbilities)
			createHeroAbility(heroAbility);
	}
	
}

