package sample;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
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
    VBox vbox, galleryBox;

    @FXML
    ImageView recyclingBin;

    @FXML
    Button uploadButton;

    List<ImageView> userViews = new ArrayList<>();

    List<ImageView> galleryViews = new ArrayList<>();

    File defaultImgFile = new File("src/question-mark.jpg");
    private ImageView currentView;
    private Image defaultImg;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        try{
            recyclingBin.setImage(new Image(new FileInputStream(new File("src/recyclingbin.png"))));
            defaultImg = new Image(new FileInputStream(defaultImgFile));
        }catch(IOException exe){
            exe.printStackTrace();
        }


        Main.getMainStage().setOnCloseRequest(e->{
            System.out.println("Closing");
            try{
                handleExit();
            }catch(IOException ex){
                ex.printStackTrace();
            }

        });
        try{
            initializeRows();
        }catch(IOException e){
            e.printStackTrace();
        }

        setDefaultImages();
        if(ProgramController.getCurrentScheduler().getGalleryInfo().getImagePaths().size() > 0){
            setSavedImages();
        }
    }

    public void handleExit() throws IOException{
        for(ImageView view: userViews){
            File file = new File("schedulers/"+ProgramController.getCurrentScheduler().getTitle()+"/images/"+view.getId());
            saveImageToDisk(file, view);
        }
        //ProgramController.getCurrentScheduler().getGalleryInfo().setImagePaths(new ArrayList<>());
        for(int i = 0; i< galleryViews.size(); i++){
            if(galleryViews.get(i).getImage().equals(defaultImg)){
                //System.out.println("removing");
                //ProgramController.getCurrentScheduler().getGalleryInfo().getImagePaths().set(i, defaultImgFile.getAbsolutePath());
                if(ProgramController.getCurrentScheduler().getGalleryInfo().getImagePaths().size() > i)
                    ProgramController.getCurrentScheduler().getGalleryInfo().getImagePaths().set(i, defaultImgFile.getAbsolutePath());
                else{
                    ProgramController.getCurrentScheduler().getGalleryInfo().getImagePaths().add(i, defaultImgFile.getAbsolutePath());
                }
            }
        }

        System.out.println("Closing");
        FileHandler.save(ProgramController.getCurrentScheduler());
        Platform.exit();
    }

    private void initializeRows() throws IOException{

        for(Node box: vbox.getChildren()){
            if(box instanceof HBox){
                HBox hbox = (HBox)box;
                for(Node node: hbox.getChildren()){
                    if(node instanceof ImageView){
                        ImageView view = (ImageView)node;
                        //System.out.println("ID: "+view.getId());
                        userViews.add(view);
                    }
                }
            }
        }
        for(int i = 0; i < userViews.size(); i++){
            ImageView view = userViews.get(i);

            if(ProgramController.getCurrentScheduler().getElements().size() <= i){
                ProgramController.getCurrentScheduler().getElements().add(new Element());
            }
            File imgFile = new File(ProgramController.getCurrentScheduler().getElements().get(i).getFileName());
            view.setImage(new Image(new FileInputStream(imgFile)));
        }
    }

    public void SetOnDragDropped(DragEvent event){
        Dragboard db = event.getDragboard();
        if(db.hasImage()){
            ImageView view = (ImageView)event.getSource();
            view.setImage(db.getImage());
        }
    }

    public void SetOnDragOver(DragEvent event){
        if (event.getGestureSource() != (ImageView)event.getTarget()&&
                event.getDragboard().hasImage()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        //currentView = (ImageView)event.getTarget();
        event.consume();
    }

    private void saveImageToDisk(File file, ImageView view){
        try{
            BufferedImage bImage = SwingFXUtils.fromFXImage(view.getImage(), null);
            String id = view.getId();
            ImageIO.write(bImage, "png", file);

            switch (id){
                case "p1":
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
    }

    private void setDefaultImages(){

        for(Node node: galleryBox.getChildren()){
            if(node instanceof HBox){
                HBox box = (HBox)node;
                for(Node _node: box.getChildren()){
                    if(_node instanceof ImageView){
                        ImageView view = (ImageView)_node;
                        view.setImage(defaultImg);
                        view.setPreserveRatio(false);
                        galleryViews.add(view);
                    }
                }
            }
        }



    }

    public void handleUpload(ActionEvent event){
        System.out.println("uploading image");
        Stage currentStage = (Stage)uploadButton.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        File file = fileChooser.showOpenDialog(currentStage);

        try{
            BufferedImage bufferedImage = ImageIO.read(file);
            //add image path to list to be initialized at start

            //System.out.println(file.getAbsolutePath());
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
           /* for(ImageView view: galleryViews){
                Image currentImage = view.getImage();
                if(currentImage.equals(defaultImg)){
                    //System.out.println("default image");
                    view.setImage(image);
                    break;
                }
            }*/
            for(int i = 0; i< galleryViews.size(); i++){
                Image currentImage = galleryViews.get(i).getImage();
                if(currentImage.equals(defaultImg)){
                    galleryViews.get(i).setImage(image);
                   // System.out.println("i: "+i);
                    //System.out.println("Size: "+ProgramController.getCurrentScheduler().getGalleryInfo().getImagePaths().size());
                    if(ProgramController.getCurrentScheduler().getGalleryInfo().getImagePaths().size() > i)
                        ProgramController.getCurrentScheduler().getGalleryInfo().getImagePaths().set(i, file.getAbsolutePath());
                    else{
                        ProgramController.getCurrentScheduler().getGalleryInfo().getImagePaths().add(i, file.getAbsolutePath());
                    }
                    //System.out.println("saving to index "+i);
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
                for(ImageView view: galleryViews){


                    //System.out.println(file.getAbsolutePath().substring(file.getAbsolutePath().length()-17,file.getAbsolutePath().length()));
                    if(!file.getAbsolutePath().substring(file.getAbsolutePath().length()-17,file.getAbsolutePath().length()).equals("question-mark.jpg")&& view.getImage().equals(defaultImg)){
                        //System.out.println("yessssss");
                        view.setImage(image);
                        break;
                    }
                }
            }
        }catch(IOException e){
            e.printStackTrace();
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

    public void setOnDeletePressed(ActionEvent event){
        Button button = (Button)event.getSource();
        String id = button.getId();
        ImageView view = null;
        switch(id){
            case "p1Button":
                System.out.println("p1 pressed");
                view = userViews.get(0);
                break;
            case "p2Button":
                System.out.println("p2Pressed");
                view = userViews.get(1);
                break;
            case "lunchButton":
                System.out.println("lunch pressed");
                view = userViews.get(2);
                break;
            case "p3Button":
                System.out.println("p3 pressed");
                view = userViews.get(3);
                break;
            case "p4Button":
                System.out.println("p4 pressed");
                view = userViews.get(4);
                break;
        }
        try{
            view.setImage(defaultImg);
        }catch(NullPointerException e){
            System.out.println("trying to delete from null imageview");
        }
    }

    public void setOnDeleteAll(){
        for(ImageView view: userViews){
            view.setImage(defaultImg);
        }
    }

    public void setOnDeleteGallery(){
        AlertBox box = new AlertBox("Confrimation", "Are you sure?");
        box.display();
        if(box.isYes()){
            for(ImageView view: galleryViews){
                view.setImage(defaultImg);
            }
            ProgramController.getCurrentScheduler().getGalleryInfo().setImagePaths(new ArrayList<String>());
        }
    }

    public void setOnRecycleDropped(DragEvent event){
        Dragboard db = event.getDragboard();

        if(db.hasImage()){
            currentView.setImage(defaultImg);


        }
    }

}
