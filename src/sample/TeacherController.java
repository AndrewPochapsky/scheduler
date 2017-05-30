package sample;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TeacherController implements Initializable{

    Utility utility = new Utility();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void handleAddStudent(ActionEvent event) throws IOException{
        utility.loadScene("create", 600, 400, event, false, false, false, false);
    }

    public void handleRemoveStudent(ActionEvent event)throws IOException{
        utility.loadScene("remove", 600, 400, event, false, false, false, false);
    }



    public void handleBack(ActionEvent event)throws IOException{
        utility.loadScene("menu", 600, 400, event, false, false, false, false);
    }



}
