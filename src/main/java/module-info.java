module com.example.foodjournal {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires org.json;
    requires json.simple;
    requires java.desktop;
    requires lombok;
    requires com.google.zxing;
    requires com.google.zxing.javase;
//    requires commons.codec;
    requires org.apache.commons.codec;
    requires webcam.capture;
    requires org.junit.jupiter.params;

    opens controllers to javafx.fxml;
    exports controllers;
    exports application;
    opens application to javafx.fxml;
    exports application.methods;
    opens application.methods to javafx.fxml;
}