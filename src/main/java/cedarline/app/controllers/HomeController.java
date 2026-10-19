package cedarline.app.controllers;

import cedarline.app.models.Department;
import cedarline.app.models.StaffAccount;
import cedarline.app.util.Feedback;
import cedarline.app.util.Session;
import cedarline.app.util.ViewLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    /** true = each user can only open their own department; false = everyone can open everything. */
    private static final boolean OWN_DEPARTMENT_ONLY = true;

    @FXML
    private Label welcomeLbl;

    @FXML
    private Button hrBtn;

    @FXML
    private Button clientsBtn;

    @FXML
    private Button inventoryBtn;

    @FXML
    private Button ordersBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    public void initialize() {
        StaffAccount who = Session.getCurrent();
        if (who == null) {
            return;
        }
        welcomeLbl.setText("Signed in as " + who.getUsername() + " - " + who.getDepartment().getLabel());

        // find the card that belongs to the signed-in user's department
        Button own = switch (who.getDepartment()) {
            case HR -> hrBtn;
            case CLIENTS -> clientsBtn;
            case INVENTORY -> inventoryBtn;
            case ORDERS -> ordersBtn;
        };

        // grey out the other departments' cards
        if (OWN_DEPARTMENT_ONLY) {
            for (Button card : new Button[]{hrBtn, clientsBtn, inventoryBtn, ordersBtn}) {
                card.setDisable(card != own);
            }
        }
    }

    @FXML
    void openHr(ActionEvent event) {
        openSection(Department.HR, "employees-view.fxml", "HR Department - Employees");
    }

    @FXML
    void openClients(ActionEvent event) {
        openSection(Department.CLIENTS, "clients-view.fxml", "Clients Department - Clients");
    }

    @FXML
    void openInventory(ActionEvent event) {
        openSection(Department.INVENTORY, "products-view.fxml", "Inventory Department - Products");
    }

    @FXML
    void openOrders(ActionEvent event) {
        openSection(Department.ORDERS, "orders-view.fxml", "Orders Department - Orders");
    }

    @FXML
    void logout(ActionEvent event) {
        Session.end();
        ViewLoader.closeDepartmentWindows();
        try {
            Stage stage = (Stage) logoutBtn.getScene().getWindow();
            ViewLoader.replaceScene(stage, "login-view.fxml", "Cedarline Supply - Sign in", 460, 520);
        } catch (IOException ex) {
            Feedback.alert(Alert.AlertType.ERROR, "Logout failed", ex.getMessage());
        }
    }

    private void openSection(Department target, String fxmlName, String title) {
        // second line of defence, in case a card is ever enabled by mistake
        StaffAccount who = Session.getCurrent();
        if (OWN_DEPARTMENT_ONLY && (who == null || who.getDepartment() != target)) {
            Feedback.alert(Alert.AlertType.WARNING, "Access denied",
                    "You can only manage your own department.");
            return;
        }
        try {
            ViewLoader.openWindow(fxmlName, title, 1180, 620);
        } catch (IOException ex) {
            Feedback.alert(Alert.AlertType.WARNING, "Section unavailable",
                    "This section could not be opened yet.\n" + ex.getMessage());
        }
    }
}
