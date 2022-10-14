package services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import entity.IPerson;

@Path("/")
@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestService {

	@PersistenceContext(unitName = "mysqlDB")
	EntityManager em;
	
	@GET
	@Path("/json")
	@Produces(MediaType.APPLICATION_JSON)
	public String helloJSON() {
		return "Hello World by JSON!";
	}
	
	@GET
	@Path("/xml")
	@Produces(MediaType.APPLICATION_XML)
	public String helloXML() {
		return "Hello World by XML!";
	}
	
	@Path("/person/{uid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public IPerson getPerson(@PathParam("uid") long uid) {
		return em.find(IPerson.class, uid);
	}
	
	@Path("/person")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public void createPerson(IPerson person) {
		em.persist(person);
	}
}
