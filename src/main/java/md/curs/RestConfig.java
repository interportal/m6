package md.curs;

import md.curs.rest.RestUserService;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

/**
 * Created by MG
 */
@Component
public class RestConfig extends ResourceConfig {

    public RestConfig() {
        register(RestUserService.class);
    }
}
