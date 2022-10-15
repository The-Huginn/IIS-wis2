package services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import entity.Lector;
import entity.Student;
import entity.StudyCourse;

@Path("/")
@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestService {

	@PersistenceContext(unitName = "mysqlDB")
	EntityManager em;
	
	@Path("/student/{uid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Student getStudent(@PathParam("uid") long uid) {
		return em.find(Student.class, uid);
	}

	@Path("/lector/{uid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Lector getLector(@PathParam("uid") long uid) {
		return em.find(Lector.class, uid);
	}

	@Path("/course/{uid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public StudyCourse getStudyCourse(@PathParam("uid") long uid) {
		return em.find(StudyCourse.class, uid);
	}
}
