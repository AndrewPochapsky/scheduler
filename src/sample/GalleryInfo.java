package sample;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GalleryInfo implements Serializable{

    private List<String> imagePaths;

    public GalleryInfo(){
        imagePaths = new ArrayList<>();
        /*for(int i = 0; i< 16; i++){
            imagePaths.add(i, "heh");
        }*/
    }

    public List<String> getImagePaths() {
        return imagePaths;
    }

    public void setImagePaths(List<String> paths){
        imagePaths = paths;
    }

}
