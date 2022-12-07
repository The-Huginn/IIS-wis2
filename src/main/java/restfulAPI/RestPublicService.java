package restfulAPI;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import dtos.Common.UserInfoDTO;
import entity.StudyCourse;
import helper.IResponseBuilder;
import services.interfaces.IPublicService;
import services.interfaces.IPublicService.UserInfo;

@Path("/public")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@PermitAll
public class RestPublicService {
	
	@Context
	SecurityContext ctx;

	@Inject
	IPublicService service;

	@Inject
	IResponseBuilder rb;

	@GET
	@Path("/userinfo")
	public UserInfo helloJSON() throws NoSuchAlgorithmException {
		return service.getUserInfo(ctx);
	}

	@GET
	@Path("/userinfo/names")
	public UserInfoDTO getNames() throws NoSuchAlgorithmException {
		return service.getNames(ctx);
	}

	@POST
	@Path("/pwd_update")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response updatePassword(@FormParam("oldPassword") String oldPassword, @FormParam("newPassword") String newPassword) {
		return rb.createResponse(service.updatePassword(ctx, oldPassword, newPassword));
	}

	@Path("/course")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<StudyCourse> getStudyCourses() {
		return service.getStudyCourses();
	}

	@Path("/course/{uid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public StudyCourse getStudyCourse(@PathParam("uid") long uid) {
		return service.getStudyCourse(uid);
	}
}
