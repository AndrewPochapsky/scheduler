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
    private int id;

    public Element(){

    }

    public String getFileName() {
        return filePath;
    }

    public void setFileName(String filePath) {
        this.filePath = filePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
   
}


