package java_knights.metrics;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.metrics.annotation.Counted;

@Singleton
@Path("/hello")
public class HelloResource {

    @Inject
    @ConfigProperty(name = "who", defaultValue = "world")
    private String who;

    @GET
    @Counted(name="hello counter", absolute = true, monotonic = true)
    public String hello() {
        return "Hello " + who;
    }
}
