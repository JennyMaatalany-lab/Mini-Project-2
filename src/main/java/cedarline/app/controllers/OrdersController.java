package cedarline.app.controllers;

import cedarline.app.models.Order;
import cedarline.app.models.OrdersStore;
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

public class OrdersController {

    @FXML
    private TextField clientNameFld;

    @FXML
    private TextField productNameFld;

    @FXML
    private TextField quantityFld;

    @FXML
    private TextField priceFld;

    @FXML
    private TextField destinationFld;

    @FXML
    private TextField orderDateFld;

    @FXML
    private ComboBox<String> statusBox;

    @FXML
    private Label messageLbl;

    @FXML
    private TableView<Order> ordersTable;

    @FXML
    private TableColumn<Order, String> idCol;

    @FXML
    private TableColumn<Order, String> clientNameCol;

    @FXML
    private TableColumn<Order, String> productNameCol;

    @FXML
    private TableColumn<Order, Number> quantityCol;

    @FXML
    private TableColumn<Order, Number> priceCol;

    @FXML
    private TableColumn<Order, String> destinationCol;

    @FXML
    private TableColumn<Order, String> orderDateCol;

    @FXML
    private TableColumn<Order, String> statusCol;

    private final OrdersStore store = OrdersStore.getInstance();

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        clientNameCol.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        destinationCol.setCellValueFactory(new PropertyValueFactory<>("destination"));
        orderDateCol.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        statusBox.getItems().addAll("Pending", "Shipped", "Delivered", "Cancelled");
        statusBox.setValue(statusBox.getItems().get(0));
        statusBox.setMaxWidth(Double.MAX_VALUE);

        ordersTable.setItems(store.getOrdersList());

        // picking a row copies its values into the form so it can be edited
        ordersTable.getSelectionModel().selectedItemProperty().addListener((obs, oldRow, row) -> {
            if (row != null) {
                clientNameFld.setText(row.getClientName());
                productNameFld.setText(row.getProductName());
                quantityFld.setText(String.valueOf(row.getQuantity()));
                priceFld.setText(String.format(Locale.US, "%.2f", row.getPrice()));
                destinationFld.setText(row.getDestination());
                orderDateFld.setText(row.getOrderDate());
                statusBox.setValue(row.getStatus());
                Feedback.clear(messageLbl);
            }
        });
    }

    @FXML
    void addOrder(ActionEvent event) {
        String problems = validateForm();
        if (!problems.isEmpty()) {
            Feedback.error(messageLbl, problems);
            return;
        }

        store.addOrder(clientNameFld.getText().trim(),
                productNameFld.getText().trim(),
                Integer.parseInt(quantityFld.getText().trim()),
                Double.parseDouble(priceFld.getText().trim()),
                destinationFld.getText().trim(),
                orderDateFld.getText().trim(),
                statusBox.getValue());
        clearFields();
        Feedback.ok(messageLbl, "Order added.");
    }

    @FXML
    void updateOrder(ActionEvent event) {
        Order selected = ordersTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Feedback.error(messageLbl, "Select an order from the table first.");
            return;
        }

        String problems = validateForm();
        if (!problems.isEmpty()) {
            Feedback.error(messageLbl, problems);
            return;
        }

        store.updateOrder(selected, clientNameFld.getText().trim(),
                productNameFld.getText().trim(),
                Integer.parseInt(quantityFld.getText().trim()),
                Double.parseDouble(priceFld.getText().trim()),
                destinationFld.getText().trim(),
                orderDateFld.getText().trim(),
                statusBox.getValue());
        Feedback.ok(messageLbl, "Order updated.");
    }

    @FXML
    void deleteOrder(ActionEvent event) {
        Order selected = ordersTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Feedback.error(messageLbl, "Select an order from the table first.");
            return;
        }

        if (Feedback.confirm("Delete order " + selected.getOrderId() + "?")) {
            store.deleteOrder(selected);
            clearFields();
            Feedback.ok(messageLbl, "Order removed.");
        }
    }

    @FXML
    void clearForm(ActionEvent event) {
        clearFields();
        Feedback.clear(messageLbl);
    }

    private void clearFields() {
        ordersTable.getSelectionModel().clearSelection();
        clientNameFld.clear();
        productNameFld.clear();
        quantityFld.clear();
        priceFld.clear();
        destinationFld.clear();
        orderDateFld.clear();
        statusBox.setValue(statusBox.getItems().get(0));
    }

    /** Returns an empty string when everything is fine, otherwise one line per problem. */
    private String validateForm() {
        StringBuilder problems = new StringBuilder();

        if (clientNameFld.getText().isBlank()) {
            problems.append("Client is required.\n");
        }
        if (productNameFld.getText().isBlank()) {
            problems.append("Product is required.\n");
        }
        String quantityText = quantityFld.getText().trim();
        if (!Inputs.isWholeNumber(quantityText) || Integer.parseInt(quantityText) < 1) {
            problems.append("Quantity must be a whole number of at least 1.\n");
        }
        if (!Inputs.isMoney(priceFld.getText().trim())) {
            problems.append("Price ($) must be an amount like 385 or 385.50.\n");
        }
        if (destinationFld.getText().isBlank()) {
            problems.append("Destination is required.\n");
        }
        if (!Inputs.isIsoDate(orderDateFld.getText().trim())) {
            problems.append("Order date must be written as yyyy-mm-dd.\n");
        }
        if (statusBox.getValue() == null) {
            problems.append("Pick a value for status.\n");
        }
        return problems.toString().trim();
    }
}
