package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RemoveController implements Initializable{

    @FXML
    ListView<String> fileList;

    Utility utility = new Utility();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File folder = new File("schedulers");
        File[] filesArr=folder.listFiles();

        ObservableList<String> listOfFiles = FXCollections.observableArrayList(Utility.fileNames(filesArr));
        fileList.setItems(listOfFiles);
    }

    public void handleRemove() throws IOException{
        AlertBox box = new AlertBox("Confirmation","Are you sure?");

        String s = fileList.getSelectionModel().getSelectedItem();
        if(s!=null){
            box.display();
            if(box.isYes()){
                File fileToRemove = new File("schedulers/"+s);
                FileUtils.deleteDirectory(fileToRemove);

                File folder = new File("schedulers");
                File[] filesArr=folder.listFiles();

                ObservableList<String> listOfFiles = FXCollections.observableArrayList(Utility.fileNames(filesArr));
                fileList.setItems(listOfFiles);
            }

        }

    }

    public void handleEditGallery()throws IOException, ClassNotFoundException{
        if(fileList.getSelectionModel().getSelectedItem()!=null) {
            String name = fileList.getSelectionModel().getSelectedItem();
            FileHandler.load(name);
            //load gallery scene
            utility.loadScene("gallery", 600, 500, null, false, false, true, true);
        }
    }


    public void handleBack(ActionEvent event)throws IOException {

        utility.loadScene("teacher", 600, 400, event, false, false, false, false);
    }


}
