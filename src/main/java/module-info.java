module com.example.foodjournal {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.commons.codec;


    opens controllers to javafx.fxml;
    exports controllers;
    exports application;
    opens application to javafx.fxml;
    exports application.methods;
    opens application.methods to javafx.fxml;
}