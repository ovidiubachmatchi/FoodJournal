package controllers;

import application.UserSession;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.fxml.Initializable;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenu implements Initializable {

    @FXML
    private Button addButton;

    @FXML
    private Label plusLabel,minusLabel;

    @FXML
    private Pane plusPane,plusSection;

    @FXML
    private Circle plusCircle1,plusCircle2,plusCircle3;

    TranslateTransition plusSectionTransition = new TranslateTransition();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addButton.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                plusCircle2.setScaleX(1.05);
                plusCircle2.setScaleY(1.05);
                plusCircle2.setScaleZ(1.05);
                plusCircle3.setScaleX(1.05);
                plusCircle3.setScaleY(1.05);
                plusCircle3.setScaleZ(1.05);
            } else {
                plusCircle2.setScaleX(1);
                plusCircle2.setScaleY(1);
                plusCircle2.setScaleZ(1);
                plusCircle3.setScaleX(1);
                plusCircle3.setScaleY(1);
                plusCircle3.setScaleZ(1);
            }
        });
        plusSectionTransition.setDuration(Duration.millis(350));
        plusSectionTransition.setNode(plusSection);
    }

    public void logoutCall() throws IOException {
        UserSession.getInstance();
        Application.changeScene("login.fxml");
    }


    public void addButtonAction(ActionEvent actionEvent) {
        System.out.println(plusSection.getTranslateY());
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
}
