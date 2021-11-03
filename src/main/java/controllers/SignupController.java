package controllers;

import application.UserSession;
import application.methods.Animations;
import application.methods.PBKDF2;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class SignupController implements Initializable {

    @FXML
    private TextField password;

    @FXML
    private TextField repeatPassword;

    @FXML
    private TextField age;

    @FXML
    private TextField email;

    @FXML
    private TextField username;

    @FXML
    private TextField height;

    @FXML
    private TextField weight;

    @FXML
    private Label wrongLabel;

    @FXML
    private ChoiceBox genderCheckBox;

    @FXML
    private ChoiceBox objectiveCheckBox;

    @FXML
    private ChoiceBox weightChoiceBox;

    @FXML
    private ChoiceBox heightChoiceBox;

    @FXML
    private Button signup;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Animations.ScaleOnHover(signup);
        password.setFocusTraversable(false);
    }

    private void login() throws IOException {
        Application m = new Application();
        m.changeScene("login.fxml");
    }

    @FXML
    protected void loginButtonClicked() throws IOException {
        login();
    }

    private boolean signup() throws IOException {
        if (!fieldsUncompleted())
            return false;
        if (!emailCheck())
            return false;
//        if (!alreadySignup())
//            return false;
        if (!passwordCheck())
            return false;
        if (!heightCheck())
            return false;
        if (!weightCheck())
            return false;
        if (!ageCheck())
            return false;
        if (!genderCheck())
            return false;
        if (!objectiveCheck())
            return false;


        String hashedPassword = PBKDF2.getHashedPassword(password.getText());

        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;

        try {

            psCheckUserExists = Application.connectDB.prepareStatement("SELECT * FROM user WHERE email = ?");
            psCheckUserExists.setString(1, email.getText());
            resultSet = psCheckUserExists.executeQuery();

            if (resultSet.isBeforeFirst()) {
                System.out.println("User already exists!");
                wrongLabel.setText("This email is already taken");
            } else {
                psInsert = Application.connectDB.prepareStatement(
                        "INSERT INTO user (email, username, password, weight, height, objective, age, gender, weightMeasurement, heightMeasurement ) VALUES (?,?,?,?,?,?,?,?,?,?)"
                );
                psInsert.setString(1, email.getText());
                psInsert.setString(2, username.getText());
                psInsert.setString(3, hashedPassword);
                psInsert.setFloat(4, Float.parseFloat(weight.getText()));
                psInsert.setFloat(5, Float.parseFloat(height.getText()));
                psInsert.setString(6, objectiveCheckBox.getValue().toString());
                psInsert.setInt(7, Integer.parseInt(age.getText()));
                psInsert.setString(8, String.valueOf(genderCheckBox.getValue().toString().charAt(0)));
                psInsert.setString(9, weightChoiceBox.getValue().toString());
                psInsert.setString(10, heightChoiceBox.getValue().toString());

                psInsert.executeUpdate();
                UserSession.getInstance(
                        username.getText()
                );
                Application.changeScene("login.fxml");
            }
        }catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        return true;
    }

    private boolean ageCheck() {
        if (!whRegex(age.getText(),"round"))
        {
            wrongLabel.setText("Age should be a number");
            return false;
        }
        return true;
    }


    private boolean whRegex(String string, String mode){
        String regex;
        if (mode.equals("round"))
            regex = "^[0-9]+$";
        else
            regex = "[+-]?((\\d+\\.?\\d*)|(\\.\\d+))";

        Pattern pat = Pattern.compile(regex);
        if (string == null)
            return false;
        return pat.matcher(string).matches();
    }

    private boolean genderCheck() {
        if(genderCheckBox.getValue().equals("Select your gender")) {
            wrongLabel.setText("Please select your gender");
            return false;
        }
        return true;
    }

    private boolean objectiveCheck() {
        if(objectiveCheckBox.getValue().equals("Select your objective")) {
            wrongLabel.setText("Please select your objective");
            return false;
        }
        return true;
    }

    private boolean fieldsUncompleted() {
        if (username.getText().isEmpty() || password.getText().isEmpty() || email.getText().isEmpty() || weight.getText().isEmpty() || height.getText().isEmpty()){
            wrongLabel.setText("Please enter your data");
            return false;
        }
        return true;
    }

    private boolean alreadySignup() {
        return false;
    }


    private boolean weightCheck() {
        if (!whRegex(weight.getText(),"float"))
        {
            wrongLabel.setText("Weight should be a number");
            return false;
        }
        return true;
    }

    private boolean heightCheck() {
        if (!whRegex(height.getText(),"float"))
        {
            wrongLabel.setText("Height should be a number");
            return false;
        }
        return true;
    }

    public static boolean validEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    private boolean emailCheck() {
        if (validEmail(email.getText())==false) {
            wrongLabel.setText("Your email address is not valid");
            return false;
        }
        return true;
    }

    private boolean passwordCheck() {
        if (password.getText().length() < 8){
            wrongLabel.setText("Password must be at least 8 characters");
            return false;
        }
        if (password.getText().length() > 30){
            wrongLabel.setText("The password must be at most 30 characters long");
            return false;
        }
        return true;
    }

    @FXML
    protected void signupButtonClicked() throws IOException {
        signup();
    }

}
