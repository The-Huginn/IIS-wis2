package restfulAPI;

import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBContext;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import entity.Lector;
import entity.Room;
import entity.Student;
import entity.StudyCourse;
import helper.IResponseBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import services.interfaces.IGuarantService;

@Path("/guarant")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("lector")
@Api(value = "Guarant actions")
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

	@Path("/course/addStudent/{course_uid}/{student_uid}")
	@POST
	@ApiOperation(value = "Add student to a study course.")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addStudent(
		@ApiParam(required = true, example = "10") @PathParam("course_uid") long course_uid,
		@ApiParam(required = true, example = "10") @PathParam("student_uid") long student_uid
	) {
		return rb.createResponse(guarantService.addStudentToCourse(course_uid, student_uid));
	}

	@Path("/course/addLector/{course_uid}/{lector_uid}")
	@POST
	@ApiOperation(value = "Add lector to a study course.")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addLector(
		@ApiParam(required = true, example = "10") @PathParam("course_uid") long course_uid,
		@ApiParam(required = true, example = "10") @PathParam("lector_uid") long lector_uid
	) {
		return rb.createResponse(guarantService.addLectorToCourse(course_uid, lector_uid));
	}

    @Path("/courseDate/{course_uid}/{room_uid}")
    @POST
	@ApiOperation(value = "Create new course date.")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCourseDate(
		@ApiParam(required = true, example = "10") @PathParam("course_uid") long course_uid,
		@ApiParam(required = true, example = "10") @PathParam("room_uid") long room_uid
	) {
        return rb.createResponse(guarantService.createCourseDate(course_uid, room_uid));
	}

	@Path("/room")
    @GET
    @ApiOperation(value = "Returns list of all rooms.")
    public List<Room> getRooms() {
        return guarantService.getRooms();
    }

	@Path("/lector")
	@GET
	@ApiOperation(value = "Returns list of all lectors.")
	public List<Lector> getLectors() {
		return guarantService.getLectors();
	}
}
