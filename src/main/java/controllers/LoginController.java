package controllers;

import application.UserSession;
import application.methods.DatabaseConnection;
import application.methods.PBKDF2;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button login;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Button signup;

    @FXML
    private Label wrong;

    private void checkLogin() throws IOException {
        if (email.getText().isEmpty() || password.getText().isEmpty())
            wrong.setText("Please enter your data");
        else {
            wrong.setText("");

            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();

            String hashedPassword = PBKDF2.getHashedPassword(password.getText());

            String verifyLogin = "SELECT * from users WHERE email = '" + email.getText() + "' AND password ='"+ hashedPassword +"'";

            try {

                Statement statement = connectDB.createStatement();
                ResultSet queryResult = statement.executeQuery(verifyLogin);


                if(queryResult.next() == false){
                    wrong.setText("Invalid login. Please try again.");
                }
                else {
                    String _email = email.getText();
                    String _privileges = queryResult.getString(9);
                    String _username = queryResult.getString(3);
                    Short _age = queryResult.getShort(8);
                    Short _weight = queryResult.getShort(5);
                    Short _height = queryResult.getShort(6);
                    String _objective = queryResult.getString(7);
                    UserSession.getInstance(_email,_privileges,_username,_age,_weight,_height,_objective);
                    Application.changeScene("afterLogin.fxml");
                }
            }catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }
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