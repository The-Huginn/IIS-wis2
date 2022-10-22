package restfulAPI;

import javax.annotation.security.RolesAllowed;
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
import entity.DateEvaluation;
import entity.Student;

@Path("/student")
@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("student")
public class RestStudentService {

	@PersistenceContext(unitName = "primary")
	EntityManager em;

	@Path("/dateEval/create")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public void addGuarant(@QueryParam("date_uid") long date_uid,
							@QueryParam("student_uid") long student_uid, DateEvaluation dateEval)
	{
		CourseDate date = em.find(CourseDate.class, date_uid);
		Student student = em.find(Student.class, student_uid);
		dateEval.setCourseDate(date);
		dateEval.setStudent(student);
		em.persist(dateEval);
	}
}
