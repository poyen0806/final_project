package application.views;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import application.components.TimeAxis;
import application.components.TaskBarChart;

public class CalendarAppView {

    private static final int CONTAINER_WIDTH = 450;
    private static final int CONTAINER_HEIGHT = 600;

    private HBox view;
    private Button createTaskButton;

    public CalendarAppView() {
    	
    	createTaskButton = new Button("Create Task");
        createTaskButton.setOnAction(event -> {
            try {
                openCreateTaskPage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    	
        TimeAxis timeAxis = new TimeAxis(); // 時間軸
        TaskBarChart taskBarChart = new TaskBarChart(); // 任務Bar

        // 建立用於時間軸和任務長條圖的容器
        HBox container = new HBox();
        container.setPrefWidth(CONTAINER_WIDTH);
        container.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        container.getChildren().addAll(timeAxis.getView(), taskBarChart.getView(), createTaskButton);

        // 建立一個ScrollPane，並將容器設置為其內容
        ScrollPane scrollPane = new ScrollPane(container);
        scrollPane.setFitToWidth(true);

        view = new HBox(scrollPane);
        view.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        view.setPrefWidth(CONTAINER_WIDTH);
        view.setPrefHeight(CONTAINER_HEIGHT);
    }
    
    private void openCreateTaskPage() {
        Stage createTaskStage = new Stage();
        Pane createTaskPane = new Pane();

        CreateTask createTask = new CreateTask();
        try {
            createTask.start(createTaskStage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        createTaskStage.initModality(Modality.APPLICATION_MODAL);
        createTaskStage.setScene(new Scene(createTaskPane));
        createTaskStage.showAndWait();
    }


    public HBox getView() {
        return view;
    }

    public double getWidth() {
        return CONTAINER_WIDTH;
    }

    public double getHeight() {
        return CONTAINER_HEIGHT;
    }
}
