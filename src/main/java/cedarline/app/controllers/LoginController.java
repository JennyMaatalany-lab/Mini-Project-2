package cedarline.app.controllers;

import cedarline.app.models.AccountRegistry;
import cedarline.app.models.StaffAccount;
import cedarline.app.util.Feedback;
import cedarline.app.util.Session;
import cedarline.app.util.ViewLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class LoginController {

    @FXML
    private TextField usernameFld;

    @FXML
    private PasswordField passwordFld;

    @FXML
    private Button loginBtn;

    @FXML
    private Label errorMsg;

    @FXML
    void handleLogin(ActionEvent event) {
        String username = usernameFld.getText().trim();
        String password = passwordFld.getText();

        if (username.isEmpty() || password.isEmpty()) {
            Feedback.error(errorMsg, "Please enter your username and password.");
            return;
        }

        Optional<StaffAccount> match = AccountRegistry.authenticate(username, password);
        if (match.isEmpty()) {
            Feedback.error(errorMsg, "Incorrect username or password.");
            passwordFld.clear();
            passwordFld.requestFocus();
            return;
        }

        Session.start(match.get());
        try {
            Stage stage = (Stage) loginBtn.getScene().getWindow();
            ViewLoader.replaceScene(stage, "home-view.fxml", "Cedarline Supply - Home", 760, 480);
        } catch (IOException ex) {
            Session.end();
            Feedback.error(errorMsg, "Could not open the home screen: " + ex.getMessage());
        }
    }
}
