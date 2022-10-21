package restfulAPI;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
public class JaxRSActivator extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();

        resources.add(RestAdminService.class);
        resources.add(RestGuarantService.class);
        resources.add(RestLectorService.class);
        resources.add(RestPublicService.class);
        resources.add(RestStudentService.class);

        resources.add(com.github.phillipkruger.apiee.ApieeService.class);

        return resources;
    }
}
