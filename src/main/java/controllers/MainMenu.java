package controllers;

import application.UserSession;
import application.methods.Animations;
import application.methods.Camera;
import application.methods.DatabaseConnection;
import hashmap.*;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import static application.methods.OnlineReadJSON.scan_barcode;

public class MainMenu implements Initializable {

    public Button cancel;
    public Button addTheMeal;
    public Pane underBottomSection;
    public Pane bottomSection;
    public Pane plusPane;
    public Pane profilePane;
    public Circle plusCircle1;
    public Button avatarBtn;
    public String choicebox_nutrition_data_per;
    public Text serving_metric;
    public ChoiceBox<String> serving;
    public TextField serving_value;
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
    private Button checkBarCode,scanWithCameraBTN;
    @FXML
    private Label weightLabelAdd,heightLabelAdd;
    String srv_vlu;
    @FXML
    private DatePicker goalDatePicker;
    public static TextField static_barcodeField;
    @FXML private LineChart linechart;
    @FXML private StackedBarChart<String, Number> sbc;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        static_barcodeField = barcodeField;
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
                scanWithCameraBTN.setVisible(true);
            }
            else {
                checkBarCode.setVisible(true);
                scanWithCameraBTN.setVisible(false);
            }
        });
        background.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            logout.setVisible(false);
            editProfile.setVisible(false);
            getTranslate362();
            newKcalGoalPane.setVisible(false);
//            try {
//                OCR.BarcodeInfo test = OCR.decodeImage(new FileInputStream("C:\\Users\\ovidiu\\Downloads\\barcode.jpg"));
//                System.out.println(test.getText());
//            } catch (OCR.BarcodeDecodingException e) {
//                e.printStackTrace();
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            };
        });

        serving.setOnAction(this::servingChange);
        goalDatePicker.setValue(LocalDate.now());
        barchartInit();
        changeGoalDetails("");
        goalDatePicker.valueProperty().addListener((observable, oldDate, newDate)-> changeGoalDetails(String.valueOf(oldDate)));
        datePickerAdd.valueProperty().addListener((observable, oldDate, newDate)-> changeDatePickerAddLabel());
    }
    @FXML LineChart weightChart;
    private void barchartInit() {
        sbc.getData().clear();
        weightChart.getData().clear();
        weightChart.setAnimated(true);
        sbc.setAnimated(true);// enable animation
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        XYChart.Series<String, Number> kgseries = new XYChart.Series<>();
        series.setName("Week kcal progress");
        Calendar c = Calendar.getInstance();
        c.setTime(java.sql.Date.valueOf(goalDatePicker.getValue()));
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        LocalDate datePicked = goalDatePicker.getValue();
//        System.out.println("date picked: "+(datePicked));
//        System.out.println("monday: date modified by -"+(dayOfWeek-1)+" days: "+(datePicked.minusDays(dayOfWeek-1)));

        HashMapp map = new HashMapp();
        // weekly kcal consumtion
        map.put(new Key(1), new Value(getKcalConsumedByDate(String.valueOf(datePicked.minusDays(dayOfWeek-1)))));
        map.put(new Key(2), new Value(getKcalConsumedByDate(String.valueOf(datePicked.minusDays(dayOfWeek-2)))));
        map.put(new Key(3), new Value(getKcalConsumedByDate(String.valueOf(datePicked.minusDays(dayOfWeek-3)))));
        map.put(new Key(4), new Value(getKcalConsumedByDate(String.valueOf(datePicked.minusDays(dayOfWeek-4)))));
        map.put(new Key(5), new Value(getKcalConsumedByDate(String.valueOf(datePicked.minusDays(dayOfWeek-5)))));
        map.put(new Key(6), new Value(getKcalConsumedByDate(String.valueOf(datePicked.minusDays(dayOfWeek-6)))));
        map.put(new Key(7), new Value(getKcalConsumedByDate(String.valueOf(datePicked.minusDays(dayOfWeek-7)))));
        // weekly weight change
        map.put(new Key(8), new Value(getWeightConsumedByDate(String.valueOf(datePicked.minusDays(dayOfWeek-1)))));
        map.put(new Key(9), new Value(getWeightConsumedByDate(String.valueOf(datePicked.minusDays(dayOfWeek-2)))));
        map.put(new Key(10), new Value(getWeightConsumedByDate(String.valueOf(datePicked.minusDays(dayOfWeek-3)))));
        map.put(new Key(11), new Value(getWeightConsumedByDate(String.valueOf(datePicked.minusDays(dayOfWeek-4)))));
        map.put(new Key(12), new Value(getWeightConsumedByDate(String.valueOf(datePicked.minusDays(dayOfWeek-5)))));
        map.put(new Key(13), new Value(getWeightConsumedByDate(String.valueOf(datePicked.minusDays(dayOfWeek-6)))));
        map.put(new Key(14), new Value(getWeightConsumedByDate(String.valueOf(datePicked.minusDays(dayOfWeek-7)))));

        series.getData().add(new XYChart.Data<>("Sunday", map.get(new Key(1)).getValue()));
        series.getData().add(new XYChart.Data<>("Monday", map.get(new Key(2)).getValue()));
        series.getData().add(new XYChart.Data<>("Tuesday", map.get(new Key(3)).getValue()));
        series.getData().add(new XYChart.Data<>("Wednesday", map.get(new Key(4)).getValue()));
        series.getData().add(new XYChart.Data<>("Thursday", map.get(new Key(5)).getValue()));
        series.getData().add(new XYChart.Data<>("Friday", map.get(new Key(6)).getValue()));
        series.getData().add(new XYChart.Data<>("Saturday", map.get(new Key(7)).getValue()));

        if (map.get(new Key(8)).getValue() > 0)
        kgseries.getData().add(new XYChart.Data<>("Sunday", map.get(new Key(8)).getValue()));
        if (map.get(new Key(9)).getValue() > 0 )
        kgseries.getData().add(new XYChart.Data<>("Monday", map.get(new Key(9)).getValue()));
        if (map.get(new Key(10)).getValue() > 0)
        kgseries.getData().add(new XYChart.Data<>("Tuesday", map.get(new Key(10)).getValue()));
        if (map.get(new Key(11)).getValue() > 0)
        kgseries.getData().add(new XYChart.Data<>("Wednesday", map.get(new Key(11)).getValue()));
        if (map.get(new Key(12)).getValue() > 0)
        kgseries.getData().add(new XYChart.Data<>("Thursday", map.get(new Key(12)).getValue()));
        if (map.get(new Key(13)).getValue() > 0)
        kgseries.getData().add(new XYChart.Data<>("Friday", map.get(new Key(13)).getValue()));
        if (map.get(new Key(14)).getValue() > 0)
        kgseries.getData().add(new XYChart.Data<>("Saturday", map.get(new Key(14)).getValue()));

        weightChart.getData().add(kgseries);
        sbc.getData().add(series);
        weightChart.setAnimated(false);
        sbc.setAnimated(false);// enable animation
        sbcY.setAutoRanging(true);
        weekProgress.setText("KCAL progress in week "+goalDatePicker.getValue().get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear())+" of "+goalDatePicker.getValue().getYear());
    }
    @FXML private Label weekProgress;
    @FXML private NumberAxis sbcY;
    /*
        String date (YYYY-MM-DD)
    */
    private int getKcalConsumedByDate(String date) {
        int kcal_eaten = 0;
        try {
            Connection connectDB;
            DatabaseConnection connectNow = new DatabaseConnection();
            connectDB = connectNow.getConnection();
            Statement statement = connectDB.createStatement();

            String verifyLogin = "SELECT json_extract(history, '$." + date + "') AS 'date' " +
                    "FROM users " +
                    "WHERE email = '" + UserSession.getEmail() + "';";
            ResultSet queryResult = statement.executeQuery(verifyLogin);
            queryResult.next();
            String getDate = queryResult.getString(1);

            if (getDate != null) {
                JSONObject getDateJSON = new JSONObject(getDate.substring(1, getDate.length() - 1));
                kcal_eaten = (int) getDateJSON.getFloat("kcal_consumed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return kcal_eaten;
    }
    private int getWeightConsumedByDate(String date) {
        int weight = 0;
        try {
            Connection connectDB;
            DatabaseConnection connectNow = new DatabaseConnection();
            connectDB = connectNow.getConnection();
            Statement statement = connectDB.createStatement();

            String verifyLogin = "SELECT json_extract(history, '$." + date + "') AS 'date' " +
                    "FROM users " +
                    "WHERE email = '" + UserSession.getEmail() + "';";
            ResultSet queryResult = statement.executeQuery(verifyLogin);
            queryResult.next();
            String getDate = queryResult.getString(1);

            if (getDate != null) {
                JSONObject getDateJSON = new JSONObject(getDate.substring(1, getDate.length() - 1));
                weight = (int) getDateJSON.getFloat("weight");
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return weight;
    }
    @FXML private Label todayLabelAdd;
    private void changeDatePickerAddLabel() {
        todayLabelAdd.setVisible(datePickerAdd.getValue().equals(LocalDate.now()));
    }
    public void scanWithCameraCall() {
        try {
            new Camera();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    @FXML private Text goalKCALEaten,goalKCALGoal,goalKCALBurned;
    @FXML private Label todayLabel;
    @FXML private PieChart testChart;
    // goalDatePicker event listener for this \/
    /**
     *  Aceasta metoda updateaza datele din interfata grafica laund datele din baza de date
     */
    private void changeGoalDetails(String oldDate) {
        todayLabel.setVisible(goalDatePicker.getValue().equals(LocalDate.now()));
        try {
            Connection connectDB;
            DatabaseConnection connectNow = new DatabaseConnection();
            connectDB = connectNow.getConnection();
            Statement statement = connectDB.createStatement();

            String verifyLogin = "SELECT json_extract(history, '$." + goalDatePicker.getValue() + "') AS 'date' " +
                    "FROM users " +
                    "WHERE email = '" + UserSession.getEmail() + "';";
            ResultSet queryResult = statement.executeQuery(verifyLogin);
            queryResult.next();
            String getDate = queryResult.getString(1);

            int kcal_eaten = 0;
            if (getDate != null) {
                JSONObject getDateJSON = new JSONObject(getDate.substring(1, getDate.length() - 1));
                kcal_eaten = (int) getDateJSON.getFloat("kcal_consumed");
                goalKCALEaten.setText(String.valueOf(kcal_eaten));
            } else
                goalKCALEaten.setText("0");

            PieChart.Data pcd1 = new PieChart.Data("eaten", kcal_eaten);
            PieChart.Data pcd2 = new PieChart.Data("remaining", UserSession.getDailyKcal() - kcal_eaten);

            goalKCALBurned.setText("0");

            int remaining = UserSession.getDailyKcal() - kcal_eaten;
            if (remaining > 0) {
                goalKCALGoal.setText(String.valueOf(remaining));
                kcalLeftText.setText("kcal left");
                kcalLeftText.setStyle("-fx-text-fill: #000000;");
                testChart.getData().clear();
                testChart.getData().addAll(pcd1, pcd2);
                pcd1.getNode().setStyle("-fx-pie-color: lightblue;");
                pcd2.getNode().setStyle("-fx-pie-color: rgba(0,0,0,0.90);");
            } else if (remaining < 0) {
                goalKCALGoal.setText("+" + (remaining * (-1)));
                kcalLeftText.setText("kcal over your\ngoal");
                kcalLeftText.setStyle("-fx-text-fill: #a43838; -fx-font-size: 17px;");
                testChart.getData().clear();
                testChart.getData().addAll(pcd1);
                pcd1.getNode().setStyle("-fx-pie-color: #b43838;");
            } else {
                goalKCALGoal.setText(String.valueOf(remaining * (-1)));
                kcalLeftText.setText("Completed");
                kcalLeftText.setStyle("-fx-text-fill: #30ab30;");
                testChart.getData().clear();
                testChart.getData().addAll(pcd1);
                pcd1.getNode().setStyle("-fx-pie-color: #3ec03e;");
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = goalDatePicker.getValue();
        Calendar c = Calendar.getInstance();
        if (oldDate != ""){
            date = LocalDate.parse(oldDate, formatter);
            c.setTime(java.sql.Date.valueOf(date));
        }
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        if (oldDate.equals("") || goalDatePicker.getValue().getDayOfMonth() < date.minusDays(dayOfWeek-1).getDayOfMonth()
        || goalDatePicker.getValue().getDayOfMonth() > date.minusDays(dayOfWeek-7).getDayOfMonth() )
            // means day is already in the showed week
        barchartInit();
    }
    @FXML Text kcalLeftText;
    private void servingChange(Event event) {
        if (Objects.equals(serving.getValue(),("per serving")))
            try {
                serving_value.setText(String.valueOf(scan_barcode(barcodeField.getText()).getJSONObject("product").getFloat("serving_quantity")));
                if (scan_barcode(barcodeField.getText()).getJSONObject("product").getString("serving_size").contains("ml"))
                    serving_metric.setText("ml");
                else
                    serving_metric.setText("grams");
            } catch (IOException e) {
                e.printStackTrace();
            }
        else {
            try {
                serving_value.setText(scan_barcode(barcodeField.getText()).getJSONObject("product").getString("nutrition_data_per").substring(0, scan_barcode(barcodeField.getText()).getJSONObject("product").getString("nutrition_data_per").length() - 1));
                serving_metric.setText("grams");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        srv_vlu = serving_value.getText();
    }
    /**
     *  Aceasta metoda se ocupa de animatia de tranzitie
     */
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
    /**
     *  se curata sesiunea curenta si se schimba scena la interfata de logare
     */
    public void logoutCall() throws IOException {
        UserSession.getInstance();
        Application.changeScene("login.fxml");
    }

    @FXML Pane addTheMealPane;
    /**
     *  Aceasta metoda se ocupa de adaugarea mancarii in baza de date in sectiunea de history, sectiune in format json
     */
    public void addTheMealBtn() throws IOException {
        if (!addTheMealPane.isVisible()) {
            addTheMealPane.setVisible(true);
            choicebox_nutrition_data_per = "per " + scan_barcode(barcodeField.getText()).getJSONObject("product").getString("nutrition_data_per");

            serving.setItems(FXCollections.observableArrayList("per serving", choicebox_nutrition_data_per));
            if (serving_quantity.getText().equals("")) {
                serving.setItems(FXCollections.observableArrayList(choicebox_nutrition_data_per));
                serving.setValue(choicebox_nutrition_data_per);
            }
            else
            serving.setValue("per serving");
        }
        else
        {
            DecimalFormat f = new DecimalFormat(".##");
//             calculam cate kcal am consumat dupa g/ml de produs mancat
            float serving_quantity;
            try {
                serving_quantity = scan_barcode(barcodeField.getText()).getJSONObject("product").getFloat("serving_quantity");
            }catch(JSONException e) {
                serving_quantity = 100;
            }
            float energy_kcal_serving;
            try {
                energy_kcal_serving = scan_barcode(barcodeField.getText()).getJSONObject("product").getJSONObject("nutriments").getFloat("energy-kcal_serving");
            }catch(JSONException e) {
                energy_kcal_serving = scan_barcode(barcodeField.getText()).getJSONObject("product").getJSONObject("nutriments").getFloat("energy-kcal_100g");
            }
            float consumed = Float.parseFloat(serving_value.getText());
            float energy_kcal_consumed =  Float.parseFloat(f.format(consumed * energy_kcal_serving / serving_quantity));

            String date = String.valueOf(datePickerAdd.getValue());
            float weight = Float.parseFloat(f.format(Float.parseFloat(weightFieldAddKG.getText())));
            float height = Float.parseFloat(f.format(Float.parseFloat(heightFieldAddM.getText())));

//          adaugam in baza de date
//            date: {
//                   kcal_consumed: float,
//                   height: float,
//                   weight: float,
//                  }
            try {
                Connection connectDB;
                DatabaseConnection connectNow = new DatabaseConnection();
                connectDB = connectNow.getConnection();
                Statement statement = connectDB.createStatement();

                String verifyLogin = "SELECT json_extract(history, '$."+date+"') AS 'date' " +
                                     "FROM users " +
                                     "WHERE email = '" + UserSession.getEmail() + "';";
                ResultSet queryResult = statement.executeQuery(verifyLogin);
                queryResult.next();
                String getDate = queryResult.getString(1);

                if(getDate != null) {
                    // key day already exists
                    // update value

                    JSONObject getDateJSON = new JSONObject(getDate.substring(1, getDate.length()-1));

                    String updateKcal = "update users set history = JSON_REPLACE(history, '$."+date+"[0].kcal_consumed', "+f.format(getDateJSON.getFloat("kcal_consumed")+energy_kcal_consumed)+") where email='"+UserSession.getEmail()+"';";
                    String updateWeight = "update users set history = JSON_REPLACE(history, '$."+date+"[0].weight', "+weight+") where email='"+UserSession.getEmail()+"';";
                    String updateHeight = "update users set history = JSON_REPLACE(history, '$."+date+"[0].height', "+height+") where email='"+UserSession.getEmail()+"';";
                    statement.executeUpdate(updateKcal);
                    statement.executeUpdate(updateWeight);
                    statement.executeUpdate(updateHeight);
                }
                else {
                    String setQuery = "UPDATE users SET history = JSON_SET(history, '$."+date+"', JSON_ARRAY(JSON_OBJECT('height', "+height+", 'weight', "+weight+", 'kcal_consumed', "+energy_kcal_consumed+"))) where email='"+UserSession.getEmail()+"';";
                    statement.executeUpdate(setQuery);
                }
                // update user table height and weight
                String updateHeight =
                        "UPDATE users " +
                        "SET " +
                        "height = "+height+" " +
                        "WHERE " +
                        "email = '"+UserSession.getEmail()+"';";
                String updateWeight =
                        "UPDATE users " +
                        "SET " +
                        "weight = "+weight+" " +
                        "WHERE " +
                        "email = '"+UserSession.getEmail()+"';";
                statement.executeUpdate(updateHeight);
                statement.executeUpdate(updateWeight);
                // update userSession
                UserSession.setHeight(height);
                UserSession.setWeight(weight);
            }
            catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
            cancelAddMealBtn();
            cancelAddMealBtn();
            barcodeField.setText("");
            getTranslate362();
            changeGoalDetails("");

        }
    }
    @FXML private Pane newKcalGoalPane;
    @FXML private TextField newKcalValue;
    public void changeKCALGoal() {
        newKcalGoalPane.setVisible(!newKcalGoalPane.isVisible());
    }
    @FXML private StackedBarChart barchart;
    @FXML private Label dailyJCAKGoalErrorLabel;
    public void updateKCALGoal() {
        String newValue = (newKcalValue.getText());
        checkNewKCALNotNull(newValue);
    }
    public void serving_plus() {
        serving_value.setText(String.valueOf(Float.parseFloat(serving_value.getText())+Float.parseFloat(srv_vlu)));
        if (Float.parseFloat(serving_value.getText()) < 0)
            serving_value.setText("0");
    }

    public void serving_minus() {
        serving_value.setText(String.valueOf(Float.parseFloat(serving_value.getText())-Float.parseFloat(srv_vlu)));
        if (Float.parseFloat(serving_value.getText()) < 0)
            serving_value.setText("0");
    }

    @FXML
    private DatePicker datePickerAdd;
    @FXML
    private TextField weightFieldAddKG,heightFieldAddM,weightFieldAddLB,heightFieldAddFEET;
    /**
     *  Aceasta metoda updateaza campurile din sectiunea de adaugare a unui aliment
     *  si se afiseaza interfata
     */
    public void addButtonAction() {
        datePickerAdd.setValue(LocalDate.now());
        DecimalFormat f = new DecimalFormat(".##");
        if(plusSection.getTranslateY() == 0) {
            weightFieldAddKG.setText(f.format((UserSession.getWeight())));
            heightFieldAddM.setText(f.format(UserSession.getHeight()));
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
    @FXML private Text burnedSymbol;
    @FXML private Line lineSymbol;
    @FXML private FlowPane eaten, burned, kcalLeft;
    @FXML private Circle testCircle;
    @FXML private Pane updateProfile,KCALGoalPane;
    /**
     *  Aceasta metoda verifica daca am modificat ceva in interfata de setari ale contului
     *  daca se gasesc modificari se introduc in baza de date si se updateaza interfata principala de informatii
     */
    public void updateProfileCall() {
        try {
            Connection connectDB;
            DatabaseConnection connectNow = new DatabaseConnection();
            connectDB = connectNow.getConnection();
            Statement statement = connectDB.createStatement();
            if (!username.getText().equals(UserSession.getUsername())) {
                String update = "update users set username = '"+username.getText()+"' where email='" + UserSession.getEmail() + "';";
                statement.executeUpdate(update);
                UserSession.setUsername(username.getText());
            }
            boolean changes = false;
            if (Integer.parseInt(age.getText()) != UserSession.getAge()) {
                String update = "update users set age = "+Short.parseShort(age.getText())+" where email='" + UserSession.getEmail() + "';";
                statement.executeUpdate(update);
                UserSession.setAge(Short.parseShort(age.getText()));
                changes = true;
            }
            if (!UserSession.getGender().equals(String.valueOf(String.valueOf(genderCheckBox.getValue()).charAt(0)))) {
                String update = "update users set gender = '"+String.valueOf(genderCheckBox.getValue()).charAt(0)+"' where email='" + UserSession.getEmail() + "';";
                statement.executeUpdate(update);
                UserSession.setGender(String.valueOf(String.valueOf(genderCheckBox.getValue()).charAt(0)));
                changes = true;
            }
            if (!(UserSession.getObjective().equals(String.valueOf(String.valueOf(objectiveCheckBox.getValue()).charAt(7))))) {
                String update = "update users set objective = '"+String.valueOf(objectiveCheckBox.getValue()).charAt(7)+"' where email='" + UserSession.getEmail() + "';";
                statement.executeUpdate(update);
                UserSession.setObjective(String.valueOf(String.valueOf(objectiveCheckBox.getValue()).charAt(7)));
                changes = true;
            }
            if (!UserSession.getAMR().equals(String.valueOf(String.valueOf(activityCheckBox.getValue()).charAt(0)))) {
                String update = "update users set AMR = '"+String.valueOf(activityCheckBox.getValue()).charAt(0)+"' where email='" + UserSession.getEmail() + "';";
                statement.executeUpdate(update);
                UserSession.setAMR(String.valueOf(String.valueOf(activityCheckBox.getValue()).charAt(0)));
                changes = true;
            }
            updateButton.setText("Updated!");
            if (changes)
            updateKCALGoalPane.setVisible(true);
            else {
                editProfileCall();
            }
        }
        catch (Exception e) {
            updateButton.setText("Error");
        }
    }
    private float dailyKCALCalculator() {
        double bmr;
        int _age = UserSession.getAge();
        int _heightCM = (int) ( UserSession.getHeight() * 100 );
        float _kg = UserSession.getWeight();
        if (UserSession.getGender().charAt(0) == 'M')
            bmr = 66.47 + (13.75 * _kg) + (5.003 * _heightCM) - (6.755 * _age);
        else
            bmr = 655.1 + (9.563 * _kg) + (1.850 * _heightCM) - (4.676 * _age);
        float daily = 0;

        switch (activityCheckBox.getValue().charAt(0)) {
            case 'S' -> daily = (float) (bmr * 1.2);
            case 'L' -> daily = (float) (bmr * 1.375);
            case 'M' -> daily = (float) (bmr * 1.55);
            case 'A' -> daily = (float) (bmr * 1.725);
            case 'V' -> daily = (float) (bmr * 1.9);
        }

        if (objectiveCheckBox.getValue().charAt(7) == 'g')
            daily += 500;
        else if (objectiveCheckBox.getValue().charAt(7) == 'l')
            daily -= 500;
        return daily;
    }
    public void updateKCALGoalYesBTNCall() {
        updateKCALGoalDB(String.valueOf(dailyKCALCalculator()));
        updateKCALGoalPane.setVisible(false);
        editProfileCall();
    }
    public void updateKCALGoalDB(String newValue) {
        checkNewKCALNotNull(newValue);
    }

    public void checkNewKCALNotNull(String newValue) {
        if (Objects.equals(newValue, "")) dailyJCAKGoalErrorLabel.setVisible(true);
        else {
            dailyJCAKGoalErrorLabel.setVisible(false);
            UserSession.setDailyKcal((int)(Float.parseFloat(newValue)));
            try {
                Connection connectDB;
                DatabaseConnection connectNow = new DatabaseConnection();
                connectDB = connectNow.getConnection();
                Statement statement = connectDB.createStatement();
                String query = "update users set dailyKcal = "+newValue+" where email='"+UserSession.getEmail()+"';";
                statement.executeUpdate(query);
                changeKCALGoal();
                changeGoalDetails("");
            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }
            newKcalValue.setText("");
        }
    }

    public void updateKCALGoalNoBTNCall() {
        updateKCALGoalPane.setVisible(false);
        editProfileCall();
    }
    @FXML Pane updateKCALGoalPane,sbcPane,leftPane,shadow2,shadow1;
    @FXML Button updateButton;
    public void editProfileCall() {
        leftPane.setVisible(!leftPane.isVisible());
        if (leftPane.isVisible())
            KCALGoalPane.setLayoutX(304);
        else
            KCALGoalPane.setLayoutX(204);
        shadow2.setVisible(!shadow2.isVisible());
        shadow1.setVisible(!shadow1.isVisible());
        sbcPane.setVisible(!sbcPane.isVisible());
        updateButton.setText("Update");
        updateProfile.setVisible(!updateProfile.isVisible());
        logout.setVisible(false);
        editProfile.setVisible(false);
        getTranslate362();
        newKcalGoalPane.setVisible(false);
        plusPane.setVisible(!plusPane.isVisible());
        goalDatePicker.setVisible(!goalDatePicker.isVisible());
        todayLabel.setVisible(!todayLabel.isVisible());
        eaten.setVisible(!eaten.isVisible());
        burned.setVisible(!burned.isVisible());
        burnedSymbol.setVisible(!burnedSymbol.isVisible());
        lineSymbol.setVisible(!lineSymbol.isVisible());
        testChart.setVisible(!testChart.isVisible());
        testCircle.setVisible(!testCircle.isVisible());
        kcalLeft.setVisible(!kcalLeft.isVisible());
        bottomSection.setVisible(!bottomSection.isVisible());
        if(goalDatePicker.isVisible()) {
            editProfile.setText("Edit profile");
            KCALGoalPane.setPrefHeight(270);
        }
        else {
            editProfile.setText("Main menu");
            KCALGoalPane.setPrefHeight(290);
        }
        age.setText(String.valueOf(UserSession.getAge()));
        username.setText(String.valueOf(UserSession.getUsername()));
        username.setFocusTraversable(false);
//        System.out.println(UserSession.getGender());
        switch (UserSession.getGender()) {
            case "M" -> genderCheckBox.getSelectionModel().select(0);
            case "F" -> genderCheckBox.getSelectionModel().select(1);
            case "O" -> genderCheckBox.getSelectionModel().select(2);
            case "I" -> genderCheckBox.getSelectionModel().select(3);
        }

        switch (UserSession.getObjective()) {
            case "l" -> objectiveCheckBox.getSelectionModel().select(0);
            case "g" -> objectiveCheckBox.getSelectionModel().select(1);
            case "m" -> objectiveCheckBox.getSelectionModel().select(2);
        }

        switch (UserSession.getAMR()) {
            case "S" -> activityCheckBox.getSelectionModel().select(0);
            case "L" -> activityCheckBox.getSelectionModel().select(1);
            case "M" -> activityCheckBox.getSelectionModel().select(2);
            case "A" -> activityCheckBox.getSelectionModel().select(3);
            case "V" -> activityCheckBox.getSelectionModel().select(4);
        }
    }
    @FXML ChoiceBox<String> genderCheckBox, objectiveCheckBox, activityCheckBox;
    @FXML TextField age, username;
    @FXML Pane addMealPane;
    public void addMealBtn() {
            addMealPaneTransition.setDuration(Duration.millis(200));
            addMealPaneTransition.setToY(-362);
            addMealPaneTransition.play();
    }
    public void cancelAddMealBtn() {
        if (addTheMealPane.isVisible())
            addTheMealPane.setVisible(false);
        else {
            if (nutrimentsFacts.isVisible())
                nutrimentsFacts.setVisible(false);
            else {
                addMealPaneTransition.setDuration(Duration.millis(350));
                addMealPaneTransition.setToY(0);
                addMealPaneTransition.play();
                addTheMeal.setDisable(true);
            }
        }
        if (!nutrimentsFacts.isVisible() && !addTheMealPane.isVisible())
            addTheMeal.setDisable(true);
    }
    @FXML Text serving_quantity;
    @FXML Label barcodeLabel, nutrition_data_per, energy_kcal_100g, energy_kcal_serving, product_name_en
    ,saturated_fat_100g, saturated_fat_serving, fat_100g, fat_serving, salt_100g, salt_serving, quantity
    ,sugars_100g, sugars_serving, proteins_100g, proteins_serving, carbohydrates_100g, carbohydrates_serving;
    @FXML GridPane nutrimentsFacts;
    public void checkBarCodeBtn() throws IOException {
        JSONObject scanner = scan_barcode(barcodeField.getText());
        if( scanner.get("status").equals(0)) {
            checkBarCode.setText("ITEM NOT FOUND");
            checkBarCode.setTextFill(Color.RED);
            addTheMeal.setDisable(true);
        }
        else {
            addTheMeal.setDisable(false);
            nutrimentsFacts.setVisible(true);
            try {
                serving_quantity.setText(scanner.getJSONObject("product").getString("serving_size"));
            } catch(JSONException e) {
                serving_quantity.setText("");
            }
            nutrition_data_per.setText(scanner.getJSONObject("product").getString("nutrition_data_per"));

            product_name_en.setText(scanner.getJSONObject("product").getString("product_name"));
            quantity.setText(scanner.getJSONObject("product").getString("quantity"));

            energy_kcal_100g.setText(scanner.getJSONObject("product").getJSONObject("nutriments").getFloat("energy-kcal_100g")+"kcal");
            try {
                energy_kcal_serving.setText(scanner.getJSONObject("product").getJSONObject("nutriments").getFloat("energy-kcal_serving") + "kcal");
            }catch(JSONException e) {
            energy_kcal_serving.setText("");
            }

            saturated_fat_100g.setText(scanner.getJSONObject("product").getJSONObject("nutriments").getFloat("saturated-fat_100g") +"g");
            try{
            saturated_fat_serving.setText(scanner.getJSONObject("product").getJSONObject("nutriments").getFloat("saturated-fat_serving")+"g");
            }catch(JSONException e) {
                energy_kcal_serving.setText("");
            }

            fat_100g.setText(scanner.getJSONObject("product").getJSONObject("nutriments").getFloat("fat_100g")+"g");
            try {
            fat_serving.setText(scanner.getJSONObject("product").getJSONObject("nutriments").getFloat("fat_serving")+"g");
            }catch(JSONException e) {
                energy_kcal_serving.setText("");
            }

            salt_100g.setText(scanner.getJSONObject("product").getJSONObject("nutriments").getFloat("salt_100g")+"g");
            try {
            salt_serving.setText(scanner.getJSONObject("product").getJSONObject("nutriments").getFloat("salt_serving")+"g");
            }catch(JSONException e) {
                energy_kcal_serving.setText("");
            }

            sugars_100g.setText(scanner.getJSONObject("product").getJSONObject("nutriments").getFloat("sugars_100g")+"g");
            try {
            sugars_serving.setText(scanner.getJSONObject("product").getJSONObject("nutriments").getFloat("sugars_serving")+"g");
            }catch(JSONException e) {
                energy_kcal_serving.setText("");
            }

            proteins_100g.setText(scanner.getJSONObject("product").getJSONObject("nutriments").getFloat("proteins_100g")+"g");
            try {
            proteins_serving.setText(scanner.getJSONObject("product").getJSONObject("nutriments").getFloat("proteins_serving")+"g");
            }catch(JSONException e) {
                energy_kcal_serving.setText("");
            }

            carbohydrates_100g.setText(scanner.getJSONObject("product").getJSONObject("nutriments").getFloat("carbohydrates_100g")+"g");
            try{
            carbohydrates_serving.setText(scanner.getJSONObject("product").getJSONObject("nutriments").getFloat("carbohydrates_serving")+"g");
            }catch(JSONException e) {
                energy_kcal_serving.setText("");
            }
        }
    }
}
