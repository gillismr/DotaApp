package rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

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
	@Path("/base_hero_id")
	public Hero getHeroForId(@QueryParam("id") int id){
		return dao.findHero(id);
	}
		
	@GET
	@Produces("application/json")
	@Path("/all_heroes_at_level")
	public List<HeroInstance> getAllHeroInstances(@QueryParam("level") int level){
		return dao.makeHeroInstances(level);
	}
	
	@GET
	@Produces("application/json")
	@Path("/hero_id_at_level")
	public HeroInstance getHeroInstances(@QueryParam("id") int id, @QueryParam("level") int level){
		return dao.makeHeroInstanceForId(id, level);
	}
	

}
