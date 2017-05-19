package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;

public class Element implements Serializable {
    //TODO if use is not found remove this class, use could be holding the directory if the image it has
    private String filePath;
    private String text;

    public Element(){
        filePath = "src/question-mark.jpg";
        text = new String();
    }

    public String getFileName() {
        return filePath;
    }

    public void setFileName(String filePath) {
        this.filePath = filePath;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}


