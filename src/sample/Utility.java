package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public static List<String> fileNames(File[] filesArr){
        List<String> list = new ArrayList<>();
        for(File file: filesArr){
            boolean remove = false;
            String newName = "";
            char[] name = file.getName().toCharArray();
            for(char letter:name){
                if(!remove && letter !='.'){
                    newName+=letter;
                }
                if(letter=='.'){
                    remove = true;
                }
            }
            list.add(newName);
        }
        return list;
    }

    public static boolean fileExists(String input){
        if(input.trim().equals("")){
            return true;
        }
        boolean exists = false;
        File folder = new File("schedulers");
        File[] filesArr=folder.listFiles();
        List<String> names = fileNames(filesArr);
        for(String name:names){
            //System.out.println("name: "+name);
            //System.out.println("input: "+input);
            if(input.equals(name)){
                exists = true;
                break;
            }
        }
        //System.out.println("Exists: "+exists);
        return exists;
    }

}
