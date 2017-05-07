package sample;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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

    @FXML
    ImageView lunch;

    //TODO consider moving this to program controller class as it will be used in other controllers probably
    //private final File defaultImgFile = new File("src/question-mark.jpg");
    @Override
    public void initialize(URL location, ResourceBundle resources){

        for(Element e: ProgramController.getCurrentScheduler().getElements())
            System.out.println(e.getFileName());

        Scheduler currentScheduler = ProgramController.getCurrentScheduler();

        for(String path: ProgramController.getCurrentScheduler().getGalleryInfo().getImagePaths()){
            //System.out.println(path);
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
                        //System.out.println("ID: "+view.getId());
                        views.add(view);
                    }
                }

            }

        }
        for(int i = 0; i < views.size(); i++){
            ImageView view = views.get(i);
            //TODO set up saving the images

            if(ProgramController.getCurrentScheduler().getElements().size() <= i){
                ProgramController.getCurrentScheduler().getElements().add(new Element());
            }
            File imgFile = new File(ProgramController.getCurrentScheduler().getElements().get(i).getFileName());
            view.setImage(new Image(new FileInputStream(imgFile)));
            /*
            view.setOnDragOver(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    if (event.getGestureSource() != view &&
                            event.getDragboard().hasImage()) {
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                    }
                    event.consume();

                }
            });*/
            /*
            view.setOnDragDropped(new EventHandler<DragEvent>() {
                @Override
                public void handle(DragEvent event) {
                    Dragboard db = event.getDragboard();
                    if(db.hasImage()){
                        view.setImage(db.getImage());
                        String path = db.getFiles().get(0).getAbsolutePath();
                        //System.out.println("test");
                        String id = view.getId();

                        switch (id){
                            case "p1":
                                ProgramController.getCurrentScheduler().getElements().get(0).setFileName(path);
                                break;
                            case "p2":
                                ProgramController.getCurrentScheduler().getElements().get(1).setFileName(path);
                                break;
                            case "lunch":
                                ProgramController.getCurrentScheduler().getElements().get(2).setFileName(path);
                                break;
                            case "p3":
                                ProgramController.getCurrentScheduler().getElements().get(3).setFileName(path);
                                break;
                            case "p4":
                                ProgramController.getCurrentScheduler().getElements().get(4).setFileName(path);
                                break;
                        }
                       // ProgramController.getCurrentScheduler().getElements().get(0).setFileName(path);
                    }

                }
            });*/
        }
    }

    public void handleOpenGallery(ActionEvent event) throws IOException{
        System.out.println("Opening Gallery");
        Utility utility = new Utility();
        utility.loadScene("gallery", 700, 400, event, false, false, true);
    }

    public void SetOnDragDropped(DragEvent event){
        Dragboard db = event.getDragboard();
        if(db.hasImage()){

            ImageView view = (ImageView)event.getSource();

            view.setImage(db.getImage());

            //System.out.println("shit");
            //System.out.println("test");
            //System.out.println("ID: ");
            String id = view.getId();
            BufferedImage bImage = SwingFXUtils.fromFXImage(view.getImage(), null);
            //TODO create a folder for each scheduler
            File file = new File("schedulers/"+ProgramController.getCurrentScheduler().getTitle()+"/images/"+view.getId());
            try{
                ImageIO.write(bImage, "png", file);

                switch (id){
                    case "p1":
                        System.out.println("ya");
                        ProgramController.getCurrentScheduler().getElements().get(0).setFileName(file.getAbsolutePath());
                        break;
                    case "p2":
                        ProgramController.getCurrentScheduler().getElements().get(1).setFileName(file.getAbsolutePath());
                        break;
                    case "lunch":
                        ProgramController.getCurrentScheduler().getElements().get(2).setFileName(file.getAbsolutePath());
                        break;
                    case "p3":
                        ProgramController.getCurrentScheduler().getElements().get(3).setFileName(file.getAbsolutePath());
                        break;
                    case "p4":
                        ProgramController.getCurrentScheduler().getElements().get(4).setFileName(file.getAbsolutePath());
                        break;
                }
            }catch(IOException e){
                e.printStackTrace();
            }

            /*
            switch (id){
                case "p1":
                    System.out.println("ya");
                    ProgramController.getCurrentScheduler().getElements().get(0).setFileName(path);
                    break;
                case "p2":
                    ProgramController.getCurrentScheduler().getElements().get(1).setFileName(path);
                    break;
                case "lunch":
                    ProgramController.getCurrentScheduler().getElements().get(2).setFileName(path);
                    break;
                case "p3":
                    ProgramController.getCurrentScheduler().getElements().get(3).setFileName(path);
                    break;
                case "p4":
                    ProgramController.getCurrentScheduler().getElements().get(4).setFileName(path);
                    break;
            }*/
            // ProgramController.getCurrentScheduler().getElements().get(0).setFileName(path);
        }
    }

    public void SetOnDragOver(DragEvent event){
        if (event.getGestureSource() != (ImageView)event.getTarget()&&
                event.getDragboard().hasImage()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }



}
