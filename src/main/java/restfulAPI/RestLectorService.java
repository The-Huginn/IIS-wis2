package restfulAPI;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import entity.DateEvaluation;
import entity.Lector;

@Path("/lector")
@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestLectorService {

	@PersistenceContext(unitName = "primary")
	EntityManager em;
	
    @Path("/dateEval/add_evaluation")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public void addGuarant(@QueryParam("dateEval_uid") long dateEval_uid,
							@QueryParam("lector_uid") long lector_uid, @QueryParam("evaluation") double evaluation)
	{
		DateEvaluation dateEval = em.find(DateEvaluation.class, dateEval_uid);
        Lector lector = em.find(Lector.class, lector_uid);
		dateEval.setLector(lector);
		dateEval.setEvaluation(evaluation);
		em.persist(dateEval);
	}
}
