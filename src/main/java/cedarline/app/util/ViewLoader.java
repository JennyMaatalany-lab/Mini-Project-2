package cedarline.app.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Small helper so every controller loads FXML files the same way
 * (same folder, same stylesheet) instead of repeating FXMLLoader code.
 */
public final class ViewLoader {

    private static final String VIEWS_FOLDER = "/cedarline/app/views/";

    private static final List<Stage> departmentWindows = new ArrayList<>();

    private ViewLoader() {
    }

    public static Scene buildScene(String fxmlName, double width, double height) throws IOException {
        URL location = ViewLoader.class.getResource(VIEWS_FOLDER + fxmlName);
        if (location == null) {
            throw new IOException("View file not found: " + fxmlName);
        }
        Parent root = new FXMLLoader(location).load();
        Scene scene = new Scene(root, width, height);
        return scene;
    }

    /** Swaps the content of an existing window (used for login -> home and back). */
    public static void replaceScene(Stage stage, String fxmlName, String title,
                                    double width, double height) throws IOException {
        Scene scene = buildScene(fxmlName, width, height);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.sizeToScene();
        stage.centerOnScreen();
    }

    /** Opens a view in its own window and remembers it so it can be closed on logout. */
    public static void openWindow(String fxmlName, String title,
                                  double width, double height) throws IOException {
        Scene scene = buildScene(fxmlName, width, height);
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(scene);
        departmentWindows.add(stage);
        stage.setOnHidden(event -> departmentWindows.remove(stage));
        stage.show();
    }

    public static void closeDepartmentWindows() {
        for (Stage stage : new ArrayList<>(departmentWindows)) {
            stage.close();
        }
    }
}
