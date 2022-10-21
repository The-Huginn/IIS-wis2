package restfulAPI;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import entity.Lector;
import entity.Room;
import entity.Student;
import entity.StudyCourse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import services.IAdminSecurityRealm;
import services.IAdminService;
import helper.IResponseBuilder;

@Path("/admin")
@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Student, Lector, Room and Course Administration")
@RolesAllowed("admin")
public class RestAdminService {

	@Inject
    private IAdminSecurityRealm adminSecurityService;

	@Inject
	private IAdminService adminService;

	@Inject
	private IResponseBuilder rb;

    @POST
    @Path("/user")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "Creates new user in the system, with initial credentials")
    public Response addUser(
        @ApiParam(required = true, example = "xlogin00") @FormParam("username") final String username,
        @ApiParam(required = true, example = "cGFzc3dvcmQxMjM=", value = "Expecting Base64 encoded password, e.g. password123 = cGFzc3dvcmQxMjM=") @FormParam("password") final String password
        ) {
        return rb.createResponse(adminSecurityService.addUser(username, password));
    }

    @POST
    @Path("/roles")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "Adds roles to existing user")
    public Response addRoles(
        @ApiParam(required = true, example = "xlogin00") @FormParam("username") final String username,
        @ApiParam(required = true, example = "admin\nuser") @FormParam("roles") final List<String> roles
        ) {
        return rb.createResponse(adminSecurityService.addRoles(username, roles));
    }

    @DELETE
    @Path("/user")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation("Removes user from the application")
    public Response removeUser(
        @ApiParam(required = true, example = "xlogin00") @FormParam("username") final String username
        ) {
        return rb.createResponse(adminSecurityService.removeUser(username));
    }

    @DELETE
    @Path("/roles")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "Removes selected roles from the user", notes = "User does not have to own these roles")
    public Response removeRoles(
        @ApiParam(required = true, example = "xlogin00") @FormParam("username") final String username,
        @ApiParam(required = true, example = "admin\nuser") @FormParam("roles") final List<String> roles
        ) {
        return rb.createResponse(adminSecurityService.removeRoles(username, roles));
    }	

	@Path("/course")
	@POST
	public Response createCourse(StudyCourse course) {
		return rb.createResponse(adminService.createCourse(course));
	}
	
	@Path("/lector")
	@POST
	@ApiOperation(value = "Creates new lector with initial password. This password can be changed at public API")
	public Response createLector(
        @ApiParam(required = true) Lector lector,
        @ApiParam(required = true, example = "cGFzc3dvcmQxMjM=", value = "Expecting Base64 encoded password, e.g. password123 = cGFzc3dvcmQxMjM=") @QueryParam("password") String password) {
        
        String reply = adminSecurityService.addUser(lector.getUsername(), password);
        if (reply != null)
            return rb.createResponse(reply);

        return rb.createResponse(adminService.createLector(lector));
	}
	
	@Path("/student")
	@POST
    @ApiOperation(value = "Creates new student with initial password. This password can be changed at public API")
	public Response createStudent(
        @ApiParam(required = true) Student student,
        @ApiParam(required = true, example = "cGFzc3dvcmQxMjM=", value = "Expecting Base64 encoded password, e.g. password123 = cGFzc3dvcmQxMjM=") @QueryParam("password") String password) {
        
        String reply = adminSecurityService.addUser(student.getUsername(), password);
        if (reply != null)
            return rb.createResponse(reply);

        return rb.createResponse(adminService.createStudent(student));
	}

    @Path("/room")
	@POST
    @ApiOperation(value = "Creates room")
	public Response createRoom(
        @ApiParam(required = true) Room room
        ) {
        return rb.createResponse(adminService.createRoom(room));
	}

	@Path("/guarant/{course_uid}/{guarant_uid}")
	@POST
    @ApiOperation(value = "Assigns Lector as guarant to course")
	public Response addGuarant(
        @ApiParam(required = true, example = "10") @PathParam("course_uid") long course_uid,
        @ApiParam(required = true, example = "10") @PathParam("guarant_uid") long guarant_uid
        ) {
        return rb.createResponse(adminService.addGuarant(course_uid, guarant_uid));
	}

    @Path("/course")
    @GET
    @ApiOperation(value = "Finds all available courses")
    public List<StudyCourse> getCourses() {
        return adminService.getCourses();
    }

    @Path("/course/{course_uid}")
    @GET
    @ApiOperation(value = "Finds course with specified uid")
    public StudyCourse getCourse(
        @ApiParam(required = true, example = "10") @PathParam("course_uid") long course_uid
        ) {
        return adminService.getCourse(course_uid);
    }

    @Path("/student")
    @GET
    @ApiOperation("Finds all existing students")
    public List<Student> getStudents() {
        return adminService.getStudents();
    }

    @Path("/student/{student_uid}")
    @GET
    @ApiOperation(value = "Finds student with specified uid")
    public Student getStudent(
        @ApiParam(required = true, example = "10") @PathParam("student_uid") long student_uid
        ) {
        return adminService.getStudent(student_uid);
    }

    @Path("/lector")
    @GET
    @ApiOperation(value = "Finds all existing lectors")
    public List<Lector> getLectors() {
        return adminService.getLectors();
    }

    @Path("/lector/{lector_uid}")
    @GET
    @ApiOperation(value = "Finds lector with specified uid")
    public Lector getLector(
        @ApiParam(required = true, example = "10") @PathParam("lector_uid") long lector_uid
        ) {
        return adminService.getLector(lector_uid);
    }

    @Path("/room")
    @GET
    @ApiOperation(value = "Finds all existing rooms")
    public List<Room> getRooms() {
        return adminService.getRooms();
    }

    @Path("/room/{room_uid}")
    @GET
    @ApiOperation("Finds room with specified uid")
    public Room getRoom(
        @ApiParam(required = true, example = "10") @PathParam("room_uid") long room_uid
        ) {
        return adminService.getRoom(room_uid);
    }

    @Path("/guarant")
    @GET
    @ApiOperation(value = "Finds all lectors who are guarants at least for one subject")
    public List<Lector> getGuarants() {
        return adminService.getGuarants();
    }

    @Path("/guarant/{course_uid}")
    @GET
    @ApiOperation(value = "Finds guarant for course specified with uid")
    public Lector getGuarant(
        @ApiParam(required = true, example = "10") @PathParam("course_uid") long course_uid
        ) {
        return adminService.getGuarant(course_uid);
    }

    @Path("course/{course_uid}")
    @DELETE
    @ApiOperation(value = "Removes course specified by uid")
    public Response removeCourse(
        @ApiParam(required = true, example = "10") @PathParam("course_uid") long course_uid
        ) {
        return rb.createResponse(adminService.removeCourse(course_uid));
    }

    @Path("lector/{lector_uid}")
    @DELETE
    @ApiOperation(value = "Removes lector specified by uid")
    public Response removeLector(
        @ApiParam(required = true, example = "10") @PathParam("lector_uid") long lector_uid
        ) {
        return rb.createResponse(adminService.removeLector(lector_uid));
    }

    @Path("student/{student_uid}")
    @DELETE
    @ApiOperation(value = "Removes student specified by uid")
    public Response removeStudent(
        @ApiParam(required = true, example = "10") @PathParam("student_uid") long student_uid
        ) {
        return rb.createResponse(adminService.removeStudent(student_uid));
    }

    @Path("room/{room_uid}")
    @DELETE
    @ApiOperation(value = "Removes room specified by uid")
    public Response removeRoom(
        @ApiParam(required = true, example = "10") @PathParam("room_uid") long room_uid
        ) {
        return rb.createResponse(adminService.removeRoom(room_uid));
    }
}
