package restfulAPI;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.swagger.jaxrs.config.BeanConfig;

@ApplicationPath("/api")
public class JaxRSActivator extends Application {

    public JaxRSActivator() {

        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.0");
        beanConfig.setTitle("Wis2 Swagger");
        beanConfig.setHost("localhost:8080");
        beanConfig.setSchemes(new String[] {"http"});
        beanConfig.setBasePath("/wis2/docs");
        beanConfig.setResourcePackage("vutbr.cz");
        beanConfig.setScan(true);
        beanConfig.setPrettyPrint(true);
    }
}
