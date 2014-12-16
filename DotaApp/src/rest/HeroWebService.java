package rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import model.Hero;
import model.HeroInstance;
import access.HeroDao;

@Path("/hero_ws")
public class HeroWebService {

	public HeroDao dao = HeroDao.getInstance();
	
	@GET
	@Produces("application/json")
	@Path("/all_base_heroes")
	public List<Hero> getAllHeroes(){
		return dao.findAllHeroes();
	}
	
	@GET
	@Produces("application/json")
	@Path("/base_hero_id?={id}")
	public Hero getHeroForId(@PathParam("id") int id){
		return dao.findHero(id);
	}
	
	@GET
	@Produces("application/json")
	@Path("/base_hero_name?={name}")
	public Hero getHeroForName(@PathParam("name") String name){
		return dao.findHero(name);
	}
	
	@GET
	@Produces("application/json")
	@Path("/all_heroes_at_level?={level}")
	public List<HeroInstance> getAllHeroInstances(@PathParam("level") int level){
		return dao.makeHeroInstances(level);
	}
	
	@GET
	@Produces("application/json")
	@Path("/hero_id=?{id}_at_level?={level}")
	public HeroInstance getHeroInstances(@PathParam("id") int id, @PathParam("level") int level){
		return dao.makeHeroInstanceForId(id, level);
	}
	
	@GET
	@Produces("application/json")
	@Path("/hero_name=?{name}_at_level?={level}")
	public HeroInstance getHeroInstances(@PathParam("name") String name, @PathParam("level") int level){
		return dao.makeHeroInstanceForName(name, level);
	}
	
	
	

}
