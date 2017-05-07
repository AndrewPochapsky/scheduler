package sample;

import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Scheduler implements Serializable{

    private String title;
    private String description;

    private File directory;

    private List<Element> elements;

    private GalleryInfo galleryInfo;

    private final String DEFAULT_TITLE = "Schedule";
    private final String DEFAULT_DESCRIPTION = "Schedule your classes";

    public Scheduler(){
        title= DEFAULT_TITLE;
        description = DEFAULT_DESCRIPTION;
        elements = new ArrayList<>();
        galleryInfo = new GalleryInfo();
        directory = new File("schedulers/"+title);
        directory.mkdir();
        File imgFile = new File(directory + "/images");
        imgFile.mkdir();

    }
    public Scheduler(String title, String description){
        if(!title.trim().equals("")){
            this.title = title;
        }else{
            this.title=DEFAULT_TITLE;
        }
        if(!description.trim().equals(""))
            this.description= description;
        else{
            this.description=DEFAULT_DESCRIPTION;
        }
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
