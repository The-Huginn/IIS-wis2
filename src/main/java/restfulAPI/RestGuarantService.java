package restfulAPI;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import entity.CourseDate;
import entity.Lector;
import entity.Room;
import entity.Student;
import entity.StudyCourse;

@Path("/garant")
@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestGuarantService {

	@PersistenceContext(unitName = "primary")
	EntityManager em;

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
