package controllers;

import application.UserSession;
import application.methods.Animations;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.fxml.Initializable;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class MainMenu implements Initializable {

    @FXML
    private Button addButton,addToJournal;

    @FXML
    private Label plusLabel,minusLabel;

    @FXML
    private Pane plusPane,plusSection;

    @FXML
    private Circle plusCircle1,plusCircle2,plusCircle3;

    TranslateTransition plusSectionTransition = new TranslateTransition();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Animations.ScaleOnHover(addButton,new Circle[]{plusCircle2,plusCircle3});
        Animations.ScaleOnHover(addToJournal);
        plusSectionTransition.setDuration(Duration.millis(350));
        plusSectionTransition.setNode(plusSection);
    }

    public void logoutCall() throws IOException {
        UserSession.getInstance();
        Application.changeScene("login.fxml");
    }

    @FXML
    private DatePicker datePickerAdd;
    @FXML
    private TextField weightFieldAdd,heightFieldAdd;
    public void addButtonAction(ActionEvent actionEvent) {
        datePickerAdd.setValue(LocalDate.now());
        weightFieldAdd.setText(String.valueOf(UserSession.getWeight()));
        heightFieldAdd.setText((String.valueOf(UserSession.getHeight())));
        if(plusSection.getTranslateY() == 0) {
            plusSectionTransition.setToY(-362);
            plusSectionTransition.play();
            plusLabel.setVisible(false);
            minusLabel.setVisible(true);

        }
        if(plusSection.getTranslateY() == -362){
            plusSectionTransition.setToY(0);
            plusSectionTransition.play();
            plusLabel.setVisible(true);
            minusLabel.setVisible(false);
        }
    }
    @FXML
    private Button logout;

    public void avatarBtnAction(ActionEvent actionEvent) {
        if (logout.isVisible())
            logout.setVisible(false);
        else
            logout.setVisible(true);

    }
}
