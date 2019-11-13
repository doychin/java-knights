package java_knights.jwt;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("/login")
public class LoginResource {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final String AUTH_TOKEN_PREFIX = "Bearer ";

    @PermitAll
    @GET
    @Path("{user}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(@PathParam("user") String user) {
        return Response.ok(user).entity(JwtUtils.generateJWT(user)).build();
    }
}
