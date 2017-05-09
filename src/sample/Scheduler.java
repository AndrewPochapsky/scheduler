package sample;

import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Scheduler implements Serializable{

    private String title;

    private File directory;

    private List<Element> elements;

    private GalleryInfo galleryInfo;


    public Scheduler(String title){
        this.title = title;
        galleryInfo = new GalleryInfo();
        elements = new ArrayList<>();
        directory = new File("schedulers/"+title);
        directory.mkdir();
        File imgFile = new File(directory + "/images");
        imgFile.mkdir();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public List<Element> getElements() {
        return elements;
    }

    public GalleryInfo getGalleryInfo() {
        return galleryInfo;
    }

    public File getDirectory() {
        return directory;
    }

    public void setDirectory(File directory) {
        this.directory = directory;
    }
}
