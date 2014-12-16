package rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import model.Item;
import access.ItemDao;

@Path("/item_ws")
public class ItemWebService {

	public ItemDao dao = ItemDao.getInstance();
	
	public ItemWebService() {
		// TODO Auto-generated constructor stub
	}
	
	@GET
	@Produces("application/json")
	@Path("/all")
	public List<Item> getAllItems(){
		return dao.findAllItems();
	}
	
	@GET
	@Produces("application/json")
	@Path("/id?={id}")
	public Item getItemsForId(@PathParam("id") int id){
		return dao.findItem(id);
	}
	
	@GET
	@Produces("application/json")
	@Path("/name?={name}")
	public Item getItemForName(@PathParam("name") String name){
		return dao.findItem(name);
	}
	
	@GET
	@Produces("application/json")
	@Path("/all_parts_item_id?={id}")
	public List<Item> getAllComponentsForId(@PathParam("id") int id){
		return dao.findAllComponentsExhaustive(id);
	}
	
	@GET
	@Produces("application/json")
	@Path("/all_parts_item_name?={name}")
	public List<Item> getAllComponentsForId(@PathParam("name") String name){
		return dao.findAllComponentsExhaustive(name);
	}
	
	@GET
	@Produces("application/json")
	@Path("/all_aura_items")
	public List<Item> getAllItemsWithAura(){
		return dao.findAllItemsWithAura();
	}

	@GET
	@Produces("application/json")
	@Path("/all_items_with_active")
	public List<Item> getAllItemsWithAbility(){
		return dao.findAllItemsWithAbility();
	}

}
