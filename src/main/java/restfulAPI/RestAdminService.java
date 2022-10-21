package restfulAPI;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import entity.Lector;
import entity.Room;
import entity.Student;
import entity.StudyCourse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import services.IAdminSecurityRealm;

@Path("/admin")
@Stateless
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Users' Administration")
@RolesAllowed("admin")
public class RestAdminService {

	@Inject
    IAdminSecurityRealm AdminService;

    @POST
    @Path("/user")
    @ApiOperation(value = "Creates new user in the system, with initial credentials")
    public String addUser(
        @ApiParam(required = true, example = "xlogin00") @FormParam("username") final String username,
        @ApiParam(required = true, example = "cGFzc3dvcmQxMjM=", value = "Expecting Base64 encoded password, e.g. password123 = cGFzc3dvcmQxMjM=") @FormParam("password") final String password
        ) {
        return AdminService.addUser(username, password);
    }

    @POST
    @Path("/roles")
    @ApiOperation(value = "Adds roles to existing user")
    public String addRoles(
        @ApiParam(required = true, example = "xlogin00") @FormParam("username") final String username,
        @ApiParam(required = true, example = "admin\nuser") @FormParam("roles") final List<String> roles
        ) {
        return AdminService.addRoles(username, roles);
    }

    @DELETE
    @Path("/user")
    @ApiOperation("Removes user from the application")
    public String removeUser(
        @ApiParam(required = true, example = "xlogin00") @FormParam("username") final String username
        ) {
        return AdminService.removeUser(username);
    }

    @DELETE
    @Path("/roles")
    @ApiOperation(value = "Removes selected roles from the user", notes = "User does not have to own these roles")
    public String removeRoles(
        @ApiParam(required = true, example = "xlogin00") @FormParam("username") final String username,
        @ApiParam(required = true, example = "admin\nuser") @FormParam("roles") final List<String> roles
        ) {
        return AdminService.removeRoles(username, roles);
    }

	/////////////////////

	@PersistenceContext(unitName = "primary")
	EntityManager em;

	@Path("/course/create")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void createCourse(StudyCourse course) {
		em.persist(course);
	}
	
	@Path("/lector/create")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void createLector(Lector lector) {
		em.persist(lector);
	}
	
	@Path("/student/create")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void createStudent(Student student) {
		em.persist(student);
	}

    @Path("/room/create")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void createRoom(Room room) {
		em.persist(room);
	}

	@Path("/course/add_guarant")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void addGuarant(@QueryParam("course_uid") long course_uid, @QueryParam("guarant_uid") long guarant_uid) {
		StudyCourse course = em.find(StudyCourse.class, course_uid);
		Lector lector = em.find(Lector.class, guarant_uid);
		course.setGuarant(lector);
		em.persist(course);
	}
	
}
