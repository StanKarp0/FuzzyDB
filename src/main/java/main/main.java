package main;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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
    private RadioButton manual;

    @FXML
    private RadioButton automatic;

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

            stage.setScene(new Scene(root, 800, 600));

            stage.show();
            System.out.println(price);
            loadControls();
        } catch (Exception e) {
            System.out.println("start" + e);
            e.printStackTrace();
        }
    }


    private void loadControls(){
        ToggleGroup group = new ToggleGroup();
        petrol.setToggleGroup(group);
        diesel.setToggleGroup(group);
        ToggleGroup group2 = new ToggleGroup();
        automatic.setToggleGroup(group2);
        manual.setToggleGroup(group2);
        search.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //ZMIENNE:
                int isDiesel;
                if(diesel.isSelected()){
                    isDiesel = 1;
                } else if(petrol.isSelected()){
                    isDiesel = 0;
                }else{
                    isDiesel = -1;
                }
                int isAutomatic;
                if(automatic.isSelected()){
                    isAutomatic = 1;
                } else if(automatic.isSelected()){
                    isAutomatic = 0;
                }else{
                    isAutomatic = -1;
                }
                int selectedPrice;
                try {
                    selectedPrice = Integer.valueOf(price.getText());
                }catch(NumberFormatException e){
                    selectedPrice = -1;
                }
                int selectedAge;
                try {
                    selectedAge = Integer.valueOf(age.getText());
                }catch(NumberFormatException e){
                    selectedAge = -1;
                }
                int selectedHp;
                try {
                    selectedHp = Integer.valueOf(hp.getText());
                }catch(NumberFormatException e){
                    selectedHp = -1;
                }
                System.out.println("wcisnieto" + selectedPrice + ", " + selectedAge + " , " + selectedHp + " , " + isDiesel + " , " + isAutomatic);
                //TODO
                //TUTAJ INICJALIZACJA WYSZUKIWANIA ROZMYTEGO ORAZ TUTAJ BEDZIE WYWOLANA FUNKCJA WYPISUJACA WYNIK
            }
        });
    }
}
