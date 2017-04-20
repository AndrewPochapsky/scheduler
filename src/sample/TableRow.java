package sample;

import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TableRow implements Serializable {

    private String title;
    private List<Element> elements;
    //private HBox row;

    public TableRow(){
        title = "This is a row";
        elements = new ArrayList<>();
    }

    public TableRow(String title){
        this.title = title;
        elements= new ArrayList<>();
    }

    public void addElement(Element e){
        elements.add(e);
    }

    public void removeElement(Element e){
        elements.remove(e);
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

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }
}
