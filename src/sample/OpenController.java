package sample;

import com.sun.deploy.util.ArrayUtil;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class OpenController implements Initializable{
    @FXML
    ListView<String> fileList;
    @FXML
    Button closeButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        File folder = new File("schedulers");
        File[] filesArr=folder.listFiles();

        ObservableList<String> listOfFiles = FXCollections.observableArrayList(Utility.fileNames(filesArr));
        fileList.setItems(listOfFiles);

    }

    public void handleOpen(ActionEvent event) throws ClassNotFoundException, IOException{
        FileHandler.load(fileList.getSelectionModel().getSelectedItem());

        Utility utility = new Utility();
        utility.loadScene("main", 1200, 800, event, true, true, false);
        Main.getMainStage().close();

        //System.out.println(fileList.getSelectionModel().getSelectedItem());
    }

    public void handleClose(){
        Stage stage =(Stage)closeButton.getScene().getWindow();
        stage.close();
    }



}
