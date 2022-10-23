package restfulAPI;

import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import entity.CourseDate;
import entity.Lector;
import entity.Room;
import entity.Student;
import entity.StudyCourse;
import helper.IResponseBuilder;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import services.interfaces.IGuarantService;

@Path("/guarant")
@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("lector")
public class RestGuarantService {

	@PersistenceContext(unitName = "primary")
	EntityManager em;

	@Resource
	EJBContext ejb;

	@Inject
	private IResponseBuilder rb;

	@Inject
	private IGuarantService guarantService;

    @Path("/course/myCourses")
    @GET
    @ApiOperation(value = "Finds all courses that Guarant leads")
    public List<StudyCourse> getMyCourses() {
        return guarantService.getGuarantCourses(ejb.getCallerPrincipal().getName());
    }

	@Path("/student/{course_uid}")
	@GET
	@ApiOperation(value = "Finds all students with pending registration in course with specified uid.")
	public List<Student> getStudentsWithRegistration(
        @ApiParam(required = true, example = "10") @PathParam("course_uid") long course_uid
        ) {
		return guarantService.getStudentsWithRegistration(course_uid);
	}

	@Path("/course/add_student")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public void addStudent(@QueryParam("course_uid") long course_uid, @QueryParam("student_uid") long student_uid) {
		StudyCourse course = em.find(StudyCourse.class, course_uid);
		Student student = em.find(Student.class, student_uid);
		course.addStudent(student);
		student.addCourse(course);
		em.persist(course);
		em.persist(student);
	}

	@Path("/course/add_lector")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public void addLector(@QueryParam("course_uid") long course_uid, @QueryParam("lector_uid") long lector_uid) {
		StudyCourse course = em.find(StudyCourse.class, course_uid);
		Lector lector = em.find(Lector.class, lector_uid);
		course.addLector(lector);
		lector.addCourse(course);
		em.persist(course);
		em.persist(lector);
	}

    @Path("/courseDate/create")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public void createCourseDate(@QueryParam("course_uid") long course_uid,
                                    @QueryParam("room_uid") long room_uid, CourseDate date)
    {
        StudyCourse course = em.find(StudyCourse.class, course_uid);
        Room room = em.find(Room.class, room_uid);
        date.setCourse(course);
        date.setRoom(room);
        em.persist(date);
	}
}
