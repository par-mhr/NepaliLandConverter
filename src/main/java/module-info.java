module com.example.nepalilandconverter {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.nepalilandconverter to javafx.fxml;
    exports com.example.nepalilandconverter;

    requires com.jfoenix;
}