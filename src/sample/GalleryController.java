package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GalleryController implements Initializable {

    @FXML
    Button uploadButton;

    @FXML
    AnchorPane pane;

    List<ImageView> views;

    File defaultImgFile = new File("src/question-mark.jpg");
    private Image defaultImg;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        views = new ArrayList<ImageView>();
        try{
            defaultImg = new Image(new FileInputStream(defaultImgFile));
            for(Node node: pane.getChildren()){
                if(node instanceof ImageView){
                    ImageView view = (ImageView)node;
                    view.setImage(defaultImg);
                    views.add(view);
                }
            }

        }
        catch(IOException e){
            e.printStackTrace();
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
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            for(ImageView view: views){
                Image currentImage = view.getImage();
                if(currentImage.equals(defaultImg)){
                    System.out.println("default image");
                    view.setImage(image);
                    break;
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
