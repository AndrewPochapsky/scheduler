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


    private final File defaultImgFile = new File("src/question-mark.jpg");
    @Override
    public void initialize(URL location, ResourceBundle resources){

        Scheduler currentScheduler = ProgramController.getCurrentScheduler();

        for(String path: ProgramController.getCurrentScheduler().getGalleryInfo().getImagePaths()){
            System.out.println(path);
        }

        nameDisplay.setText("Title: "+currentScheduler.getTitle());
        descriptionDisplay.setText("Description: "+currentScheduler.getDescription());
        amountOfRowsDisplay.setText("Amount of rows: "+ String.valueOf(currentScheduler.getRows().size()));
        for(TableRow row: ProgramController.getCurrentScheduler().getRows()){

            handleAddRow(row);

        }
        initializeRows();

    }

    public void handleAddRow(TableRow row){
        //System.out.println("adding row");
        int numOfImages = 4;
        //ProgramController.getCurrentScheduler().addEmptyRow();


        Text title = new Text(row.getTitle());
        List<ImageView> views = new ArrayList<ImageView>();

        for(int i = 0; i < numOfImages; i++){
            views.add(new ImageView());
            //System.out.println("Adding imageview");
        }

        for(ImageView view : views){

            view.setFitHeight(100);
            view.setFitWidth(100);
            view.setPreserveRatio(true);

        }

        HBox rowToAdd = new HBox(title);
        rowToAdd.getChildren().addAll(views);
        vbox.getChildren().add(rowToAdd);

       // initializeRows();
    }

    public void handleExit() throws IOException{
        System.out.println("Closing");
        FileHandler.save(ProgramController.getCurrentScheduler());
        Platform.exit();
    }

    private void initializeRows(){
       //Element element: row.getElements()
        //System.out.println("size of vbox children: "+vbox.getChildren().size());
        for(int i = 0;i < vbox.getChildren().size(); i++){
            HBox box = (HBox)vbox.getChildren().get(i);
            for(Node node: box.getChildren()){
                if(node instanceof ImageView){
                    ImageView view = (ImageView)node;
                    view.setPreserveRatio(true);
                    if(view.getImage()==null){
                        try{
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
                                    //List<File> files = event.getDragboard().getFiles();
                                    Dragboard db = event.getDragboard();
                                    //Image img = new Image(new FileInputStream(files.get(0)));
                                    //System.out.println("Detecting Drop");
                                    if(db.hasImage())
                                        //System.out.println("Detecting image");
                                        view.setImage(db.getImage());
                                    // element.setImage(img);


                                }
                            });

                        }catch(FileNotFoundException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public void handleOpenGallery(ActionEvent event) throws IOException{
        System.out.println("Opening Gallery");
        Utility utility = new Utility();
        utility.loadScene("gallery", 700, 400, event, false, false, true);
    }



}
