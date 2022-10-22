package restfulAPI;

import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import entity.DateEvaluation;
import entity.Lector;
import helper.IResponseBuilder;
import io.swagger.annotations.ApiParam;

@Path("/lector")
@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("lector")
public class RestLectorService {

	@PersistenceContext(unitName = "primary")
	EntityManager em;

	@Resource
	EJBContext ejb;

	@Inject
	private IResponseBuilder rb;
	
    @Path("/dateEval/{dateEval_uid}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response addEvaluation(
		@ApiParam(required = true, example = "10") @PathParam("dateEval_uid") long dateEval_uid,
		@ApiParam(required = true, example = "69", value = "Value between 0-100")
			@FormParam("evaluation")
			@DecimalMin(value = "0.0", message = "Must be greater than 0.0 [RestLectorService.addEvaluation]")
			@DecimalMax(value = "100.0", message = "Must be smaller than 100.0 [RestLectorService.addEvaluation]") double evaluation
	) {
		TypedQuery<Lector> query = em.createNamedQuery("findUid", Lector.class);
		query.setParameter("table", "Lector").setParameter("username", ejb.getCallerPrincipal().getName());
		List<Lector> reply = query.getResultList();
		if (reply.isEmpty())
			return rb.createResponse("User is not found as lector in database");
		
		Lector lector = reply.get(0);
		try {
			DateEvaluation dateEval = em.find(DateEvaluation.class, dateEval_uid);
			dateEval.setLector(lector);
			dateEval.setEvaluation(evaluation);
			em.persist(dateEval);
		} catch (Exception e) {
			e.printStackTrace();
			rb.createResponse(e.getMessage());
		}

		return rb.createResponse(null);
	}
}
