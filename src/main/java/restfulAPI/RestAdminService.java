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
@Api(value = "Users' Administration")
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
	public Response createLector(Lector lector) {
        return rb.createResponse(adminService.createLector(lector));
	}
	
	@Path("/student")
	@POST
	public Response createStudent(Student student) {
        return rb.createResponse(adminService.createStudent(student));
	}

    @Path("/room")
	@POST
	public Response createRoom(Room room) {
        return rb.createResponse(adminService.createRoom(room));
	}

	@Path("/guarant/{course_uid}/{guarant_uid}")
	@POST
	public Response addGuarant(@PathParam("course_uid") long course_uid, @PathParam("guarant_uid") long guarant_uid) {
        return rb.createResponse(adminService.addGuarant(course_uid, guarant_uid));
	}

    @Path("/course")
    @GET
    public List<StudyCourse> getCourses() {
        return adminService.getCourses();
    }

    @Path("/course/{course_uid}")
    @GET
    public StudyCourse getCourse(@PathParam("course_uid") long course_uid) {
        return adminService.getCourse(course_uid);
    }

    @Path("/student")
    @GET
    public List<Student> getStudents() {
        return adminService.getStudents();
    }

    @Path("/student/{student_uid}")
    @GET
    public Student getStudent(@PathParam("student_uid") long student_uid) {
        return adminService.getStudent(student_uid);
    }

    @Path("/lector")
    @GET
    public List<Lector> getLectors() {
        return adminService.getLectors();
    }

    @Path("/lector/{lector_uid}")
    @GET
    public Lector getLector(@PathParam("lector_uid") long lector_uid) {
        return adminService.getLector(lector_uid);
    }

    @Path("/room")
    @GET
    public List<Room> getRooms() {
        return adminService.getRooms();
    }

    @Path("/room/{room_uid}")
    @GET
    public Room getRoom(@PathParam("room_uid") long room_uid) {
        return adminService.getRoom(room_uid);
    }

    @Path("/guarant")
    @GET
    public List<Lector> getGuarants() {
        return adminService.getGuarants();
    }

    @Path("/guarant/{course_uid}")
    @GET
    public Lector getGuarant(@PathParam("course_uid") long course_uid) {
        return adminService.getGuarant(course_uid);
    }

    @Path("course/{course_uid}")
    @DELETE
    public Response removeCourse(@PathParam("course_uid") long course_uid) {
        return rb.createResponse(adminService.removeCourse(course_uid));
    }

    @Path("lector/{lector_uid}")
    @DELETE
    public Response removeLector(@PathParam("lector_uid") long lector_uid) {
        return rb.createResponse(adminService.removeLector(lector_uid));
    }

    @Path("student/{student_uid}")
    @DELETE
    public Response removeStudent(@PathParam("student_uid") long student_uid) {
        return rb.createResponse(adminService.removeStudent(student_uid));
    }

    @Path("room/{room_uid}")
    @DELETE
    public Response removeRoom(@PathParam("room_uid") long room_uid) {
        return rb.createResponse(adminService.removeRoom(room_uid));
    }
}
