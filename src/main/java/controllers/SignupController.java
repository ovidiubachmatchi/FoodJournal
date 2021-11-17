package controllers;

import application.UserSession;
import application.methods.Animations;
import application.methods.DatabaseConnection;
import application.methods.PBKDF2;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class SignupController implements Initializable {

    @FXML
    private TextField password,age, email, username,height, weight;

    @FXML
    private Label wrongLabel;

    @FXML
    private ChoiceBox<String> genderCheckBox, objectiveCheckBox,weightChoiceBox,heightChoiceBox,activityCheckBox;

    @FXML
    private Button signup;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Animations.ScaleOnHover(signup);
        password.setFocusTraversable(false);
        heightChoiceBox.setOnAction(this::heightChoiceBoxChange);
    }
    @FXML
    private TextField inches,feet;
    private void heightChoiceBoxChange(ActionEvent actionEvent) {
        if(heightChoiceBox.getValue().equals("feet"))
        {
            feet.setVisible(true);
            inches.setVisible(true);
            height.setVisible(false);
        }
        else {
            feet.setVisible(false);
            inches.setVisible(false);
            height.setVisible(true);
        }
    }


    @FXML
    protected void loginButtonClicked() throws IOException {
        Application.changeScene("login.fxml");
    }

    private void signup() throws IOException {
        if (!fieldsUncompleted())
            return;
        if (!emailCheck())
            return;
        if (!passwordCheck())
            return;
        if (!heightCheck())
            return;
        if (!weightCheck())
            return;
        if (!ageCheck())
            return;
        if (!inchesCheck())
            return;
        if (!feetCheck())
            return;
        if (!genderCheck())
            return;
        if (!objectiveCheck())
            return;


        String hashedPassword = PBKDF2.getHashedPassword(password.getText());

        PreparedStatement psInsert;
        PreparedStatement psCheckUserExists;
        ResultSet resultSet;

        try {

            Connection connectDB;
            DatabaseConnection connectNow = new DatabaseConnection();
            connectDB = connectNow.getConnection();

            psCheckUserExists = connectDB.prepareStatement("SELECT * FROM users WHERE email = ?");
            psCheckUserExists.setString(1, email.getText());
            resultSet = psCheckUserExists.executeQuery();

            if (resultSet.isBeforeFirst()) {
                System.out.println("User already exists!");
                wrongLabel.setText("This email is already taken");
            } else {
                DecimalFormat f = new DecimalFormat("###.00");
                psInsert = connectDB.prepareStatement(
                        "INSERT INTO users (email, username, password, weight, height, objective, age, gender, AMR) VALUES (?,?,?,?,?,?,?,?,?)"
                );
                psInsert.setString(1, email.getText());
                psInsert.setString(2, username.getText());
                psInsert.setString(3, hashedPassword);
                psInsert.setString(6, String.valueOf(objectiveCheckBox.getValue().charAt(7)));
                psInsert.setString(9, String.valueOf(activityCheckBox.getValue().charAt(0)));
                psInsert.setInt(7, Integer.parseInt(age.getText()));
                psInsert.setString(8, String.valueOf(genderCheckBox.getValue().charAt(0)));
                if (weightChoiceBox.getValue().equals("kg")){
                    psInsert.setFloat(4, Float.parseFloat(f.format(Float.parseFloat(weight.getText()))));
                }
                if (weightChoiceBox.getValue().equals("lb")) {
                    float kg = Float.parseFloat(f.format(Float.parseFloat(f.format(Float.parseFloat(weight.getText())))*0.4536));
                    psInsert.setFloat(4, kg);
                }
                if (heightChoiceBox.getValue().equals("feet")){
                    float cm = ((float)(((int) ((Integer.parseInt(feet.getText()) * 30.48) + (Integer.parseInt(inches.getText()) * 2.54)))))/100;
                    psInsert.setFloat(5, cm);
                }
                if (heightChoiceBox.getValue().equals("m")) {
                    psInsert.setFloat(5, Float.parseFloat(f.format(Float.parseFloat(height.getText()))));
                }
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

    }

    private boolean ageCheck() {
        if (whRegex(age.getText(), "round"))
        {
            wrongLabel.setText("Age should be a rounded number");
            return false;
        }
        return true;
    }

    private boolean feetCheck() {
        if (!feet.getText().equals("")) {
            if (whRegex(feet.getText(), "round")) {
                wrongLabel.setText("Feet should be a rounded number");
                return false;
            }
            if (Integer.parseInt(feet.getText()) > 8) {
                wrongLabel.setText("Feet should not be over 8");
                return false;
            }
            if (Integer.parseInt(feet.getText()) < 1) {
                wrongLabel.setText("Feet should be over 1");
                return false;
            }
        }
        return true;
    }
    private boolean inchesCheck() {
        if (!inches.getText().equals("")) {
            if (whRegex(inches.getText(), "round")) {
                wrongLabel.setText("Inches should be a rounded number");
                return false;
            }
            if (Integer.parseInt(inches.getText()) < 0) {
                wrongLabel.setText("Inches should be a positive number");
                return false;
            }
            if (Integer.parseInt(inches.getText()) > 11) {
                wrongLabel.setText("Inches should not be over 11");
                return false;
            }
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
            return true;
        return !pat.matcher(string).matches();
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
        if(activityCheckBox.getValue().equals("Select your activity level")) {
            wrongLabel.setText("Please select activity level");
            return false;
        }
        return true;
    }

    private boolean fieldsUncompleted() {
        if (username.getText().isEmpty() || password.getText().isEmpty() || email.getText().isEmpty() || weight.getText().isEmpty()){
            wrongLabel.setText("Please enter your data");
            return false;
        }
        if(height.getText().isEmpty())
            if(feet.getText().isEmpty() || inches.getText().isEmpty()){
            wrongLabel.setText("Please enter your data");
            return false;
        }
        return true;
    }


    private boolean weightCheck() {
        if (whRegex(weight.getText(), "float"))
        {
            wrongLabel.setText("Weight should be a number");
            return false;
        }
        if (weightChoiceBox.getValue().equals("lb") && Float.parseFloat(weight.getText()) > 1400)
        {
            wrongLabel.setText("Weight should not be over 1400lb");
            return false;
        }
        if (weightChoiceBox.getValue().equals("kg") && Float.parseFloat(weight.getText()) > 650)
        {
            wrongLabel.setText("Weight should not be over 650kg");
            return false;
        }
        if ( Float.parseFloat(weight.getText()) < 0)
        {
            wrongLabel.setText("Weight should be a positive number");
            return false;
        }
        return true;
    }

    private boolean heightCheck() {
        if (!height.getText().equals("")) {
            if (whRegex(height.getText(), "float")) {
                wrongLabel.setText("Height should be a number");
                return false;
            }
            if (Float.parseFloat(height.getText()) > 3.00)
            {
                wrongLabel.setText("Height should not be over 3 meters");
                return false;
            }
            if (Float.parseFloat(height.getText()) < 0.50)
            {
                wrongLabel.setText("Height should be over 0.5 meters");
                return false;
            }
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
        if (!validEmail(email.getText())) {
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
