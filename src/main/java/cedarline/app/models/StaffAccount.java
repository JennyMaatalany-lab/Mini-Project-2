package cedarline.app.models;

public class StaffAccount {
    private final String username;
    private final String password;
    private final Department department;

    public StaffAccount(String username, String password, Department department) {
        this.username = username;
        this.password = password;
        this.department = department;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Department getDepartment() {
        return department;
    }
}
