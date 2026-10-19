package cedarline.app.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.util.Optional;

/** Message label colours and the pop-up dialogs used across the views. */
public final class Feedback {

    private static final Color ERROR_COLOR = Color.valueOf("#a8362f");
    private static final Color OK_COLOR = Color.valueOf("#1f6b46");

    private Feedback() {
    }

    public static void error(Label label, String message) {
        label.setTextFill(ERROR_COLOR);
        label.setText(message);
    }

    public static void ok(Label label, String message) {
        label.setTextFill(OK_COLOR);
        label.setText(message);
    }

    public static void clear(Label label) {
        label.setText("");
    }

    public static boolean confirm(String message) {
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);
        dialog.setTitle("Please confirm");
        dialog.setHeaderText(null);
        Optional<ButtonType> choice = dialog.showAndWait();
        return choice.isPresent() && choice.get() == ButtonType.YES;
    }

    public static void alert(Alert.AlertType type, String title, String message) {
        Alert dialog = new Alert(type, message, ButtonType.OK);
        dialog.setTitle(title);
        dialog.setHeaderText(null);
        dialog.showAndWait();
    }
}
