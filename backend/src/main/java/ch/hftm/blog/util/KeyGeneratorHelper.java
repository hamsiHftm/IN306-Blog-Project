package ch.hftm.blog.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;
import java.util.Date;

import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import jakarta.enterprise.context.Dependent;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Warning: NOT IN USE
@Dependent
public class KeyGeneratorHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(KeyGeneratorHelper.class);

    @ConfigProperty(name = "smallrye.jwt.sign.key.location")
    String privateKeyLocation;

    @ConfigProperty(name = "mp.jwt.verify.publickey.location")
    String publicKeyLocation;

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String issuer;

    // Example property to define session timeout
    @ConfigProperty(name = "jwt.expiration.days", defaultValue = "30")
    int sessionMinutes;

    /**
     * Generate RSA KeyPair and store private/public keys in specified locations.
     */
    public void generateKeys() {
        try {
            try {
                Path privateKeyPath = Path.of(getClass().getClassLoader().getResource(privateKeyLocation).getPath());
                Path publicKeyPath = Path.of(getClass().getClassLoader().getResource(publicKeyLocation).getPath());

                // If the private key already exists, assume the key pair exists and return
                if (Files.exists(privateKeyPath) && Files.size(privateKeyPath) > 0) {
                    LOGGER.info("Keypair already exists at path: {}", privateKeyPath.toAbsolutePath());
                    return;
                }
            } catch (NullPointerException e) {
                LOGGER.info("Keypair path not found");
            }

            // Generate a new RSA key pair
            LOGGER.info("Generating new RSA Keypair...");
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048);
            KeyPair pair = kpg.generateKeyPair();

            // Save the private and public keys
            saveKey(privateKeyLocation, pair.getPrivate(), "PRIVATE");
            saveKey(publicKeyLocation, pair.getPublic(), "PUBLIC");

            LOGGER.info("Keypair generated successfully.");
        } catch (Exception e) {
            LOGGER.error("Problem while generating/writing keys", e);
        }
    }

    /**
     * Save a key (private or public) to the specified file location in PEM format.
     *
     * @param keyLocation The location to store the key.
     * @param key The key to be stored.
     * @param type The type of the key (PRIVATE or PUBLIC).
     * @throws IOException If there is an issue writing the file.
     */
    private void saveKey(String keyLocation, Key key, String type) throws IOException {
        Path keyPath = Path.of(keyLocation);

        // Create directories if they do not exist
        if (keyPath.getParent() != null && !Files.exists(keyPath.getParent())) {
            Files.createDirectories(keyPath.getParent());
        }

        // Write the key in PEM format
        Base64.Encoder encoder = Base64.getEncoder();
        try (Writer writer = new FileWriter(keyLocation)) {
            writer.write("-----BEGIN RSA " + type + " KEY-----\n");
            writer.write(encoder.encodeToString(key.getEncoded()));
            writer.write("\n-----END RSA " + type + " KEY-----\n");
        }
    }

    /**
     * Generate a JWT session token with a specified expiration time.
     *
     * @param username The username for the token subject.
     * @param role The user's role.
     * @return The generated JWT token.
     */
    public String generateSessionToken(String username, String role) {
        // Get the current time and calculate expiration time
        long currentTimeInSeconds = new Date().getTime() / 1000;
        long expirationTimeInSeconds = currentTimeInSeconds + sessionMinutes * 60;

        // Build the JWT token
        JwtClaimsBuilder builder = Jwt.claims()
                .issuer(issuer)
                .groups(role)
                .upn(username)
                .expiresAt(expirationTimeInSeconds);

        // Sign the token with the private key (automatically picked up from SmallRye JWT)
        return builder.sign();
    }
}