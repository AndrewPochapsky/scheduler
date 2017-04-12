package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Utility {


    public static boolean isInteger(String s){
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    public void loadScene(String name, double width, double height, ActionEvent event, boolean isMaximized, boolean isResizable, boolean newWindow)throws IOException{
        if(newWindow){
            Parent parent = FXMLLoader.load(getClass().getResource(name+".fxml"));
            Scene scene = new Scene(parent, width, height);
            Stage stage = new Stage();
            stage.setTitle(name);
            stage.setScene(scene);
            stage.setMaximized(isMaximized);
            stage.setResizable(isResizable);
            stage.show();
        }
        else{
            Parent parent = FXMLLoader.load(getClass().getResource(name+".fxml"));
            Scene scene = new Scene(parent, width, height);
            Stage stage= (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle(name);
            stage.setMaximized(isMaximized);
            stage.setResizable(isResizable);
            stage.show();
        }

    }


}
