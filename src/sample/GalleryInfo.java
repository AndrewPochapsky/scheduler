package sample;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GalleryInfo implements Serializable{

    private List<String> imagePaths;

    public GalleryInfo(){
        imagePaths = new ArrayList<>();
    }

    public List<String> getImagePaths() {
        return imagePaths;
    }
}
