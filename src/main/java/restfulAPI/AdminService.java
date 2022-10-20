package restfulAPI;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.swagger.annotations.ApiResponse;
import services.IAdminSecurityRealm;

@Path("/admin")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AdminService {
    
    @Inject @Any
    IAdminSecurityRealm AdminService;

    @POST
    @Path("/user")
    @RolesAllowed("admin")
    @ApiResponse(code = 200, message = "Successfully added user")
    public void addUser(@FormParam("username") final String username, @FormParam("password") final String password) {
        AdminService.addUser(username, password);
    }

    @POST
    @Path("/roles")
    @RolesAllowed("admin")
    public void addRoles(@FormParam("username") final String username, @FormParam("roles") final List<String> roles) {
        AdminService.addRoles(username, roles);
    }

    @DELETE
    @Path("/user")
    @RolesAllowed("admin")
    public void removeUser(@FormParam("username") final String username) {
        AdminService.removeUser(username);
    }

    @DELETE
    @Path("/roles")
    @RolesAllowed("admin")
    public void removeRoles(@FormParam("username") final String username, @FormParam("roles") final List<String> roles) {
        AdminService.removeRoles(username, roles);
    }
}