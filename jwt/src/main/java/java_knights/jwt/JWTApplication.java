package java_knights.jwt;

import javax.annotation.security.DeclareRoles;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.eclipse.microprofile.auth.LoginConfig;

@ApplicationPath("/")
@LoginConfig(authMethod = "MP-JWT", realmName = "jwt-app")
@DeclareRoles("authenticated")
public class JWTApplication extends Application {}

