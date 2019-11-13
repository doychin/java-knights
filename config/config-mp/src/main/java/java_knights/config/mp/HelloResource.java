package java_knights.config.mp;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@Singleton
@Path("/hello")
public class HelloResource {

    @Inject
    @ConfigProperty(name = "who", defaultValue = "world")
    private String who;

    @GET
    public String hello() {
        return "Hello " + who;
    }
}
