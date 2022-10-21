package services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import entity.Lector;
import entity.Room;
import entity.Student;
import entity.StudyCourse;

@Path("/")
@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestAdminService {

	@PersistenceContext(unitName = "primary")
	EntityManager em;

	@Path("/course/create")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public void createCourse(StudyCourse course) {
		em.persist(course);
	}
	
	@Path("/lector/create")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public void createLector(Lector lector) {
		em.persist(lector);
	}
	
	@Path("/student/create")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public void createStudent(Student student) {
		em.persist(student);
	}

    @Path("/room/create")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public void createRoom(Room room) {
		em.persist(room);
	}

	@Path("/course/add_guarant")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public void addGuarant(@QueryParam("course_uid") long course_uid, @QueryParam("guarant_uid") long guarant_uid) {
		StudyCourse course = em.find(StudyCourse.class, course_uid);
		Lector lector = em.find(Lector.class, guarant_uid);
		course.setGuarant(lector);
		em.persist(course);
	}
	
}
