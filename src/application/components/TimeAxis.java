package application.components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import java.time.LocalTime;

public class TimeAxis {

    public static final int MINUTES_IN_DAY = 24 * 60;
    public static final int HOUR_HEIGHT = 60;
    public static final int TIME_LABEL_WIDTH = 40;

    private VBox view;

    public TimeAxis() {
        view = createTimeAxis();
    }

    private VBox createTimeAxis() {
        VBox timeAxis = new VBox();
        timeAxis.setSpacing(0);
        timeAxis.setPadding(new Insets(10, 0, 0, 0));
        timeAxis.setPrefWidth(TIME_LABEL_WIDTH);
        timeAxis.setBackground(new Background(new BackgroundFill(Color.web("#F5F5F5"), CornerRadii.EMPTY, Insets.EMPTY)));

        // Create the time labels
        for (int i = 0; i < MINUTES_IN_DAY; i += 30) {
            LocalTime time = LocalTime.of(i / 60, i % 60);
            Label label = new Label(time.toString());
            label.setPrefWidth(TIME_LABEL_WIDTH);
            label.setPrefHeight(HOUR_HEIGHT / 2);
            label.setLayoutY(i - label.getHeight() / 2);
            String labelStyle = "-fx-font-family: 'Helvetica';" + "-fx-text-fill: #333333;" + "-fx-font-size: 14px";
            label.setStyle(labelStyle);
            timeAxis.getChildren().add(label);
        }

        return timeAxis;
    }

    public VBox getView() {
        return view;
    }
}

