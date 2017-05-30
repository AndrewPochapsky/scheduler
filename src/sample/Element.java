package sample;

import java.io.Serializable;

public class Element implements Serializable {
    private String filePath;
    private String text;
    private String speechText;
    public Element(){
        filePath = "src/question-mark.jpg";
        text = new String();
    }

    public String getFileName() {
        return filePath;
    }

    public void setFileName(String filePath) {
        this.filePath = filePath;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSpeechText() {
        return speechText;
    }

    public void setSpeechText(String speechText) {
        this.speechText = speechText;
    }
}


