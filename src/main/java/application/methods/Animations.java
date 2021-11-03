package application.methods;

import javafx.scene.control.Button;
import javafx.scene.shape.Circle;

public class Animations {
    public static void ScaleOnHover(Button myBtn) {
        myBtn.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                myBtn.setScaleX(1.05);
                myBtn.setScaleY(1.05);
                myBtn.setScaleZ(1.05);
            } else {
                myBtn.setScaleX(1);
                myBtn.setScaleY(1);
                myBtn.setScaleZ(1);
            }
        });
    }
        public static void ScaleOnHover(Button myBtn, Circle[] circles) {
            myBtn.hoverProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    for(Circle circle : circles) {
                        circle.setScaleX(1.05);
                        circle.setScaleY(1.05);
                        circle.setScaleZ(1.05);
                    }
                } else {
                    for(Circle circle : circles) {
                        circle.setScaleX(1);
                        circle.setScaleY(1);
                        circle.setScaleZ(1);
                    }
                }
            });
        }
}
