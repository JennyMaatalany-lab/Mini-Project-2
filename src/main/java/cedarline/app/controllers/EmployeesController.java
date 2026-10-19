package cedarline.app.controllers;

import cedarline.app.models.Employee;
import cedarline.app.models.EmployeesStore;
import cedarline.app.util.Feedback;
import cedarline.app.util.Inputs;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.Locale;

public class EmployeesController {

    @FXML
    private TextField firstNameFld;

    @FXML
    private TextField lastNameFld;

    @FXML
    private TextField addressFld;

    @FXML
    private TextField emailFld;

    @FXML
    private TextField phoneFld;

    @FXML
    private ComboBox<String> departmentBox;

    @FXML
    private TextField complaintFld;

    @FXML
    private Label messageLbl;

    @FXML
    private TableView<Employee> employeesTable;

    @FXML
    private TableColumn<Employee, String> idCol;

    @FXML
    private TableColumn<Employee, String> firstNameCol;

    @FXML
    private TableColumn<Employee, String> lastNameCol;

    @FXML
    private TableColumn<Employee, String> addressCol;

    @FXML
    private TableColumn<Employee, String> emailCol;

    @FXML
    private TableColumn<Employee, String> phoneCol;

    @FXML
    private TableColumn<Employee, String> departmentCol;

    @FXML
    private TableColumn<Employee, String> complaintCol;

    private final EmployeesStore store = EmployeesStore.getInstance();

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        departmentCol.setCellValueFactory(new PropertyValueFactory<>("department"));
        complaintCol.setCellValueFactory(new PropertyValueFactory<>("complaint"));

        departmentBox.getItems().addAll("HR", "Sales", "Logistics", "Finance", "IT", "Warehouse");
        departmentBox.setValue(departmentBox.getItems().get(0));
        departmentBox.setMaxWidth(Double.MAX_VALUE);

        employeesTable.setItems(store.getEmployeesList());

        // picking a row copies its values into the form so it can be edited
        employeesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldRow, row) -> {
            if (row != null) {
                firstNameFld.setText(row.getFirstName());
                lastNameFld.setText(row.getLastName());
                addressFld.setText(row.getAddress());
                emailFld.setText(row.getEmail());
                phoneFld.setText(row.getPhone());
                departmentBox.setValue(row.getDepartment());
                complaintFld.setText(row.getComplaint());
                Feedback.clear(messageLbl);
            }
        });
    }

    @FXML
    void addEmployee(ActionEvent event) {
        String problems = validateForm();
        if (!problems.isEmpty()) {
            Feedback.error(messageLbl, problems);
            return;
        }

        store.addEmployee(firstNameFld.getText().trim(),
                lastNameFld.getText().trim(),
                addressFld.getText().trim(),
                emailFld.getText().trim(),
                phoneFld.getText().trim(),
                departmentBox.getValue(),
                complaintFld.getText().trim());
        clearFields();
        Feedback.ok(messageLbl, "Employee added.");
    }

    @FXML
    void updateEmployee(ActionEvent event) {
        Employee selected = employeesTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Feedback.error(messageLbl, "Select an employee from the table first.");
            return;
        }

        String problems = validateForm();
        if (!problems.isEmpty()) {
            Feedback.error(messageLbl, problems);
            return;
        }

        store.updateEmployee(selected, firstNameFld.getText().trim(),
                lastNameFld.getText().trim(),
                addressFld.getText().trim(),
                emailFld.getText().trim(),
                phoneFld.getText().trim(),
                departmentBox.getValue(),
                complaintFld.getText().trim());
        Feedback.ok(messageLbl, "Employee updated.");
    }

    @FXML
    void deleteEmployee(ActionEvent event) {
        Employee selected = employeesTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Feedback.error(messageLbl, "Select an employee from the table first.");
            return;
        }

        if (Feedback.confirm("Remove " + selected.getFirstName() + " " + selected.getLastName() + " from the employee records?")) {
            store.deleteEmployee(selected);
            clearFields();
            Feedback.ok(messageLbl, "Employee removed.");
        }
    }

    @FXML
    void clearForm(ActionEvent event) {
        clearFields();
        Feedback.clear(messageLbl);
    }

    private void clearFields() {
        employeesTable.getSelectionModel().clearSelection();
        firstNameFld.clear();
        lastNameFld.clear();
        addressFld.clear();
        emailFld.clear();
        phoneFld.clear();
        departmentBox.setValue(departmentBox.getItems().get(0));
        complaintFld.clear();
    }

    /** Returns an empty string when everything is fine, otherwise one line per problem. */
    private String validateForm() {
        StringBuilder problems = new StringBuilder();

        if (firstNameFld.getText().isBlank()) {
            problems.append("First name is required.\n");
        }
        if (lastNameFld.getText().isBlank()) {
            problems.append("Last name is required.\n");
        }
        if (addressFld.getText().isBlank()) {
            problems.append("Address is required.\n");
        }
        if (!Inputs.isEmail(emailFld.getText().trim())) {
            problems.append("Email does not look valid (name@company.com).\n");
        }
        if (!Inputs.isPhone(phoneFld.getText().trim())) {
            problems.append("Phone should contain 7 to 16 digits (a leading + is fine).\n");
        }
        if (departmentBox.getValue() == null) {
            problems.append("Pick a value for department.\n");
        }
        return problems.toString().trim();
    }
}
