package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GalleryController implements Initializable{

    List<ImageView> galleryViews = new ArrayList<>();

    @FXML
    ImageView recyclingBin;

    @FXML
    AnchorPane pane;

    @FXML
    Text title;

    File defaultImgFile = new File("src/question-mark.jpg");
    Image defaultImg;
    ImageView currentView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {



        title.setText(ProgramController.getCurrentScheduler().getTitle()+ "'s Gallery");

        try{
            recyclingBin.setImage(new Image(new FileInputStream(new File("src/recyclingbin.png"))));
            defaultImg = new Image(new FileInputStream(defaultImgFile));
        }catch(IOException exe){
            exe.printStackTrace();
        }

        setDefaultImages();
        if(ProgramController.getCurrentScheduler().getGalleryInfo().getImagePaths().size() > 0){
            setSavedImages();
        }
        for(Element e :ProgramController.getCurrentScheduler().getElements()){
            System.out.println("bleh");
            System.out.println("Speech Text: "+e.getSpeechText());
        }

    }

    private void setDefaultImages(){
        int id = 0;
        for(Node node: pane.getChildren()){
            if(node instanceof HBox){
                HBox box = (HBox)node;
                for(Node _node: box.getChildren()){
                    if(_node instanceof ImageView){
                        ImageView view = (ImageView)_node;
                        view.setImage(defaultImg);
                        //view.setId("_"+id);
                        id++;
                        view.setPreserveRatio(false);
                        galleryViews.add(view);
                    }
                }
            }
        }
    }

    public void handleUpload(ActionEvent event){
        System.out.println("uploading image");
        Stage currentStage = (Stage)title.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilterJPGandPNG = new FileChooser.ExtensionFilter("JPG files (*.jpg) and PNG files (*.png)", "*.JPG","*.PNG", "Test");
        //FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPGandPNG);
        File file = fileChooser.showOpenDialog(currentStage);

        try{
            BufferedImage bufferedImage = ImageIO.read(file);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            for(int i = 0; i< galleryViews.size(); i++){
                Image currentImage = galleryViews.get(i).getImage();
                if(currentImage.equals(defaultImg)){
                    galleryViews.get(i).setImage(image);
                    System.out.println("adding new image (gallery)");
                    ProgramController.getCurrentScheduler().getGalleryInfo().getImagePaths().add(i, file.getAbsolutePath());
                    break;
                }
            }
        }catch(Exception e){
            System.out.println("No Image selected to add to gallery");
            e.printStackTrace();
        }
    }

    private void setSavedImages(){
        try{
            for(String path: ProgramController.getCurrentScheduler().getGalleryInfo().getImagePaths()){
                File file = new File(path);
                Image image = new Image(new FileInputStream(file));
                for(int i = 0; i< galleryViews.size(); i++){
                    if(!file.getAbsolutePath().substring(file.getAbsolutePath().length()-17,file.getAbsolutePath().length()).equals("question-mark.jpg")&& galleryViews.get(i).getImage().equals(defaultImg)){
                        System.out.println("setting saved image");
                        galleryViews.get(i).setImage(image);
                        break;
                    }
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void setOnDeleteGallery(){
        AlertBox box = new AlertBox("Confirmation", "Are you sure?");
        box.display();
        if(box.isYes()){
            for(ImageView view: galleryViews){
                view.setImage(defaultImg);
            }
            ProgramController.getCurrentScheduler().getGalleryInfo().setImagePaths(new ArrayList<String>());
        }
    }

    public void setOnDragDetected(MouseEvent event){
        ImageView view = (ImageView)event.getSource();
        Dragboard db = view.startDragAndDrop(TransferMode.ANY);

        ClipboardContent content = new ClipboardContent();
        content.putImage(view.getImage());
        db.setContent(content);
        //db.setDragView(view.getImage());
        currentView = view;
        event.consume();
    }

    public void setOnDragOver(DragEvent event){
        if (event.getGestureSource() != (ImageView)event.getTarget()&&
                event.getDragboard().hasImage()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        //currentView = (ImageView)event.getTarget();
        event.consume();
    }

    public void setOnRecycleDropped(DragEvent event){
        Dragboard db = event.getDragboard();

        if(db.hasImage()){
            currentView.setImage(defaultImg);
            String id = currentView.getId();
            System.out.println("Id: "+id);
            ProgramController.getCurrentScheduler().getGalleryInfo().getImagePaths().set(Integer.parseInt(id.substring(1)), defaultImgFile.getAbsolutePath());
        }
        for(int i =0; i < galleryViews.size(); i++){
            if(ProgramController.getCurrentScheduler().getGalleryInfo().getImagePaths().get(i).equals(defaultImgFile.getAbsolutePath())){
                ProgramController.getCurrentScheduler().getGalleryInfo().getImagePaths().remove(i);
            }
            System.out.println("Path: "+ProgramController.getCurrentScheduler().getGalleryInfo().getImagePaths().get(i));
        }

        event.consume();
    }



    public void handleSaveAndExit()throws IOException{
        FileHandler.save(ProgramController.getCurrentScheduler());
        Stage stage = (Stage)title.getScene().getWindow();
        stage.close();

    }

}
