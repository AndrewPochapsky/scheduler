package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;

public class Element implements Serializable {

    private String fileName;
    private int id;
    private Image image;
    private final File defaultImgFile = new File("src/question-mark.jpg");

    private ImageView imageView;

    public Element(){


    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setUpImageView(){
        try{
            image = new Image(new FileInputStream(defaultImgFile));
            imageView = new ImageView();
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
            imageView.setImage(image);


        }
        catch(IOException e){
            e.printStackTrace();
        }

    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}


