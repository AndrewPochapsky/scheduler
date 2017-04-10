package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {


    public void handleNew(ActionEvent event)throws IOException{
        Parent parent = FXMLLoader.load(getClass().getResource("create.fxml"));
        Scene scene = new Scene(parent, 445, 375);
        Stage stage = new Stage();
        stage.setTitle("Create");

        stage.setScene(scene);
        //stage.setMaximized(false);
        stage.setResizable(false);
        stage.show();
    }

    public void handleOpen(){

    }

    public void handleExit(){

    }



}
