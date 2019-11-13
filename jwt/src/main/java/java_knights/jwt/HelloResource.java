package java_knights.jwt;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.json.Json;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.metrics.annotation.Counted;

@RequestScoped
@Path("/hello")
@Produces(MediaType.TEXT_PLAIN)
public class HelloResource {

    @Inject
    private JsonWebToken jsonWebToken;

    @GET
    @Counted(name="hello counter jwt", absolute = true, monotonic = true)
    @RolesAllowed({"authenticated"})
    public String jwtHello() {
        return "Hello " + jsonWebToken.getName();
    }

    @GET
    @Path("/nojwt")
    @Counted(name="hello counter no jwt", absolute = true, monotonic = true)
    @PermitAll
    public String noAuthentication() {
        return "Hello unauthenticated";
    }
}
