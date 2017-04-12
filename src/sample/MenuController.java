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


    public void handleNew()throws IOException{
        Utility utility = new Utility();
        utility.loadScene("create", 445, 375, null, false, false, true);
    }

    public void handleOpen() throws IOException{
        Utility utility = new Utility();
        utility.loadScene("open", 445, 375, null, false, false, true);

    }

    public void handleExit(){
        Platform.exit();
    }



}
