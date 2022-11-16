package restfulAPI;

import java.security.NoSuchAlgorithmException;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import services.interfaces.ISecurityRealm;

@Path("/public")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@PermitAll
public class RestPublicService {
	
	@Context
	SecurityContext ctx;

	@Inject
	ISecurityRealm x;

	@GET
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public String helloJSON() throws NoSuchAlgorithmException {
		String user = ctx.getUserPrincipal() == null ? "Anonymous" : ctx.getUserPrincipal().getName();
		System.err.println(user);
		return user + " " + (ctx.getUserPrincipal() == null ? "Definitely false" : ctx.isUserInRole("admin"));
		// return x.stub();
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

	// @Path("/course/{uid}")
	// @GET
	// @Produces(MediaType.APPLICATION_JSON)
	// public StudyCourse getStudyCourse(@PathParam("uid") long uid) {
	// 	return em.find(StudyCourse.class, uid);
	// }
}
