package sample;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProgramController implements Serializable{

    private String title;
    private String description;
    private List<TableRow> rows;

    public ProgramController(){
        title= "Schedule";
        description = "schedule your classes";
        rows = new ArrayList<>();
    }
    public ProgramController(String title, String description){
        this.title = title;
        this.description= description;
        rows = new ArrayList<>();
    }
}
