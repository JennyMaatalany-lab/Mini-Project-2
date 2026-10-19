package cedarline.app.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * In-memory store for employees (no database in Mini Project 2).
 * One shared instance so the data survives closing and reopening the window.
 */
public class EmployeesStore {

    private static final EmployeesStore INSTANCE = new EmployeesStore();

    private final ObservableList<Employee> employees = FXCollections.observableArrayList();
    private int idCounter = 1000;

    private EmployeesStore() {
        addEmployee("Karim", "Nassar", "Hamra St, Jounieh", "karim.nassar@cedarline.example", "+961 3 112 233", "HR", "");
        addEmployee("Layal", "Haddad", "Main Rd, Byblos", "layal.haddad@cedarline.example", "+961 70 445 566", "Finance", "Late payslip in September");
        addEmployee("Georges", "Matar", "Industrial Zone, Zouk", "georges.matar@cedarline.example", "+961 3 778 899", "Warehouse", "");
        addEmployee("Dana", "Abou Rjeily", "Mar Mikhael, Beirut", "dana.abourjeily@cedarline.example", "+961 71 220 330", "Sales", "Office AC not working");
        addEmployee("Elias", "Sfeir", "Sin el Fil, Beirut", "elias.sfeir@cedarline.example", "+961 76 990 001", "Logistics", "");
    }

    public static EmployeesStore getInstance() {
        return INSTANCE;
    }

    public ObservableList<Employee> getEmployeesList() {
        return employees;
    }

    public Employee addEmployee(String firstName, String lastName, String address, String email, String phone, String department, String complaint) {
        Employee employee = new Employee(nextId(), firstName, lastName, address, email, phone, department, complaint);
        employees.add(employee);
        return employee;
    }

    public void deleteEmployee(Employee employee) {
        if (employee != null) {
            employees.remove(employee);
        }
    }

    public void updateEmployee(Employee employee, String firstName, String lastName, String address, String email, String phone, String department, String complaint) {
        if (employee != null) {
            employee.setFirstName(firstName);
            employee.setLastName(lastName);
            employee.setAddress(address);
            employee.setEmail(email);
            employee.setPhone(phone);
            employee.setDepartment(department);
            employee.setComplaint(complaint);
        }
    }

    private String nextId() {
        idCounter++;
        return "EMP-" + idCounter;
    }
}
