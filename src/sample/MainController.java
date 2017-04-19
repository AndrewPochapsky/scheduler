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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable{
    @FXML
    Text nameDisplay, descriptionDisplay, amountOfRowsDisplay;

    @FXML
    VBox vbox;


    private final File defaultImgFile = new File("src/question-mark.jpg");
    @Override
    public void initialize(URL location, ResourceBundle resources){

        Scheduler currentScheduler = ProgramController.getCurrentScheduler();

        nameDisplay.setText("Title: "+currentScheduler.getTitle());
        descriptionDisplay.setText("Description: "+currentScheduler.getDescription());
        amountOfRowsDisplay.setText("Amount of rows: "+ String.valueOf(currentScheduler.getRows().size()));

        for(TableRow row: currentScheduler.getRows()){
            //TODO allow more than one imageview
            ImageView view = new ImageView();
            view.setFitHeight(100);
            view.setFitWidth(100);
            HBox hbox = new HBox(view);
            vbox.getChildren().add(hbox);
            System.out.println("looping");
        }//Element element: row.getElements()
        System.out.println("size of vbox children: "+vbox.getChildren().size());
        for(int i = 0;i < vbox.getChildren().size(); i++){
            HBox box = (HBox)vbox.getChildren().get(i);
            ImageView view = (ImageView)box.getChildren().get(0);

            try{
                view.setImage(new Image(new FileInputStream(defaultImgFile)));
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
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }



        }
    }

    public void handleExit() throws IOException{
        System.out.println("Closing");
        FileHandler.save(ProgramController.getCurrentScheduler());
    }


}
