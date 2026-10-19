package cedarline.app;

import cedarline.app.util.ViewLoader;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        stage.setScene(ViewLoader.buildScene("login-view.fxml", 460, 520));
        stage.setTitle("Cedarline Supply - Sign in");
        stage.setResizable(false);
        // closing the main window ends the whole app, including any open department windows
        stage.setOnCloseRequest(event -> Platform.exit());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
