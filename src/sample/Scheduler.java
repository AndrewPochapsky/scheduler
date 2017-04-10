package sample;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Scheduler implements Serializable{

    private String title;
    private String description;
    private List<TableRow> rows;

    public Scheduler(){
        title= "Schedule";
        description = "schedule your classes";
        rows = new ArrayList<>();
    }
    public Scheduler(String title, String description){
        this.title = title;
        this.description= description;
        rows = new ArrayList<>();
    }

    public void addEmptyRow(){
        TableRow row = new TableRow();
        rows.add(row);
    }

}
