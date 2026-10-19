package cedarline.app.models;

import javafx.beans.property.*;

public class Employee {
    private final SimpleStringProperty employeeId;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty address;
    private final SimpleStringProperty email;
    private final SimpleStringProperty phone;
    private final SimpleStringProperty department;
    private final SimpleStringProperty complaint;

    public Employee(String employeeId, String firstName, String lastName, String address, String email, String phone, String department, String complaint) {
        this.employeeId = new SimpleStringProperty(employeeId);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.address = new SimpleStringProperty(address);
        this.email = new SimpleStringProperty(email);
        this.phone = new SimpleStringProperty(phone);
        this.department = new SimpleStringProperty(department);
        this.complaint = new SimpleStringProperty(complaint);
    }

    public String getEmployeeId() {
        return employeeId.get();
    }

    public StringProperty employeeIdProperty() {
        return employeeId;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public StringProperty addressProperty() {
        return address;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public String getDepartment() {
        return department.get();
    }

    public void setDepartment(String department) {
        this.department.set(department);
    }

    public StringProperty departmentProperty() {
        return department;
    }

    public String getComplaint() {
        return complaint.get();
    }

    public void setComplaint(String complaint) {
        this.complaint.set(complaint);
    }

    public StringProperty complaintProperty() {
        return complaint;
    }
}
