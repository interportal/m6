package md.curs;

import md.curs.rest.UserService;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

/**
 * Created by MG
 */
@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(UserService.class);
    }
}
