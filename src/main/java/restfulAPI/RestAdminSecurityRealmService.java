package restfulAPI;

import java.util.List;

import javax.annotation.security.RolesAllowed;
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

import helper.IResponseBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import services.IAdminSecurityRealm;

@Path("/security")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Security Realm Administration")
@RolesAllowed("security")
public class RestAdminSecurityRealmService {
    
    @Inject
    IAdminSecurityRealm adminSecurityService;

    @Inject
    IResponseBuilder rb;

    @POST
    @Path("/user")
    @ApiOperation(value = "Creates new user in the system, with initial credentials and no roles in the security realm")
    public Response addUser(
        @ApiParam(required = true, example = "xlogin00") @FormParam("username") final String username,
        @ApiParam(required = true, example = "cGFzc3dvcmQxMjM=", value = "Expecting Base64 encoded password, e.g. password123 = cGFzc3dvcmQxMjM=") @FormParam("password") final String password
        ) {
        return rb.createResponse(adminSecurityService.addUser(username, password));
    }

    @POST
    @Path("/roles")
    @ApiOperation(value = "Adds roles to existing user")
    public Response addRoles(
        @ApiParam(required = true, example = "xlogin00") @FormParam("username") final String username,
        @ApiParam(required = true, example = "admin\nuser") @FormParam("roles") final List<String> roles
        ) {
        return rb.createResponse(adminSecurityService.addRoles(username, roles));
    }

    @DELETE
    @Path("/user")
    @ApiOperation("Removes user from the application security realm")
    public Response removeUser(
        @ApiParam(required = true, example = "xlogin00") @FormParam("username") final String username
        ) {
        return rb.createResponse(adminSecurityService.removeUser(username));
    }

    @DELETE
    @Path("/roles")
    @ApiOperation(value = "Removes selected roles from the user", notes = "User does not have to own these roles")
    public Response removeRoles(
        @ApiParam(required = true, example = "xlogin00") @FormParam("username") final String username,
        @ApiParam(required = true, example = "admin\nuser") @FormParam("roles") final List<String> roles
        ) {
        return rb.createResponse(adminSecurityService.removeRoles(username, roles));
    }

    @GET
    @Path("/user")
    @ApiOperation(value = "Returns all users registered in the security realm")
    public List<String> getUsers() {
        return adminSecurityService.getUsers();
    }

    @GET
    @Path("/roles/{username}")
    @ApiOperation(value = "Returns roles for spefific user with username")
    public List<String> getRoles(
        @ApiParam(required = true, example = "xlogin00") @PathParam("username") final String username
    ) {
        return adminSecurityService.getRoles(username);
    }
}
