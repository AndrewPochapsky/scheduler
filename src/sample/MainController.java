package sample;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
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
    private int lastID;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        voce.SpeechInterface.init("C:/Users/andre/.IdeaIC2016.3/config/text-to-speech jars/voce-0.9.1/lib", true, false, "", "");
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
        for(Element e :ProgramController.getCurrentScheduler().getElements()){
            //System.out.println("bleh");
            System.out.println("Speech Text: "+e.getSpeechText());
        }

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
        voce.SpeechInterface.destroy();
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
            int id=-1;
            switch(view.getId()){
                case "p1":
                    id = 0;
                    break;
                case "p2":
                    id = 1;
                    break;
                case "lunch":
                    id = 2;
                    break;
                case "p3":
                    id = 3;
                    break;
                case "p4":
                    id = 4;
                    break;
            }

            String fullPath = ProgramController.getCurrentScheduler().getGalleryInfo().getImagePaths().get(lastID);
            String abreviation = fullPath.substring(fullPath.lastIndexOf("\\")+1, fullPath.lastIndexOf("."));
            ProgramController.getCurrentScheduler().getElements().get(id).setSpeechText(abreviation);
        }
        event.consume();
    }

    public void SetOnDragOver(DragEvent event){
        if (event.getGestureSource() != (ImageView)event.getTarget()&&
                event.getDragboard().hasImage()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    private void saveImageToDisk(File file, ImageView view){
        try{
            BufferedImage bImage = SwingFXUtils.fromFXImage(view.getImage(), null);
            String id = view.getId();
            ImageIO.write(bImage, "png", file);
            findElement(id).setFileName(file.getAbsolutePath());
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
                    if(!file.getAbsolutePath().substring(file.getAbsolutePath().length()-17,file.getAbsolutePath().length()).equals("question-mark.jpg")&& view.getImage().equals(defaultImg)){
                        view.setImage(image);
                        break;
                    }
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void setOnDragDetectedUser(MouseEvent event){
        ImageView view = (ImageView)event.getSource();
        Dragboard db = view.startDragAndDrop(TransferMode.ANY);

        ClipboardContent content = new ClipboardContent();
        content.putImage(view.getImage());
        db.setContent(content);
        //db.setDragView(view.getImage());
        currentView = view;
        //lastID = Integer.parseInt(view.getId().substring(1));
        event.consume();
    }
    public void setOnDragDetected(MouseEvent event){
        ImageView view = (ImageView)event.getSource();
        Dragboard db = view.startDragAndDrop(TransferMode.ANY);

        ClipboardContent content = new ClipboardContent();
        content.putImage(view.getImage());
        db.setContent(content);
        //db.setDragView(view.getImage());
        currentView = view;
        lastID = Integer.parseInt(view.getId().substring(1));
        System.out.println("LastID: "+lastID);
        event.consume();
    }

    public void setOnDeletePressed(ActionEvent event){
        Button button = (Button)event.getSource();
        String id = button.getId();
        ImageView view = null;
        Element e= null;

        switch(id){
            case "p1Button":
                System.out.println("p1 pressed");
                view = userViews.get(0);
                e = ProgramController.getCurrentScheduler().getElements().get(0);
                break;
            case "p2Button":
                System.out.println("p2Pressed");
                view = userViews.get(1);
                e = ProgramController.getCurrentScheduler().getElements().get(1);
                break;
            case "lunchButton":
                System.out.println("lunch pressed");
                view = userViews.get(2);
                e = ProgramController.getCurrentScheduler().getElements().get(2);
                break;
            case "p3Button":
                System.out.println("p3 pressed");
                view = userViews.get(3);
                e = ProgramController.getCurrentScheduler().getElements().get(3);
                break;
            case "p4Button":
                System.out.println("p4 pressed");
                view = userViews.get(4);
                e = ProgramController.getCurrentScheduler().getElements().get(4);
                break;
        }
        try{
            view.setImage(defaultImg);
            e.setSpeechText("");
        }catch(NullPointerException ex){
            System.out.println("trying to delete from null imageview");
        }
    }



    public void setOnRecycleDropped(DragEvent event){
        Dragboard db = event.getDragboard();

        if(db.hasImage()){
            for(ImageView view: userViews){
                if(currentView.equals(view)){
                    currentView.setImage(defaultImg);
                    findElement(view.getId()).setSpeechText("");
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
    //TODO make this work for gallery as well, will need to use id of each to get the correct imagepath and then read it out EZPZ
    public void setOnMouseEnter(MouseEvent event){
        ImageView view = (ImageView)event.getTarget();
        voce.SpeechInterface.synthesize(findElement(view.getId()).getSpeechText());
    }
    public void setOnMouseExit(){
        voce.SpeechInterface.stopSynthesizing();
    }

    public void setOnMouseEnterGallery(MouseEvent event){
        ImageView view = (ImageView)event.getTarget();
        int id = Integer.parseInt(view.getId().substring(1));
        if(!view.getImage().equals(defaultImg)){
            String path = ProgramController.getCurrentScheduler().getGalleryInfo().getImagePaths().get(id);
            String text = path.substring(path.lastIndexOf("\\")+1, path.lastIndexOf("."));
            voce.SpeechInterface.synthesize(text);
        }

    }

    private Element findElement(String id){
        Element e = null;
        switch(id){
            case "p1":
                e = ProgramController.getCurrentScheduler().getElements().get(0);
                break;
            case "p2":
                e = ProgramController.getCurrentScheduler().getElements().get(1);
                break;
            case "lunch":
                e = ProgramController.getCurrentScheduler().getElements().get(2);
                break;
            case "p3":
                e = ProgramController.getCurrentScheduler().getElements().get(3);
                break;
            case "p4":
                e = ProgramController.getCurrentScheduler().getElements().get(4);
                break;
        }
        return e;
    }


}
