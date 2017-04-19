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
        try{
            image = new Image(new FileInputStream(defaultImgFile));

        }
        catch(IOException e){
            e.printStackTrace();
        }

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
        imageView = new ImageView();
        imageView.setImage(image);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}


