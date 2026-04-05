module com.nepalilandconverter {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    requires com.dlsc.formsfx;

    opens com.nepalilandconverter to javafx.fxml;
    exports com.nepalilandconverter;

    requires com.jfoenix;
}