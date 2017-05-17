package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MenuController {


    public void handleTeacher(ActionEvent event)throws IOException{
        Utility utility = new Utility();
        utility.loadScene("teacher", 600, 400, event, false, false, false, false);
    }

    public void handleStudent(ActionEvent event) throws IOException{
        Utility utility = new Utility();
        utility.loadScene("open", 600, 400, event, false, false, false, false);

    }

    public void handleExit(){
        Platform.exit();
    }



}
