package restfulAPI;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import services.IAdminSecurityRealm;

@Path("/admin")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Users' Administration")
public class AdminService {
    
    @Inject
    IAdminSecurityRealm AdminService;

    @POST
    @Path("/user")
    @RolesAllowed("admin")
    @ApiOperation(value = "Creates new user in the system, with initial credentials")
    public String addUser(
        @ApiParam(required = true, example = "xlogin00") @FormParam("username") final String username,
        @ApiParam(required = true, example = "cGFzc3dvcmQxMjM=", value = "Expecting Base64 encoded password, e.g. password123 = cGFzc3dvcmQxMjM=") @FormParam("password") final String password
        ) {
        return AdminService.addUser(username, password);
    }

    @POST
    @Path("/roles")
    @RolesAllowed("admin")
    @ApiOperation(value = "Adds roles to existing user")
    public String addRoles(
        @ApiParam(required = true, example = "xlogin00") @FormParam("username") final String username,
        @ApiParam(required = true, example = "admin\nuser") @FormParam("roles") final List<String> roles
        ) {
        return AdminService.addRoles(username, roles);
    }

    @DELETE
    @Path("/user")
    @RolesAllowed("admin")
    @ApiOperation("Removes user from the application")
    public String removeUser(
        @ApiParam(required = true, example = "xlogin00") @FormParam("username") final String username
        ) {
        return AdminService.removeUser(username);
    }

    @DELETE
    @Path("/roles")
    @RolesAllowed("admin")
    @ApiOperation(value = "Removes selected roles from the user", notes = "User does not have to own these roles")
    public String removeRoles(
        @ApiParam(required = true, example = "xlogin00") @FormParam("username") final String username,
        @ApiParam(required = true, example = "admin\nuser") @FormParam("roles") final List<String> roles
        ) {
        return AdminService.removeRoles(username, roles);
    }
}