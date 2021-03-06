package main;

import controller.Engine;
import controller.Transmission;
import controller.UserInput;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Dao;
import pojo.Car;
import view.CarView;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wojciech on 06.06.17.
 */
public class main extends Application {

    @FXML
    TextField price;

    @FXML
    TextField age;

    @FXML
    TextField hp;

    @FXML
    private Button search;

    @FXML
    private RadioButton petrol;

    @FXML
    private RadioButton diesel;

    @FXML
    private RadioButton engine;

    @FXML
    private RadioButton manual;

    @FXML
    private RadioButton automatic;

    @FXML
    private RadioButton transmission;

    @FXML
    private ChoiceBox<String> type;

    @FXML
    private TableView tableView;

    private final Dao dao;
    private CarView carView;

    public main() {
        this.dao = new Dao();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Wyszukiwarka pojazdów");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(
                    "main.fxml"));
            fxmlLoader.setController(this);
            Parent root = fxmlLoader.load();

            stage.setScene(new Scene(root, 1120, 600));

            stage.show();

            loadControls();
            try {
                loadTableView(new UserInput(10000, 10, 90, Engine.NONE, Transmission.NONE, null));
            }catch(Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Brak pliku cars.xml!");
                alert.setContentText("Brak pliku cars.xml!");

                alert.showAndWait();
            }
            
        } catch (Exception e) {
            System.out.println("start" + e);
            e.printStackTrace();
        }
    }


    private void loadControls(){
        ToggleGroup group = new ToggleGroup();
        petrol.setToggleGroup(group);
        diesel.setToggleGroup(group);
        engine.setToggleGroup(group);
        engine.setSelected(true);
        ToggleGroup group2 = new ToggleGroup();
        automatic.setToggleGroup(group2);
        manual.setToggleGroup(group2);
        transmission.setToggleGroup(group2);
        transmission.setSelected(true);
        type.setItems(FXCollections.observableArrayList(Car.ALL_TYPES, "hatchback", "sedan", "kombi", "suv", "coupe", "minivan", "pick-up", "kabriolet", "terenowy"));
        type.getSelectionModel().selectFirst();
        search.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //ZMIENNE:
                Engine engine = Engine.NONE;
                if(diesel.isSelected()){
                    engine = Engine.DIESEL;
                } else if(petrol.isSelected()){
                    engine = Engine.PETROL;
                }
                Transmission transmission = Transmission.NONE;
                if(automatic.isSelected()){
                    transmission = Transmission.AUTOMATIC;
                } else if(manual.isSelected()){
                    transmission = Transmission.MANUAL;
                }

                int selectedPrice;
                try {
                    selectedPrice = Integer.valueOf(price.getText());
                }catch(NumberFormatException e){
                    selectedPrice = -99999;
                }
                int selectedAge;
                try {
                    selectedAge = Integer.valueOf(age.getText());
                }catch(NumberFormatException e){
                    selectedAge = -99999;
                }
                int selectedHp;
                try {
                    selectedHp = Integer.valueOf(hp.getText());
                }catch(NumberFormatException e){
                    selectedHp = -99999;
                }

                UserInput input = new UserInput(selectedPrice, selectedAge, selectedHp, engine, transmission, type.getValue());
                loadTableView(input);
            }
        });
    }

    private void loadTableView(UserInput input) {
        List<Car> cars = dao.find(input);

        List<CarView> carViewList = new ArrayList<>();
        for(Car c: cars){
            carViewList.add(new CarView(c));
        }

        ObservableList<CarView> list = FXCollections.observableArrayList(carViewList);
        tableView.setItems(list);

    }
    
}
