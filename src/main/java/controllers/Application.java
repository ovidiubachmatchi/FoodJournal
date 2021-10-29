package controllers;

import application.methods.DatabaseConnection;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;

public class Application extends javafx.application.Application {

    public static Connection connectDB;
    private static Stage stg;
    static DatabaseConnection connectNow = new DatabaseConnection();

    @Override
    public void start(Stage stage) throws IOException {
        stg = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setTitle("Food Journal");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image("file:logo.png"));
        stage.show();

        connectDB = connectNow.getConnection();
    }

    public static void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(Application.class.getResource(fxml));
        stg.getScene().setRoot(pane);
    }
    public static void changeScene(String fxml, String username) throws IOException {
        Parent pane = FXMLLoader.load(Application.class.getResource(fxml));
        stg.getScene().setRoot(pane);
    }

    public static void main(String[] args) {
        launch();
    }
}