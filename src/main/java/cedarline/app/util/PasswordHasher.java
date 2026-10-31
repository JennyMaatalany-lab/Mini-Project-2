package cedarline.app.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Turns a password into its SHA-256 hash (64 lowercase hex characters).
 * The Accounts table stores only this hash, never the password itself.
 * It gives exactly the same text as MySQL's SHA2(password, 256), so the SQL script
 * can create the accounts and Java can check them.
 */
public final class PasswordHasher {

    private PasswordHasher() {
    }

    public static String sha256(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException ex) {
            // every Java installation has SHA-256, so this cannot really happen
            throw new IllegalStateException("SHA-256 is not available", ex);
        }
    }
}
