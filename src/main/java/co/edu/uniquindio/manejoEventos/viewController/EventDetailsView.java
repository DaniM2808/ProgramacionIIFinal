package co.edu.uniquindio.manejoEventos.viewController;

import co.edu.uniquindio.manejoEventos.model.EventManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

import static co.edu.uniquindio.manejoEventos.viewController.MainView.switchMenu;

public class EventDetailsView implements Initializable {
    @FXML
    private Label eventNameText;
    @FXML
    private Label descEventText;
    @FXML
    private Label placeText;
    @FXML
    private Label eventPricesText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        eventNameText.setText(EventManager.getInstance().getCurrentSelectedEvent().getName());
        descEventText.setText(EventManager.getInstance().getCurrentSelectedEvent().getDescription());
        placeText.setText(EventManager.getInstance().getCurrentSelectedEvent().getThePlace().getName());
        eventPricesText.setText(EventManager.getInstance().getCurrentSelectedEvent().getEventPrices());
    }



    @FXML
    public void backAction(ActionEvent event){
        switchMenu(event, "mainMenu.fxml");
    }
}
