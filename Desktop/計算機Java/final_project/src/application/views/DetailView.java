package application.views;

import application.controllers.PopupWindow;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DetailView {

    public static void showEventDetails(String time, String location, String title) {
        Stage primaryStage = new Stage();
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setTitle("標題");

        Label timeLabel = new Label("時間： " + time);
        Label locationLabel = new Label("地點： " + location);

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(timeLabel, locationLabel);

        Scene scene = new Scene(vbox, 300, 200);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> PopupWindow.handleKeyPressed(event));
        primaryStage.initModality(Modality.NONE);
        primaryStage.setScene(scene);
        primaryStage.setTitle(title);
        primaryStage.showAndWait();
    }
}
