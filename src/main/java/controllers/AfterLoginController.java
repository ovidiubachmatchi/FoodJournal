package controllers;

import application.UserSession;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class AfterLoginController {

    @FXML
    private Button logout;

    public  void logoutCall() throws IOException {
        UserSession.cleanUserSession();
        Application.changeScene("login.fxml");
    }

}
