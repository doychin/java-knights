package java_knights.jwt;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.eclipse.microprofile.jwt.Claims;

class JwtUtils {

    private static final PrivateKey PRIVATE_KEY = readPrivateKey();

    private static final int AUTH_EXPIRY_SECONDS = 60 * 60;

    private static final String TEST_USER_EXAMPLE_COM = "@example.com";

    static String generateJWT(String user) {
        JSONObject claims = new JSONObject();

        claims.put(Claims.iss.name(), "http://localhost:8080");
        claims.put(Claims.aud.name(), "jwt-app");
        claims.put(Claims.upn.name(), user);
        claims.put(Claims.sub.name(), user + TEST_USER_EXAMPLE_COM);
        JSONArray groups = new JSONArray();
        groups.addAll(Collections.singletonList("authenticated"));
        claims.put(Claims.groups.name(), groups);

        long currentTimeInSeconds = System.currentTimeMillis() / 1000;
        claims.put(Claims.iat.name(), currentTimeInSeconds);
        claims.put(Claims.exp.name(), currentTimeInSeconds + AUTH_EXPIRY_SECONDS);
        claims.put(Claims.auth_time.name(), currentTimeInSeconds);
        claims.put(Claims.jti.name(), user + TEST_USER_EXAMPLE_COM + "-" + currentTimeInSeconds);

        claims.put(Claims.given_name.name(), "firstName");
        claims.put(Claims.family_name.name(), "lastName");

        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256)
                .keyID("mp-hol")
                .type(JOSEObjectType.JWT)
                .build();

        try {
            JWTClaimsSet claimsSet = JWTClaimsSet.parse(claims);
            SignedJWT jwt = new SignedJWT(header, claimsSet);
            jwt.sign(new RSASSASigner(PRIVATE_KEY));
            return jwt.serialize();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static PrivateKey readPrivateKey() {
        InputStream contentIS = JwtUtils.class.getResourceAsStream("/private_key.pem");
        byte[] tmp = new byte[4096];
        try {
            int length = contentIS.read(tmp);
            return decodePrivateKey(new String(tmp, 0, length));
        } catch (Exception ex) {
            throw new RuntimeException("Could not read private key", ex);
        }
    }

    private static PrivateKey decodePrivateKey(String pemEncoded) throws Exception {
        pemEncoded = removeBeginEnd(pemEncoded);
        byte[] pkcs8EncodedBytes = Base64.getDecoder().decode(pemEncoded);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pkcs8EncodedBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(keySpec);
    }

    private static String removeBeginEnd(String pem) {
        pem = pem.replaceAll("-----BEGIN (.*)-----", "");
        pem = pem.replaceAll("-----END (.*)----", "");
        pem = pem.replaceAll("\r\n", "");
        pem = pem.replaceAll("\n", "");
        return pem.trim();
    }

}