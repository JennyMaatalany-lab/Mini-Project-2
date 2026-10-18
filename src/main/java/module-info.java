module cedarline.app {
    requires javafx.controls;
    requires javafx.fxml;

    // FXMLLoader needs reflective access to the controllers (@FXML fields and handlers)
    opens cedarline.app.controllers to javafx.fxml;
    // PropertyValueFactory reads the model properties by name, so javafx.base needs access
    opens cedarline.app.models to javafx.base;

    exports cedarline.app;
    exports cedarline.app.controllers;
    exports cedarline.app.models;
}
