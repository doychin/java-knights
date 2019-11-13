package java_knights.openapi;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.opentracing.Traced;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Singleton
@Path("/hello")
public class HelloResource {

    @Inject
    @ConfigProperty(name = "who", defaultValue = "world")
    private String who;

    @GET
    @Counted(name = "hello counter", absolute = true, monotonic = true)
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(
        description = "returns \"Hello {who}\" where {who} is provided by configuration or \"world\" if who" +
            " is not provided")
    public String hello() {
        return "Hello " + who;
    }
}

