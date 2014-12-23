package access;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Component;
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
		//em.getTransaction().begin();
		//Query q = em.createNamedQuery("Item.dropAll");
		//q.executeUpdate();
		//em.getTransaction().commit();
		for(Item item:items)
			createItem(item);
	}
	
	public void removeItem(Item item){
		em.getTransaction().begin();
		em.remove(item);
		em.getTransaction().commit();
	}

	public List<Item> findAllComponentsExhaustive(int id){
		List<Item> allSubItems = new ArrayList<Item>();
		Item subItem = null;
		Item rootItem = this.findItem(id);
		for(Component onePart:rootItem.getMadeFrom()){
			subItem = this.findItem(onePart.getIsUsed().getId());
			allSubItems.add(subItem);
			allSubItems.addAll(this.findAllComponentsExhaustive(subItem.getId()));
		}
		return allSubItems;
	}

	
	@SuppressWarnings("unchecked")
	public List<Item> findAllItemsWithAura(){
		List<Item> auraItems = new ArrayList<Item>();
		em.getTransaction().begin();
		Query q = em.createQuery("SELECT * FROM item i JOIN itemaura ia ON ia.ITEM_ID = i.ID");
		auraItems = q.getResultList();
		em.getTransaction().commit();
		return auraItems;
	}
	

	@SuppressWarnings("unchecked")
	public List<Item> findAllItemsWithAbility(){
		List<Item> abilityItems = new ArrayList<Item>();
		em.getTransaction().begin();
		Query q = em.createQuery("SELECT i.* from item i join itemability ia ON ia.ITEM_ID = i.ID");
		abilityItems = q.getResultList();
		em.getTransaction().commit();
		return abilityItems;
	}

	
	
}
