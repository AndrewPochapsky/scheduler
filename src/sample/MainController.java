package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable{
    @FXML
    Text nameDisplay, descriptionDisplay, amountOfRowsDisplay;

    @FXML
    VBox vbox;

    //TODO consider moving this to program controller class as it will be used in other controllers probably
    private final File defaultImgFile = new File("src/question-mark.jpg");
    @Override
    public void initialize(URL location, ResourceBundle resources){

        Scheduler currentScheduler = ProgramController.getCurrentScheduler();

        for(String path: ProgramController.getCurrentScheduler().getGalleryInfo().getImagePaths()){
            System.out.println(path);
        }

        nameDisplay.setText("Title: "+currentScheduler.getTitle());
        descriptionDisplay.setText("Description: "+currentScheduler.getDescription());
        /*for(TableRow row: ProgramController.getCurrentScheduler().getRows()){

            handleAddRow(row);

        }*/
        try{
            initializeRows();
        }catch(IOException e){
            e.printStackTrace();
        }


    }

    public void handleExit() throws IOException{
        System.out.println("Closing");
        FileHandler.save(ProgramController.getCurrentScheduler());
        Platform.exit();
    }

    private void initializeRows() throws IOException{
        List<ImageView> views = new ArrayList<>();
        for(Node box: vbox.getChildren()){
            if(box instanceof HBox){
                HBox hbox = (HBox)box;
                for(Node node: hbox.getChildren()){
                    if(node instanceof ImageView){
                        ImageView view = (ImageView)node;
                        views.add(view);
                    }
                }

            }

        }
        for(ImageView view: views){
            //TODO set up saving the images
            view.setImage(new Image(new FileInputStream(defaultImgFile)));

            view.setOnDragOver(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    if (event.getGestureSource() != view &&
                            event.getDragboard().hasImage()) {
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                    }
                    event.consume();

                }
            });

            view.setOnDragDropped(new EventHandler<DragEvent>() {
                @Override
                public void handle(DragEvent event) {
                    Dragboard db = event.getDragboard();
                    if(db.hasImage())
                        view.setImage(db.getImage());
                }
            });
        }
    }

    public void handleOpenGallery(ActionEvent event) throws IOException{
        System.out.println("Opening Gallery");
        Utility utility = new Utility();
        utility.loadScene("gallery", 700, 400, event, false, false, true);
    }






}
