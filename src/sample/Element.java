package sample;

import java.io.Serializable;

public class Element implements Serializable {

    private String fileName;
    private int id;

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
}
