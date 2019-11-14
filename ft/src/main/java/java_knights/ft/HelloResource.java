package java_knights.ft;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.Random;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.jwt.*;
import org.eclipse.microprofile.metrics.annotation.Counted;

@Singleton
@Path("/hello")
public class HelloResource {

    private Random r = new Random(System.currentTimeMillis());

    @GET
    public String hello() {
        return "Fast hello world";
    }

    @GET
    @Path("/slow")
    @Timeout(500)
    @Fallback(fallbackMethod = "fallback")
    @Counted(name = "slow", monotonic = true)
    public String slowHello() {
        try { Thread.sleep(r.nextInt(1000)); } catch (InterruptedException ignored) { }
        return "Slow hello world";
    }

    @Counted(name = "fallback", monotonic = true)
    public String fallback() {
        return "fallback hello world";
    }
}
