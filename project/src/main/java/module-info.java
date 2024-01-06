module com.fiit {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.fiit.gui to javafx.fxml;
    exports com.fiit.gui;
}