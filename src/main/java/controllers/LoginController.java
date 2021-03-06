package controllers;

import application.UserSession;
import application.methods.Animations;
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
import java.util.Objects;
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
    /**
     *  Aceasta metoda verifica daca am completat corect campurile din view-ul de logare
     *  si daca campurile sunt valide in baza de date atunci salvez datele in sesiunea de logare
     */
    private void checkLogin() throws IOException {
        if (email.getText().isEmpty() || password.getText().isEmpty())
            wrongLabel.setText("Please enter your data");
        else if (!validEmail(email.getText())) {
            wrongLabel.setText("Your email address is not valid");
        }
        else {
            wrongLabel.setText("");
            String hashedPassword = PBKDF2.getHashedPassword(password.getText());
            String verifyLogin = "SELECT * from users WHERE email = '" + email.getText() + "' AND password ='"+ hashedPassword +"'";
            try {
                Connection connectDB;
                DatabaseConnection connectNow = new DatabaseConnection();
                connectDB = connectNow.getConnection();

                if(connectDB == null)
                    wrongLabel.setText("Something went wrong");
                Statement statement = connectDB.createStatement();
                ResultSet queryResult = statement.executeQuery(verifyLogin);


                if(!queryResult.next()){
                    wrongLabel.setText("Invalid login. Please try again.");
                }
                else {
                    // String email, String username ,Short age, String gender, Float weight,Float height, String objective, String privileges, String AMR, int dailyKcal
                    UserSession.getInstance(
                            email.getText(),
                            queryResult.getString("username"),
                            queryResult.getShort("age"),
                            queryResult.getString("gender"),
                            queryResult.getFloat("weight"),
                            queryResult.getFloat("height"),
                            queryResult.getString("objective"),
                            queryResult.getString("privileges"),
                            queryResult.getString("AMR"),
                            queryResult.getInt("dailyKcal")
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
        if (!Objects.equals(UserSession.getFreshSignUpUsername(), "")) {
            wrongLabel.setWrapText(true);
            wrongLabel.setText("Thank you " +UserSession.getFreshSignUpUsername() +" for filling out our sign up form.\nWe are glad that you joined us.");
        }
        UserSession.getInstance();
    }

    @FXML
    protected void loginButtonClicked() throws IOException {
        checkLogin();
    }

    @FXML
    protected void signupButtonClicked() throws IOException {
        Application.changeScene("signup.fxml");
    }

}