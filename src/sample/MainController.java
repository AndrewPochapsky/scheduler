package sample;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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

    List<ImageView> userViews = new ArrayList<>();

    List<ImageView> galleryViews = new ArrayList<>();

    List<TextField> fields = new ArrayList<>();

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
        setUpTextFields();
    }

    public void handleExit() throws IOException{
        for(ImageView view: userViews){
            File file = new File("schedulers/"+ProgramController.getCurrentScheduler().getTitle()+"/images/"+view.getId());
            saveImageToDisk(file, view);
        }

        //save fields
        for(int i =0; i <fields.size(); i++){
            ProgramController.getCurrentScheduler().getElements().get(i).setText(fields.get(i).getText());
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
                    if(node instanceof VBox){
                        VBox v = (VBox)node;
                        for(Node element : v.getChildren()){
                            if(element instanceof ImageView){
                                userViews.add((ImageView)element);
                            }
                            if(element instanceof TextField){
                                fields.add((TextField)element);
                            }
                        }
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
        event.consume();
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


    public void setOnRecycleDropped(DragEvent event){
        Dragboard db = event.getDragboard();

        if(db.hasImage()){
            for(ImageView view: userViews){
                if(currentView.equals(view)){
                    currentView.setImage(defaultImg);
                    break;
                }
            }



        }
    }

    private void setUpTextFields(){
        for(int i =0; i < fields.size(); i++){
            fields.get(i).setText(ProgramController.getCurrentScheduler().getElements().get(i).getText());
        }
    }


}
