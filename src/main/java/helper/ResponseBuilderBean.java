package helper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

public class ResponseBuilderBean implements IResponseBuilder {
    
    private ResponseBuilder rb;

    public Response createResponse(String message) {
        if (message == null)
            rb = Response.ok().entity("Request succesful.\n");
        else
            rb = Response.status(Response.Status.BAD_REQUEST)
                        .entity(message);

        return rb.build();
    }
}
