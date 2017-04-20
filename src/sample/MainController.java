package sample;

import javafx.event.ActionEvent;
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
        for(TableRow row: ProgramController.getCurrentScheduler().getRows()){
            //TODO allow more than one imageview
            Text title = new Text(row.getTitle());
            ImageView view = new ImageView();
            ImageView secondView = new ImageView();
            secondView.setFitHeight(100);
            secondView.setFitWidth(100);
            view.setFitHeight(100);
            view.setFitWidth(100);
            HBox hbox = new HBox(title, view, secondView);
            vbox.getChildren().add(hbox);
        }
        initializeRows();

    }

    public void handleAddRow(){
        System.out.println("adding row");
        ProgramController.getCurrentScheduler().addEmptyRow();
        TableRow row = new TableRow("This is a row");

        Text title = new Text(row.getTitle());

        ImageView view = new ImageView();
        ImageView secondView = new ImageView();
        secondView.setFitHeight(100);
        secondView.setFitWidth(100);
        view.setFitHeight(100);
        view.setFitWidth(100);

        view.setPreserveRatio(true);
        secondView.setPreserveRatio(true);

        HBox rowToAdd = new HBox(title, view, secondView);
        vbox.getChildren().add(rowToAdd);

        initializeRows();
    }

    public void handleExit() throws IOException{
        System.out.println("Closing");
        FileHandler.save(ProgramController.getCurrentScheduler());
    }

    private void initializeRows(){
       //Element element: row.getElements()
        System.out.println("size of vbox children: "+vbox.getChildren().size());
        for(int i = 0;i < vbox.getChildren().size(); i++){
            HBox box = (HBox)vbox.getChildren().get(i);
            for(Node node: box.getChildren()){
                if(node instanceof ImageView){
                    ImageView view = (ImageView)node;
                    if(view.getImage()==null){
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
            }


        }
    }

    public void handleOpenGallery(ActionEvent event) throws IOException{
        System.out.println("Opening Gallery");
        Utility utility = new Utility();
        utility.loadScene("gallery", 700, 400, event, false, false, true);
    }



}
