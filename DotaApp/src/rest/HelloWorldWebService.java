package rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/hello")
public class HelloWorldWebService {

	public HelloWorldWebService() {
		// TODO Auto-generated constructor stub
	}
	
	@GET
	@Path("/sayHello")
	public String sayHello(){
		return "Hello world!";
	}

}
