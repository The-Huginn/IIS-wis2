package restfulAPI;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
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
import dtos.Common.StudyCourseDTO;
import entity.Lector;
import entity.StudyCourse;
import helper.IResponseBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import services.interfaces.ILectorService;

@Path("/lector")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("lector")
@Api(value = "Lector actions")
public class RestLectorService {

	@PersistenceContext(unitName = "primary")
	EntityManager em;

	@Context
	SecurityContext ctx;

	@Inject
	private IResponseBuilder rb;

	@Inject
	private ILectorService lectorService;
	
	@Path("/course/myCourses")
    @GET
    @ApiOperation(value = "Finds all courses that Lector teaches")
    public List<StudyCourse> getMyCourses() {
        return lectorService.getLectorCourses(ctx.getUserPrincipal().getName());
    }

	@Path("/course")
    @GET
    @ApiOperation(value = "Finds all available courses")
    public List<StudyCourse> getCourses() {
        return lectorService.getCourses();
    }

    @Path("/course/{course_uid}")
    @GET
    @ApiOperation(value = "Finds course with specified uid")
    public StudyCourseDTO getCourse(
        @ApiParam(required = true, example = "10") @PathParam("course_uid") long course_uid
        ) {
        return lectorService.getCourse(course_uid);
    }

	// @Path("/student/{course_uid}")
    // @GET
    // @ApiOperation(value = "Finds all students in selected course")
    // public List<Student> getStudentsInCourse(
	// 	@ApiParam(required = true, example = "10") @PathParam("course_uid") long course_uid
	// 	) {
    //     return lectorService.getStudentsInCourse(ctx.getUserPrincipal().getName(), course_uid);
    // }

	// @Path("/courseDate/{course_uid}")
    // @GET
    // @ApiOperation(value = "Finds all course dates in selected course")
    // public List<CourseDate> getDatesInCourse(
	// 	@ApiParam(required = true, example = "10") @PathParam("course_uid") long course_uid
	// 	) {
    //     return lectorService.getDatesInCourse(course_uid);
    // }

	@Path("/courseDate/{courseDate_uid}")
    @GET
    @ApiOperation(value = "Finds course date with specified id")
    public CourseDateDTO getCourseDate(
        @ApiParam(required = true, example = "10") @PathParam("courseDate_uid") long courseDate_uid
        ) {
        return lectorService.getCourseDate(courseDate_uid);
    }

	// @Path("/dateEvaluation/{courseDate_uid}")
    // @GET
    // @ApiOperation(value = "Finds all date evaluations in selected course date")
    // public List<DateEvaluation> getEvaluationsInDate(
	// 	@ApiParam(required = true, example = "10") @PathParam("courseDate_uid") long courseDate_uid
	// 	) {
    //     return lectorService.getEvaluationsInDate(ctx.getUserPrincipal().getName(), courseDate_uid);
    // }

	// @Path("/dateEvaluation/{dateEvaluation_uid}")
    // @GET
    // @ApiOperation(value = "Finds date evaluation with specified id")
    // public DateEvaluation getDateEvaluation(
    //     @ApiParam(required = true, example = "10") @PathParam("dateEvaluation_uid") long dateEvaluation_uid
    //     ) {
    //     return lectorService.getDateEvaluation(ctx.getUserPrincipal().getName(), dateEvaluation_uid);
    // }

    @Path("/dateEvaluation/{dateEvaluation_uid}")
	@POST
	@ApiOperation(value = "Sets evaluation in date evaluation with specified id")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response addEvaluation(
		@ApiParam(required = true, example = "10") @PathParam("dateEvaluation_uid") long dateEvaluation_uid,
		@ApiParam(required = true, example = "69", value = "Value between 0-100")
			@FormParam("evaluation")
			@DecimalMin(value = "0.0", message = "Must be greater than 0.0 [RestLectorService.addEvaluation]")
			@DecimalMax(value = "100.0", message = "Must be smaller than 100.0 [RestLectorService.addEvaluation]") double evaluation
	) {
		return rb.createResponse(lectorService.addEvaluation(ctx.getUserPrincipal().getName(), evaluation, dateEvaluation_uid));
	}

    @Path("/update")
	@PUT
	@ApiOperation(value = "Updates lectors personal info")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateLectorsPersonalInfo(
		@ApiParam(required = true) Lector lector
	) {
		return rb.createResponse(lectorService.updatePersonalInfo(ctx.getUserPrincipal().getName(), lector));
	}
}
