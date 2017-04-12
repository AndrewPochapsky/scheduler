package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateController implements Initializable{

    @FXML
    TextField titleInput, rowInput;
    @FXML
    TextArea descriptionInput;
    @FXML
    Text validText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validText.setVisible(false);
    }

    public void handleCreate(ActionEvent event)throws IOException{
        //close both windows
        //create new Scheduler using user inputed values
        Scheduler scheduler = new Scheduler(titleInput.getText(), descriptionInput.getText());
        if(Utility.isInteger(rowInput.getText())){
            int columnNum = Integer.parseInt(rowInput.getText());
            int count = 0;
            for (int i = 1; i <= columnNum;i++){
                scheduler.addEmptyRow();
                count++;
            }
            System.out.println("adding "+ count+" rows");
            FileHandler.save(scheduler);
            ProgramController.setCurrentScheduler(scheduler);

            Utility utility = new Utility();
            utility.loadScene("main", 1200, 800, event, true, true, false);
            Main.getMainStage().close();

        }
        else{
            //ask for valid input
            validText.setVisible(true);
            System.out.println("Please enter a number");
        }




    }



}
