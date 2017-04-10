package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateController implements Initializable{

    @FXML
    TextField titleInput, columnInput;
    @FXML
    TextArea descriptionInput;
    @FXML
    Text validText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validText.setVisible(false);
    }


    public void handleCreate(){
        //close both windows
        //create new Scheduler using user inputed values
        Scheduler scheduler = new Scheduler(titleInput.getText(), descriptionInput.getText());
        if(Utility.isInteger(columnInput.getText())){
            int columnNum = Integer.parseInt(columnInput.getText());
            for (int i = 0; i <= columnNum;i++){
                scheduler.addEmptyRow();
            }
        }
        else{
            //ask for valid input
            validText.setVisible(true);
            System.out.println("Please enter a number");
        }




    }



}
