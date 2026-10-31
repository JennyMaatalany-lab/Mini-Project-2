package cedarline.app.models;

/** A signed-in user. The password (or its hash) is never kept in memory after the check. */
public class StaffAccount {
    private final String username;
    private final Department department;

    public StaffAccount(String username, Department department) {
        this.username = username;
        this.department = department;
    }

    public String getUsername() {
        return username;
    }

    public Department getDepartment() {
        return department;
    }
}
