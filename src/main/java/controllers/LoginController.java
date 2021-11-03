package controllers;

import application.UserSession;
import application.methods.Animations;
import application.methods.PBKDF2;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import static controllers.SignupController.validEmail;

public class LoginController implements Initializable {

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Label wrongLabel;

    @FXML
    private Button loginBtn;

    private void checkLogin() throws IOException {
        if (email.getText().isEmpty() || password.getText().isEmpty())
            wrongLabel.setText("Please enter your data");
        else if (validEmail(email.getText())==false) {
            wrongLabel.setText("Your email address is not valid");
        }
        else {
            wrongLabel.setText("");

            String hashedPassword = PBKDF2.getHashedPassword(password.getText());

            String verifyLogin = "SELECT * from user WHERE email = '" + email.getText() + "' AND password ='"+ hashedPassword +"'";

            try {

                if(controllers.Application.connectDB == null)
                    wrongLabel.setText("Something went wrong");
                Statement statement = Application.connectDB.createStatement();
                ResultSet queryResult = statement.executeQuery(verifyLogin);


                if(queryResult.next() == false){
                    wrongLabel.setText("Invalid login. Please try again.");
                }
                else {
                    UserSession.getInstance(
                            email.getText(),
                            queryResult.getString(11),
                            queryResult.getString(3),
                            queryResult.getShort(12),
                            queryResult.getFloat(6),
                            queryResult.getFloat(5),
                            queryResult.getString(9),
                            queryResult.getString(4),
                            queryResult.getString(8),
                            queryResult.getString(7)
                    );
                    Application.changeScene("mainMenu.fxml");
                }
            }catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Animations.ScaleOnHover(loginBtn);

        email.setText("ovidiubachmatchi@gmail.com");
        password.setText("123123123");

        password.setFocusTraversable(false);
        if (UserSession.getFreshSignUpUsername() != "") {
            wrongLabel.setWrapText(true);
            wrongLabel.setText("Thank you " +UserSession.getFreshSignUpUsername() +" for filling out our sign up form.\nWe are glad that you joined us.");
        }
        UserSession.getInstance();
    }

    @FXML
    protected void loginButtonClicked() throws IOException {
        checkLogin();
    }

    private void signup() throws IOException {
        Application.changeScene("signup.fxml");
    }

    @FXML
    protected void signupButtonClicked() throws IOException {
        signup();
    }

}