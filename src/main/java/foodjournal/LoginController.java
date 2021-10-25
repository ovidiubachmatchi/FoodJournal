package foodjournal;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button login;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button signup;

    @FXML
    private Label wrong;

    private void checkLogin() throws IOException {
        Application m = new Application();
        if (username.getText().isEmpty() || password.getText().isEmpty())
            wrong.setText("Please enter your data");
        else {
            if(!(username.getText().isEmpty() || password.getText().isEmpty()))
                wrong.setText("");
            m.changeScene("afterLogin.fxml");
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        password.setFocusTraversable(false);
    }
    @FXML
    protected void loginButtonClicked() throws IOException {
        checkLogin();
    }

    private void signup() throws IOException {
        Application m = new Application();
        m.changeScene("signup.fxml");
    }

    @FXML
    protected void signupButtonClicked() throws IOException {
        signup();
    }

}