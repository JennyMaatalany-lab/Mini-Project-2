package cedarline.app.controllers;

import cedarline.app.models.Product;
import cedarline.app.models.ProductsStore;
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

public class ProductsController {

    @FXML
    private TextField nameFld;

    @FXML
    private TextField categoryFld;

    @FXML
    private TextField supplierFld;

    @FXML
    private TextField quantityFld;

    @FXML
    private TextField unitPriceFld;

    @FXML
    private TextField reorderLevelFld;

    @FXML
    private TextField locationFld;

    @FXML
    private Label messageLbl;

    @FXML
    private TableView<Product> productsTable;

    @FXML
    private TableColumn<Product, String> idCol;

    @FXML
    private TableColumn<Product, String> nameCol;

    @FXML
    private TableColumn<Product, String> categoryCol;

    @FXML
    private TableColumn<Product, String> supplierCol;

    @FXML
    private TableColumn<Product, Number> quantityCol;

    @FXML
    private TableColumn<Product, Number> unitPriceCol;

    @FXML
    private TableColumn<Product, Number> reorderLevelCol;

    @FXML
    private TableColumn<Product, String> locationCol;

    private final ProductsStore store = ProductsStore.getInstance();

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("sku"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        supplierCol.setCellValueFactory(new PropertyValueFactory<>("supplier"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        unitPriceCol.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        reorderLevelCol.setCellValueFactory(new PropertyValueFactory<>("reorderLevel"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));


        productsTable.setItems(store.getProductsList());

        // picking a row copies its values into the form so it can be edited
        productsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldRow, row) -> {
            if (row != null) {
                nameFld.setText(row.getName());
                categoryFld.setText(row.getCategory());
                supplierFld.setText(row.getSupplier());
                quantityFld.setText(String.valueOf(row.getQuantity()));
                unitPriceFld.setText(String.format(Locale.US, "%.2f", row.getUnitPrice()));
                reorderLevelFld.setText(String.valueOf(row.getReorderLevel()));
                locationFld.setText(row.getLocation());
                Feedback.clear(messageLbl);
            }
        });
    }

    @FXML
    void addProduct(ActionEvent event) {
        String problems = validateForm();
        if (!problems.isEmpty()) {
            Feedback.error(messageLbl, problems);
            return;
        }

        store.addProduct(nameFld.getText().trim(),
                categoryFld.getText().trim(),
                supplierFld.getText().trim(),
                Integer.parseInt(quantityFld.getText().trim()),
                Double.parseDouble(unitPriceFld.getText().trim()),
                Integer.parseInt(reorderLevelFld.getText().trim()),
                locationFld.getText().trim());
        clearFields();
        Feedback.ok(messageLbl, "Product added.");
    }

    @FXML
    void updateProduct(ActionEvent event) {
        Product selected = productsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Feedback.error(messageLbl, "Select a product from the table first.");
            return;
        }

        String problems = validateForm();
        if (!problems.isEmpty()) {
            Feedback.error(messageLbl, problems);
            return;
        }

        store.updateProduct(selected, nameFld.getText().trim(),
                categoryFld.getText().trim(),
                supplierFld.getText().trim(),
                Integer.parseInt(quantityFld.getText().trim()),
                Double.parseDouble(unitPriceFld.getText().trim()),
                Integer.parseInt(reorderLevelFld.getText().trim()),
                locationFld.getText().trim());
        Feedback.ok(messageLbl, "Product updated.");
    }

    @FXML
    void deleteProduct(ActionEvent event) {
        Product selected = productsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Feedback.error(messageLbl, "Select a product from the table first.");
            return;
        }

        if (Feedback.confirm("Remove " + selected.getName() + " from the inventory?")) {
            store.deleteProduct(selected);
            clearFields();
            Feedback.ok(messageLbl, "Product removed.");
        }
    }

    @FXML
    void clearForm(ActionEvent event) {
        clearFields();
        Feedback.clear(messageLbl);
    }

    private void clearFields() {
        productsTable.getSelectionModel().clearSelection();
        nameFld.clear();
        categoryFld.clear();
        supplierFld.clear();
        quantityFld.clear();
        unitPriceFld.clear();
        reorderLevelFld.clear();
        locationFld.clear();
    }

    /** Returns an empty string when everything is fine, otherwise one line per problem. */
    private String validateForm() {
        StringBuilder problems = new StringBuilder();

        if (nameFld.getText().isBlank()) {
            problems.append("Product is required.\n");
        }
        if (categoryFld.getText().isBlank()) {
            problems.append("Category is required.\n");
        }
        if (supplierFld.getText().isBlank()) {
            problems.append("Supplier is required.\n");
        }
        if (!Inputs.isWholeNumber(quantityFld.getText().trim())) {
            problems.append("In stock must be a whole number (0 or more).\n");
        }
        if (!Inputs.isMoney(unitPriceFld.getText().trim())) {
            problems.append("Unit price ($) must be an amount like 385 or 385.50.\n");
        }
        if (!Inputs.isWholeNumber(reorderLevelFld.getText().trim())) {
            problems.append("Reorder at must be a whole number (0 or more).\n");
        }
        if (locationFld.getText().isBlank()) {
            problems.append("Location is required.\n");
        }
        return problems.toString().trim();
    }
}
