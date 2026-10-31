package cedarline.app.models;

import cedarline.app.util.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Employee store backed by the Employees table of the MySQL database (JDBC).
 * One shared instance, so the HR window and its table always use the same list.
 *
 * The ObservableList is a copy of the table kept in memory so the TableView can show it.
 * Every change is first written to the database; the list is only changed if that worked.
 */
public class EmployeesStore {

    /** The database stores a number (1001, 1002...), the screen shows it as EMP-1001. */
    private static final String ID_PREFIX = "EMP-";

    private static final EmployeesStore INSTANCE = new EmployeesStore();

    private final ObservableList<Employee> employees = FXCollections.observableArrayList();

    private EmployeesStore() {
    }

    public static EmployeesStore getInstance() {
        return INSTANCE;
    }

    public ObservableList<Employee> getEmployeesList() {
        return employees;
    }

    /** SELECT: reloads every employee from the database into the list. */
    public void loadEmployees() throws SQLException {
        String sql = "SELECT employee_id, first_name, last_name, address, email, phone, department, complaint "
                + "FROM Employees ORDER BY employee_id";

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            employees.clear();
            while (rs.next()) {
                employees.add(new Employee(
                        ID_PREFIX + rs.getInt("employee_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("address"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("department"),
                        rs.getString("complaint")));
            }
        }
    }

    /** INSERT: the database creates the id (AUTO_INCREMENT) and we read it back. */
    public Employee addEmployee(String firstName, String lastName, String address, String email,
                                String phone, String department, String complaint) throws SQLException {
        String sql = "INSERT INTO Employees (first_name, last_name, address, email, phone, department, complaint) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, address);
            stmt.setString(4, email);
            stmt.setString(5, phone);
            stmt.setString(6, department);
            stmt.setString(7, complaint);
            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                keys.next();
                Employee employee = new Employee(ID_PREFIX + keys.getInt(1),
                        firstName, lastName, address, email, phone, department, complaint);
                employees.add(employee);
                return employee;
            }
        }
    }

    /** DELETE: removes the row from the database, then from the list. */
    public void deleteEmployee(Employee employee) throws SQLException {
        if (employee == null) {
            return;
        }
        String sql = "DELETE FROM Employees WHERE employee_id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, numericId(employee));
            stmt.executeUpdate();
        }
        employees.remove(employee);
    }

    /** UPDATE: changes the row in the database, then the existing object (the table refreshes itself). */
    public void updateEmployee(Employee employee, String firstName, String lastName, String address,
                               String email, String phone, String department, String complaint) throws SQLException {
        if (employee == null) {
            return;
        }
        String sql = "UPDATE Employees SET first_name = ?, last_name = ?, address = ?, email = ?, "
                + "phone = ?, department = ?, complaint = ? WHERE employee_id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, address);
            stmt.setString(4, email);
            stmt.setString(5, phone);
            stmt.setString(6, department);
            stmt.setString(7, complaint);
            stmt.setInt(8, numericId(employee));
            stmt.executeUpdate();
        }

        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setAddress(address);
        employee.setEmail(email);
        employee.setPhone(phone);
        employee.setDepartment(department);
        employee.setComplaint(complaint);
    }

    /** "EMP-1003" -> 1003 */
    private int numericId(Employee employee) {
        return Integer.parseInt(employee.getEmployeeId().substring(ID_PREFIX.length()));
    }
}
