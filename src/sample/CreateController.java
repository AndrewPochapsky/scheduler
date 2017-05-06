package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateController implements Initializable{

    @FXML
    TextField titleInput;
    @FXML
    TextArea descriptionInput;
    @FXML
    Text validTitleText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validTitleText.setVisible(false);
    }

    public void handleCreate(ActionEvent event)throws IOException{
        //TODO there must be a way to improve this code
        //maybe refactor into one isValidInput method
        if(!Utility.containsLetter(titleInput.getText(), '.')){
            validTitleText.setVisible(false);
            if(!Utility.fileExists(titleInput.getText())){
                initializeScheduler(event);
            }
            else {
                System.out.println("File already exists");
                AlertBox box = new AlertBox("Confirm", "File already exists;\noverride?");
                box.display();
                if(box.isYes()){
                    System.out.println("overriding file");
                    initializeScheduler(event);
                }
            }

        }
        else{
            System.out.println("title has a period");
            validTitleText.setVisible(true);
        }
    }
    private void initializeScheduler(ActionEvent event)throws IOException{
        Scheduler scheduler=new Scheduler(titleInput.getText(), descriptionInput.getText());
        FileHandler.save(scheduler);
        ProgramController.setCurrentScheduler(scheduler);

        Utility utility = new Utility();
        utility.loadScene("main", 1200, 800, event, true, true, false);
        Main.getMainStage().close();


    }

}




