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



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File folder = new File("schedulers");
        File[] filesArr=folder.listFiles();

        ObservableList<String> listOfFiles = FXCollections.observableArrayList(Utility.fileNames(filesArr));
        fileList.setItems(listOfFiles);
    }

    public void handleRemove() throws IOException{

        String s = fileList.getSelectionModel().getSelectedItem();
        if(s!=null){
            File fileToRemove = new File("schedulers/"+s);
            FileUtils.deleteDirectory(fileToRemove);

            File folder = new File("schedulers");
            File[] filesArr=folder.listFiles();

            ObservableList<String> listOfFiles = FXCollections.observableArrayList(Utility.fileNames(filesArr));
            fileList.setItems(listOfFiles);
        }

    }

    public void handleBack(ActionEvent event)throws IOException {
        Utility utility = new Utility();
        utility.loadScene("teacher", 600, 400, event, false, false, false);
    }


}
