package ch.hftm.blog.service;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.security.PrivateKey;
import java.util.Date;
import java.util.Set;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@ApplicationScoped
public class AuthService {

    @ConfigProperty(name = "smallrye.jwt.sign.key.location")
    String privateKeyLocation;

    @ConfigProperty(name = "mp.jwt.verify.publickey.location")
    String publicKeyLocation;

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String issuer;

    // Example property to define session timeout
    @ConfigProperty(name = "jwt.expiration.days", defaultValue = "30")
    int sessionMinutes;

    private static final String PRIVATE_KEY_PATH = "META-INF/privateKey.pem";

    private PrivateKey getPrivateKey() throws Exception {
        var resource = getClass().getClassLoader().getResource(PRIVATE_KEY_PATH);
        if (resource == null) {
            throw new RuntimeException("Private key file not found");
        }
        Path path = Path.of(resource.toURI());
        String keyString = Files.readString(path);

        String privateKeyContent = keyString
                .replace("-----BEGIN RSA PRIVATE KEY-----", "")
                .replace("-----END RSA PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        byte[] encoded = Base64.getDecoder().decode(privateKeyContent);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(encoded);
        KeyFactory kf = KeyFactory.getInstance("RSA");

        return kf.generatePrivate(spec);
    }

    public String generateJwtToken(String email, Long userId) {
        try {
            PrivateKey privateKey = getPrivateKey();

            long currentTime = new Date().getTime() / 1000;
            long expirationTime = currentTime + sessionMinutes * 24 * 60 * 60;

            return Jwt.claims()
                    .issuer("in306-platform")
                    .subject(userId.toString())
                    .issuedAt(currentTime)
                    .expiresAt(expirationTime)
                    .sign(privateKey);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}