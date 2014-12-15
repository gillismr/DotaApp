package access;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Component;

public class ComponentDao {
	private EntityManagerFactory factory = Persistence.createEntityManagerFactory("DotaApp");
	private EntityManager em = null;
	private static ComponentDao instance = null;
	
	protected ComponentDao(){
		em = factory.createEntityManager();
	}
	
	public static ComponentDao getInstance(){
		if(instance == null)
			instance = new ComponentDao();
		return instance;
	}
	
	public Component findComponent(int id){
		Component component = null;
		em.getTransaction().begin();
		component = em.find(Component.class, id);
		em.getTransaction().commit();
		return component;
	}
	
	@SuppressWarnings("unchecked")
	public List<Component> findAllComponents(){
		List<Component> components = new ArrayList<Component>();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("Component.findAll");
		components = q.getResultList();
		em.getTransaction().commit();
		return components;
	}
	
	public void createComponent(Component component){
		em.getTransaction().begin();
		em.persist(component);
		em.getTransaction().commit();
	}
	
	public void initComponents(){
		
	}

}
