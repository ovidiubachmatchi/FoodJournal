package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class SignupController implements Initializable {

    @FXML
    private TextField password;

    @FXML
    private TextField repeatPassword;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        if (!genderCheck())
            return false;

        Application m = new Application();
        m.changeScene("login.fxml");
        return true;
    }

    private boolean genderCheck() {
        if(genderCheckBox.getValue().equals("Select your gender")) {
            wrongLabel.setText("Please select your gender");
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

    private boolean whRegex(String string){
        String emailRegex = "[+-]?([0-9]*[.,])?[0-9]+";

        Pattern pat = Pattern.compile(emailRegex);
        if (string == null)
            return false;
        return pat.matcher(string).matches();
    }

    private boolean weightCheck() {
        if (!whRegex(weight.getText()))
        {
            wrongLabel.setText("Weight should be a number");
            return false;
        }
        return true;
    }

    private boolean heightCheck() {
        if (!whRegex(height.getText()))
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
