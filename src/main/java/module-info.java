module com.nepalilandconverter {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires java.desktop;

    requires com.dlsc.formsfx;
    requires com.jfoenix;

    opens com.nepalilandconverter to javafx.fxml;
    exports com.nepalilandconverter;
}