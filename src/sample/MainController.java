package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable{
    @FXML
    Text nameDisplay, descriptionDisplay, amountOfRowsDisplay;

    @FXML
    ImageView imageView;



    @Override
    public void initialize(URL location, ResourceBundle resources){
        Scheduler currentScheduler = ProgramController.getCurrentScheduler();
        nameDisplay.setText("Title: "+currentScheduler.getTitle());
        descriptionDisplay.setText("Description: "+currentScheduler.getDescription());
        amountOfRowsDisplay.setText("Amount of rows: "+ String.valueOf(currentScheduler.getRows().size()));

        File defaultImageFile = new File("src/question-mark.jpg");
        try{
            Image defaultImage = new Image(new FileInputStream(defaultImageFile));
            imageView.setImage(defaultImage);
        }catch(IOException e){
            System.out.println(e.toString());
        }

    }

    public void handleDragOver(DragEvent event){
        if(event.getDragboard().hasFiles()){
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    public void handleDrop(DragEvent event) throws IOException{
        List<File> files = event.getDragboard().getFiles();
        Image img = new Image(new FileInputStream(files.get(0)));
        imageView.setImage(img);
    }
}
