package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable{
    @FXML
    Text nameDisplay, descriptionDisplay, amountOfRowsDisplay;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Scheduler currentScheduler = ProgramController.getCurrentScheduler();
        nameDisplay.setText("Title: "+currentScheduler.getTitle());
        descriptionDisplay.setText("Description: "+currentScheduler.getDescription());
        amountOfRowsDisplay.setText("Amount of rows: "+ String.valueOf(currentScheduler.getRows().size()));
    }
}
