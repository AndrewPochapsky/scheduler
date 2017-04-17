package sample;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class AlertBox {
    private boolean yes = false;
    private String title, message;

    public boolean isYes() {
        return yes;
    }

    public void setYes(boolean deleteEntry) {
        this.yes= deleteEntry;
    }

    public AlertBox(String title, String message){
        this.title = title;
        this.message = message;
    }

    public void display() {
        Stage window = new Stage();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(message);
        label.setStyle("-fx-font: 15 consolas;");
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");
        yesButton.setStyle("-fx-font: 15 consolas;");
        noButton.setStyle("-fx-font: 15 consolas;");
        yesButton.setOnAction(e -> {
            yes=true;
            window.close();

        });
        noButton.setOnAction(e -> {
            yes=false;
            window.close();

        });
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, yesButton, noButton);
        layout.setAlignment(Pos.TOP_CENTER);

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout, 200, 100);
        window.setScene(scene);
        window.setResizable(false);
        window.showAndWait();
    }

}