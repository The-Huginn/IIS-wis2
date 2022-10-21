package restfulAPI;

import java.security.NoSuchAlgorithmException;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBContext;
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

import entity.Person;

@Path("/")
@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@PermitAll
public class RestService {

	@PersistenceContext(unitName = "postgresDB")
	EntityManager em;
	
	@Resource
	EJBContext ctx;

	@GET
	@Path("/json")
	@Produces(MediaType.APPLICATION_JSON)
	public String helloJSON() throws NoSuchAlgorithmException{
		return ctx.getCallerPrincipal().getName() + ctx.isCallerInRole("admin");
		// System.err.println(service.encode("admin:ApplicationRealm:admin"));
		// System.err.println("JBoss Home: "+System.getProperty("jboss.server.config.dir"));
		// return service.encode("admin:ApplicationRealm:admin");
		// service.stub();
		// return "Hello World by JSON!";
	}
	
	@GET
	@Path("/xml")
	@RolesAllowed("admin")
	@Produces(MediaType.APPLICATION_XML)
	public String helloXML() {
		return "Hello World by XML!";
	}
	
	@Path("/person/{uid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Person getPerson(@PathParam("uid") long uid) {
		return em.find(Person.class, uid);
	}
	
	@Path("/person")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public void createPerson(Person person) {
		em.persist(person);
	}
}