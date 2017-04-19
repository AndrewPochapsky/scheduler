package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
    VBox vbox;



    @Override
    public void initialize(URL location, ResourceBundle resources){

        Scheduler currentScheduler = ProgramController.getCurrentScheduler();

        nameDisplay.setText("Title: "+currentScheduler.getTitle());
        descriptionDisplay.setText("Description: "+currentScheduler.getDescription());
        amountOfRowsDisplay.setText("Amount of rows: "+ String.valueOf(currentScheduler.getRows().size()));

        for(TableRow row: currentScheduler.getRows()){
            if(row.getRow()==null){
                row.setUpRow();
                vbox.getChildren().add(row.getRow());
            }
        }
        for(TableRow row: currentScheduler.getRows()){
            for(Element element: row.getElements()){
                ImageView view = element.getImageView();

                view.setOnDragOver(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        if(event.getDragboard().hasFiles()){
                            event.acceptTransferModes(TransferMode.ANY);
                        }
                    }
                });
                view.setOnDragDropped(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        List<File> files = event.getDragboard().getFiles();
                        try{
                            Image img = new Image(new FileInputStream(files.get(0)));
                            view.setImage(img);
                           // element.setImage(img);
                        }
                        catch(IOException e){
                            e.printStackTrace();
                        }

                    }
                });
            }
        }
    }

    public void handleExit() throws IOException{
        System.out.println("Closing");
        FileHandler.save(ProgramController.getCurrentScheduler());
    }


}
