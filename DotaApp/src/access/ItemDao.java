package access;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Item;

public class ItemDao {
	private EntityManagerFactory factory = Persistence.createEntityManagerFactory("DotaApp");
	private EntityManager em = null;
	private static ItemDao instance = null;
	
	protected ItemDao(){
		em = factory.createEntityManager();
	}
	
	public static ItemDao getInstance(){
		if(instance == null)
			instance = new ItemDao();
		return instance;
	}
	
	public Item findItem(int id){
		Item item = null;
		em.getTransaction().begin();
		item = em.find(Item.class, id);
		em.getTransaction().commit();
		return item;
	}
	
	@SuppressWarnings("unchecked")
	public List<Item> findAllItems(){
		List<Item> items = new ArrayList<Item>();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("Item.findAll");
		items = q.getResultList();
		em.getTransaction().commit();
		return items;
	}
	
	public void createItem(Item item){
		em.getTransaction().begin();
		em.persist(item);
		em.getTransaction().commit();
	}
	
	public void initItems(List<Item> items){
		em.getTransaction().begin();
		Query q = em.createNamedQuery("Item.dropAll");
		q.executeUpdate();
		em.getTransaction().commit();
		for(Item item:items)
			createItem(item);
	}
	
}
