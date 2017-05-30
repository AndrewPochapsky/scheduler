package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utility {

    public void loadScene(String name, double width, double height, ActionEvent event, boolean isMaximized, boolean isResizable, boolean newWindow, boolean isUndecorated)throws IOException{
        Parent parent = FXMLLoader.load(getClass().getResource(name+".fxml"));
        Scene scene = new Scene(parent, width, height);
        Stage stage;
        if(newWindow){
             stage = new Stage();
        }
        else{
            stage= (Stage)((Node)event.getSource()).getScene().getWindow();
        }
        if(isUndecorated){
            stage.initStyle(StageStyle.UNDECORATED);
        }
        stage.setScene(scene);
        stage.setTitle(name);
        stage.setMaximized(isMaximized);
        stage.setResizable(isResizable);
        stage.show();
    }

    public static List<String> fileNames(File[] filesArr){
        List<String> list = new ArrayList<>();
        for(File file: filesArr){
            String newName = "";
            char[] name = file.getName().toCharArray();
            for(char letter:name){
                if(letter=='.')
                    break;
                newName+=letter;
            }
            list.add(newName);
        }
        return list;
    }

    public static boolean fileExists(String input){
        if(input.trim().equals("") && fileExists("Schedule")){
            return true;
        }
        input = input.toLowerCase();
        boolean exists = false;
        File folder = new File("schedulers");
        File[] filesArr=folder.listFiles();
        List<String> names = fileNames(filesArr);
        for(String name:names){
            if(input.equals(name.toLowerCase())){
                exists = true;
                break;
            }
        }
        return exists;
    }

    public static boolean containsLetter(String string,char letter){
        char[] arr = string.toCharArray();
        for (char character: arr){
            if(character==letter){
                return true;
            }
        }
        return false;
    }

}
