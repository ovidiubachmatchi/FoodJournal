package foodjournal;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class AfterLoginController {

    @FXML
    private Button logout;

    public  void logoutCall() throws IOException {
        Application m = new Application();
        m.changeScene("login.fxml");
    }

}
