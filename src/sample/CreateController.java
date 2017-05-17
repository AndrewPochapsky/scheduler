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
    Text validTitleText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validTitleText.setVisible(false);
    }

    public void handleCreate(ActionEvent event)throws IOException{
        //TODO there must be a way to improve this code
        //maybe refactor into one isValidInput method
        if(!titleInput.getText().trim().equals("")){
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
                validTitleText.setText("*Title cannot contain '.'*");
                validTitleText.setVisible(true);
            }
        }
        else{
            validTitleText.setText("*Please enter a name*");
            validTitleText.setVisible(true);
        }

    }
    private void initializeScheduler(ActionEvent event)throws IOException{
        Scheduler scheduler=new Scheduler(titleInput.getText());
        FileHandler.save(scheduler);
        ProgramController.setCurrentScheduler(scheduler);
        titleInput.setText("");
        /*
        Utility utility = new Utility();
        utility.loadScene("main", 1200, 800, event, true, true, true);
        Main.getMainStage().close();*/


    }

    public void handleBack(ActionEvent event)throws IOException{
        Utility utility = new Utility();
        utility.loadScene("teacher", 600, 400, event, false, false, false, false);
    }


}




