package cedarline.app.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * One place that knows how to reach the MySQL database (JDBC steps 2 and 3 from Lecture 5).
 * Every Store class calls Database.getConnection() instead of repeating the URL and password.
 *
 * Each team member has his own MySQL installation, so the user and password can be changed
 * without editing this file by setting the environment variables CEDARLINE_DB_USER and
 * CEDARLINE_DB_PASSWORD (the defaults below are the ones used in the lecture).
 */
public final class Database {

    private static final String DB_URL = valueOf("CEDARLINE_DB_URL", "jdbc:mysql://localhost:3306/cedarline_db");
    private static final String DB_USER = valueOf("CEDARLINE_DB_USER", "root");
    private static final String DB_PASSWORD = valueOf("CEDARLINE_DB_PASSWORD", "123456");

    private Database() {
    }

    /** Opens a new connection. Callers must close it (use try-with-resources). */
    public static Connection getConnection() throws SQLException {
        try {
            // Step 2: register the MySQL driver (the jar comes from the pom.xml dependency)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            throw new SQLException("MySQL driver not found. Reload the Maven project.", ex);
        }
        // Step 3: open the connection
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    private static String valueOf(String variable, String fallback) {
        String value = System.getenv(variable);
        return (value == null || value.isBlank()) ? fallback : value;
    }
}
