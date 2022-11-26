package helper;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;

public class ResponseBuilderBean implements IResponseBuilder {


    public Response createResponse(String message) {

        Map<String, String> jsonValues = new HashMap<>();

        if (message == null) {
            jsonValues.put("reply", "Request succesful.");
            return Response.status(200).entity(jsonValues).build();
        } else {
            jsonValues.put("reply", message);
            return Response.status(Response.Status.BAD_REQUEST).entity(jsonValues).build();
        }
    }
}
