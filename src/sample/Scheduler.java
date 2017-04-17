package sample;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Scheduler implements Serializable{

    private String title;
    private String description;
    private List<TableRow> rows;

    private final String DEFAULT_TITLE = "Schedule";
    private final String DEFAULT_DESCRIPTION = "Schedule your classes";

    public Scheduler(){
        title= DEFAULT_TITLE;
        description = DEFAULT_DESCRIPTION;
        rows = new ArrayList<>();
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
        rows = new ArrayList<>();
    }

    public void addEmptyRow(){
        TableRow row = new TableRow();
        rows.add(row);
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

    public List<TableRow> getRows() {
        return rows;
    }

    public void setRows(List<TableRow> rows) {
        this.rows = rows;
    }
}
