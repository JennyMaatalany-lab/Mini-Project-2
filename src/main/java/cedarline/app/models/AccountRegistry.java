package cedarline.app.models;

import cedarline.app.util.Database;
import cedarline.app.util.PasswordHasher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Staff accounts live in the Accounts table of the MySQL database.
 * The table stores the SHA-256 hash of each password, not the password.
 */
public final class AccountRegistry {

    private AccountRegistry() {
    }

    /**
     * Username is not case sensitive (MySQL default), the password is (its hash must match exactly).
     * Returns the account if the credentials are right, otherwise an empty Optional.
     */
    public static Optional<StaffAccount> authenticate(String username, String password) throws SQLException {
        String sql = "SELECT username, department FROM Accounts WHERE username = ? AND password_hash = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, PasswordHasher.sha256(password));

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Department department = Department.valueOf(rs.getString("department"));
                    return Optional.of(new StaffAccount(rs.getString("username"), department));
                }
            }
        }
        return Optional.empty();
    }
}
