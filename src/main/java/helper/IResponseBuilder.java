package helper;

import javax.ws.rs.core.Response;

public interface IResponseBuilder {
    
    /**
     * 
     * @param message
     * @return Success if message set to null, otherwise error
     */
    public Response createResponse(String message);
}
