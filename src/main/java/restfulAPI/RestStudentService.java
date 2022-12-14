package restfulAPI;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import dtos.Common.CourseDateDTO;
import dtos.Common.CourseDateDTOEval;
import dtos.Common.StudyCourseDTO;
import entity.DateEvaluation;
import entity.Student;
import entity.StudyCourse;
import helper.IResponseBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import services.interfaces.IStudentService;

@Path("/student")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("student")
@Api(value = "Student actions")
public class RestStudentService {

	@PersistenceContext(unitName = "primary")
	EntityManager em;

	@Context
	SecurityContext ctx;

	@Inject
	private IResponseBuilder rb;

	@Inject
	IStudentService studentService;

	@Path("/course")
    @GET
    @ApiOperation(value = "Finds all available courses")
    public List<StudyCourse> getCourses() {
        return studentService.getCourses();
    }

    @Path("/course/myCourses")
    @GET
    @ApiOperation(value = "Finds all courses that Student attends")
    public List<StudyCourse> getMyCourses() {
        return studentService.getStudentCourses(ctx.getUserPrincipal().getName());
    }

	@Path("/course/myEvaluations/{course_uid}")
    @GET
    @ApiOperation(value = "Finds all student's date evaluations in study course")
    public List<DateEvaluation> getMyEvaluations(
		@ApiParam(required = true, example = "10") @PathParam("course_uid") long course_uid
	) {
        return studentService.getStudentEvaluations(ctx.getUserPrincipal().getName(), course_uid);
    }

	@Path("/course/myCourseDates/{course_uid}")
    @GET
    @ApiOperation(value = "Finds all student's course dates in study course")
    public List<CourseDateDTOEval> getMyCourseDates(
		@ApiParam(required = true, example = "10") @PathParam("course_uid") long course_uid
	) {
        return studentService.getStudentCourseDates(ctx.getUserPrincipal().getName(), course_uid);
    }

	@Path("/course/myRegistrations")
    @GET
    @ApiOperation(value = "Finds all courses that Student has pending registration for")
    public List<StudyCourse> getMyCoursesWithRegistration() {
        return studentService.getStudentCoursesWithRegistration(ctx.getUserPrincipal().getName());
    }

    @Path("/course/{course_uid}")
    @GET
    @ApiOperation(value = "Finds course with specified uid")
    public StudyCourseDTO getCourse(
        @ApiParam(required = true, example = "10") @PathParam("course_uid") long course_uid
        ) {
        return studentService.getCourse(course_uid);
    }

    @Path("/course/{course_uid}")
	@POST
	@ApiOperation(value = "Creates new registration in study course with specified uid")
	@Produces(MediaType.APPLICATION_JSON)
	public Response createStudyCourseRegistration(
		@ApiParam(required = true, example = "10") @PathParam("course_uid") long course_uid
	) {
		return rb.createResponse(studentService.createStudyCourseRegistration(ctx.getUserPrincipal().getName(), course_uid));
	}

	@Path("/courseDate/{courseDate_uid}")
    @GET
    @ApiOperation(value = "Finds course date with specified id")
    public CourseDateDTO getCourseDate(
        @ApiParam(required = true, example = "10") @PathParam("courseDate_uid") long courseDate_uid
        ) {
        return studentService.getCourseDate(courseDate_uid);
    }

	@Path("/dateEvaluation/{courseDate_uid}")
	@POST
	@ApiOperation(value = "Creates new date evaluation")
	@Produces(MediaType.APPLICATION_JSON)
	public Response createDateEvaluation(
		@ApiParam(required = true, example = "10") @PathParam("courseDate_uid") long courseDate_uid
	) {
		return rb.createResponse(studentService.createDateEvaluation(ctx.getUserPrincipal().getName(), courseDate_uid));
	}

    @Path("/update")
	@PUT
	@ApiOperation(value = "Updates students personal info")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateStudentsPersonalInfo(
		@ApiParam(required = true) Student student
	) {
		return rb.createResponse(studentService.updatePersonalInfo(ctx.getUserPrincipal().getName(), student));
	}
}
