package controllers;

import application.UserSession;
import application.methods.Animations;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.fxml.Initializable;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static application.methods.JsonReader.scan_barcode;

public class MainMenu implements Initializable {

    @FXML
    private Button addButton,addToJournal;

    @FXML
    private Label plusLabel,minusLabel;

    @FXML
    private Pane plusSection,background;

    @FXML
    private Circle plusCircle2,plusCircle3;

    TranslateTransition plusSectionTransition = new TranslateTransition();
    TranslateTransition addMealPaneTransition = new TranslateTransition();

    @FXML
    private TextField barcodeField;
    @FXML
    private Button checkBarCode,scan;
    @FXML
    private Label weightLabelAdd,heightLabelAdd;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DecimalFormat f = new DecimalFormat("###.##");
        weightFieldAddKG.textProperty().addListener((observable, oldValue, newValue) -> {
            if (weightFieldAddKG.getText().equals(""))
                weightFieldAddLB.setText("");
            try {
            weightFieldAddLB.setText(f.format(((Float.parseFloat(weightFieldAddKG.getText())*2.2046))));
            if (weightLabelAdd.getText().equals("Invalid format"))
                weightLabelAdd.setText("Weight");
        }
            catch (java.lang.NumberFormatException e) {
            weightLabelAdd.setText("Invalid format");
        }
        });

        weightFieldAddLB.textProperty().addListener((observable, oldValue, newValue) -> {
            if (weightFieldAddLB.getText().equals(""))
                weightFieldAddKG.setText("");
            try {
                weightFieldAddKG.setText(f.format(((Float.parseFloat(weightFieldAddLB.getText()) * 0.4536))));
                if (weightLabelAdd.getText().equals("Invalid format"))
                    weightLabelAdd.setText("Weight");
            }
            catch (java.lang.NumberFormatException e) {
                weightLabelAdd.setText("Invalid format");
            }
        });

        weightFieldAddKG.setText(String.valueOf(UserSession.getWeight()));

        heightFieldAddFEET.textProperty().addListener((observable, oldValue, newValue) -> {
            if (heightFieldAddFEET.getText().equals(""))
                heightFieldAddM.setText("");
            try {
                heightFieldAddM.setText(f.format((Float.parseFloat(heightFieldAddFEET.getText()) * 0.3048)));
                if (heightLabelAdd.getText().equals("Invalid format"))
                    heightLabelAdd.setText("Height");
            }
            catch (java.lang.NumberFormatException e) {
                heightLabelAdd.setText("Invalid format");
            }
        });

        heightFieldAddM.textProperty().addListener((observable, oldValue, newValue) -> {
            if (heightFieldAddM.getText().equals(""))
                heightFieldAddFEET.setText("");
            try {
                heightFieldAddFEET.setText(f.format((Float.parseFloat(heightFieldAddM.getText()) * 3.2808)));
                if (heightLabelAdd.getText().equals("Invalid format"))
                    heightLabelAdd.setText("Height");
            }
            catch (java.lang.NumberFormatException e) {
                heightLabelAdd.setText("Invalid format");
            }
        });

        heightFieldAddM.setText((String.valueOf(UserSession.getHeight())));


        Animations.ScaleOnHover(addButton,new Circle[]{plusCircle2,plusCircle3});
        Animations.ScaleOnHover(addToJournal);
        plusSectionTransition.setDuration(Duration.millis(350));
        plusSectionTransition.setNode(plusSection);
        addMealPaneTransition.setDuration(Duration.millis(350));
        addMealPaneTransition.setNode(addMealPane);
        barcodeField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!checkBarCode.getText().equals("Check bar code"))
            {
                checkBarCode.setText("Check bar code");
                checkBarCode.setTextFill(Color.BLACK);
            }
            if (newValue.equals("")) {
                checkBarCode.setVisible(false);
                scan.setVisible(true);
            }
            else {
                checkBarCode.setVisible(true);
                scan.setVisible(false);
            }
        });
        background.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            logout.setVisible(false);
            editProfile.setVisible(false);
            getTranslate362();
        });
    }

    private void getTranslate362() {
        if(plusSection.getTranslateY() == -362){
            plusSectionTransition.setToY(0);
            plusSectionTransition.play();
            plusLabel.setVisible(true);
            minusLabel.setVisible(false);
        }
        if(addMealPane.getTranslateY() == -362){
            addMealPaneTransition.setDuration(Duration.millis(350));
            addMealPaneTransition.setToY(0);
            addMealPaneTransition.play();
        }
    }

    public void logoutCall() throws IOException {
        UserSession.getInstance();
        Application.changeScene("login.fxml");
    }

    @FXML
    private DatePicker datePickerAdd;
    @FXML
    private TextField weightFieldAddKG,heightFieldAddM,weightFieldAddLB,heightFieldAddFEET;
    public void addButtonAction() {
        datePickerAdd.setValue(LocalDate.now());
        if(plusSection.getTranslateY() == 0) {
            plusSectionTransition.setToY(-362);
            plusSectionTransition.play();
            plusLabel.setVisible(false);
            minusLabel.setVisible(true);

        }
        getTranslate362();
    }
    @FXML
    private Button editProfile,logout;

    public void avatarBtnAction() {
        logout.setVisible(!logout.isVisible());
        editProfile.setVisible(!editProfile.isVisible());
    }
    public void editProfileCall() {

    }
    @FXML Pane addMealPane;
    public void addMealBtn() {
        System.out.println(addMealPane.getTranslateY());
        if(addMealPane.getTranslateY() == 0) {
            addMealPaneTransition.setDuration(Duration.millis(200));
            addMealPaneTransition.setToY(-362);
            addMealPaneTransition.play();

        }
        if(addMealPane.getTranslateY() == -362){
            addMealPaneTransition.setDuration(Duration.millis(350));
            addMealPaneTransition.setToY(0);
            addMealPaneTransition.play();
        }
    }
    @FXML Label barcodeLabel;
    public void checkBarCodeBtn() throws IOException {
        JSONObject scanner = scan_barcode(barcodeField.getText());
        if( scanner.get("status").equals(0)) {
            checkBarCode.setText("ITEM NOT FOUND");
        }
        else {
            checkBarCode.setText("ITEM FOUND");
        }
        checkBarCode.setTextFill(Color.RED);
    }
}
