module com.example.foodjournal {
    requires javafx.controls;
    requires javafx.fxml;


    opens foodjournal to javafx.fxml;
    exports foodjournal;
}