package cedarline.app.controllers;

import cedarline.app.models.Client;
import cedarline.app.models.ClientsStore;
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

public class ClientsController {

    @FXML
    private TextField nameFld;

    @FXML
    private TextField contactPersonFld;

    @FXML
    private TextField phoneFld;

    @FXML
    private TextField emailFld;

    @FXML
    private TextField addressFld;

    @FXML
    private TextField cityFld;

    @FXML
    private ComboBox<String> clientTypeBox;

    @FXML
    private Label messageLbl;

    @FXML
    private TableView<Client> clientsTable;

    @FXML
    private TableColumn<Client, String> idCol;

    @FXML
    private TableColumn<Client, String> nameCol;

    @FXML
    private TableColumn<Client, String> contactPersonCol;

    @FXML
    private TableColumn<Client, String> phoneCol;

    @FXML
    private TableColumn<Client, String> emailCol;

    @FXML
    private TableColumn<Client, String> addressCol;

    @FXML
    private TableColumn<Client, String> cityCol;

    @FXML
    private TableColumn<Client, String> clientTypeCol;

    private final ClientsStore store = ClientsStore.getInstance();

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        contactPersonCol.setCellValueFactory(new PropertyValueFactory<>("contactPerson"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        clientTypeCol.setCellValueFactory(new PropertyValueFactory<>("clientType"));

        clientTypeBox.getItems().addAll("Retail", "Wholesale", "Hotel / Restaurant", "Pharmacy", "Other");
        clientTypeBox.setValue(clientTypeBox.getItems().get(0));
        clientTypeBox.setMaxWidth(Double.MAX_VALUE);

        clientsTable.setItems(store.getClientsList());

        // picking a row copies its values into the form so it can be edited
        clientsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldRow, row) -> {
            if (row != null) {
                nameFld.setText(row.getName());
                contactPersonFld.setText(row.getContactPerson());
                phoneFld.setText(row.getPhone());
                emailFld.setText(row.getEmail());
                addressFld.setText(row.getAddress());
                cityFld.setText(row.getCity());
                clientTypeBox.setValue(row.getClientType());
                Feedback.clear(messageLbl);
            }
        });
    }

    @FXML
    void addClient(ActionEvent event) {
        String problems = validateForm();
        if (!problems.isEmpty()) {
            Feedback.error(messageLbl, problems);
            return;
        }

        store.addClient(nameFld.getText().trim(),
                contactPersonFld.getText().trim(),
                phoneFld.getText().trim(),
                emailFld.getText().trim(),
                addressFld.getText().trim(),
                cityFld.getText().trim(),
                clientTypeBox.getValue());
        clearFields();
        Feedback.ok(messageLbl, "Client added.");
    }

    @FXML
    void updateClient(ActionEvent event) {
        Client selected = clientsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Feedback.error(messageLbl, "Select a client from the table first.");
            return;
        }

        String problems = validateForm();
        if (!problems.isEmpty()) {
            Feedback.error(messageLbl, problems);
            return;
        }

        store.updateClient(selected, nameFld.getText().trim(),
                contactPersonFld.getText().trim(),
                phoneFld.getText().trim(),
                emailFld.getText().trim(),
                addressFld.getText().trim(),
                cityFld.getText().trim(),
                clientTypeBox.getValue());
        Feedback.ok(messageLbl, "Client updated.");
    }

    @FXML
    void deleteClient(ActionEvent event) {
        Client selected = clientsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Feedback.error(messageLbl, "Select a client from the table first.");
            return;
        }

        if (Feedback.confirm("Remove " + selected.getName() + " from the client list?")) {
            store.deleteClient(selected);
            clearFields();
            Feedback.ok(messageLbl, "Client removed.");
        }
    }

    @FXML
    void clearForm(ActionEvent event) {
        clearFields();
        Feedback.clear(messageLbl);
    }

    private void clearFields() {
        clientsTable.getSelectionModel().clearSelection();
        nameFld.clear();
        contactPersonFld.clear();
        phoneFld.clear();
        emailFld.clear();
        addressFld.clear();
        cityFld.clear();
        clientTypeBox.setValue(clientTypeBox.getItems().get(0));
    }

    /** Returns an empty string when everything is fine, otherwise one line per problem. */
    private String validateForm() {
        StringBuilder problems = new StringBuilder();

        if (nameFld.getText().isBlank()) {
            problems.append("Company is required.\n");
        }
        if (contactPersonFld.getText().isBlank()) {
            problems.append("Contact is required.\n");
        }
        if (!Inputs.isPhone(phoneFld.getText().trim())) {
            problems.append("Phone should contain 7 to 16 digits (a leading + is fine).\n");
        }
        if (!Inputs.isEmail(emailFld.getText().trim())) {
            problems.append("Email does not look valid (name@company.com).\n");
        }
        if (addressFld.getText().isBlank()) {
            problems.append("Address is required.\n");
        }
        if (cityFld.getText().isBlank()) {
            problems.append("City is required.\n");
        }
        if (clientTypeBox.getValue() == null) {
            problems.append("Pick a value for type.\n");
        }
        return problems.toString().trim();
    }
}
