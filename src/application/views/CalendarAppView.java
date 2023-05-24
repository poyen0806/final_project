package application.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
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

    private BorderPane view;
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
        container.getChildren().addAll(timeAxis.getView(), taskBarChart.getView());

        // 创建一个ScrollPane，将容器作为其内容
        ScrollPane scrollPane = new ScrollPane(container);
        scrollPane.setFitToWidth(true);

        // 创建一个BorderPane作为整体布局容器
        view = new BorderPane();
        view.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        view.setPrefWidth(CONTAINER_WIDTH);
        view.setPrefHeight(CONTAINER_HEIGHT);
        view.setCenter(scrollPane); // 将ScrollPane放置在中心

        // 将按钮放置在右上角
        HBox buttonContainer = new HBox(createTaskButton);
        buttonContainer.setAlignment(Pos.TOP_RIGHT);
        buttonContainer.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
        buttonContainer.setPadding(new Insets(5, 5, 5, 5));
        view.setTop(buttonContainer);

    }

    private void openCreateTaskPage() {
        Stage createTaskStage = new Stage();
        createTaskStage.initModality(Modality.APPLICATION_MODAL);

        Pane createTaskPane = new Pane();

        CreateTask createTask = new CreateTask();
        try {
            createTask.start(createTaskStage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        createTaskPane.getChildren().add(createTask.getRoot()); // 将CreateTask的root添加到createTaskPane
        createTaskStage.setScene(new Scene(createTaskPane));
        createTaskStage.showAndWait();
    }

    public BorderPane getView() {
        return view;
    }

    public double getWidth() {
        return CONTAINER_WIDTH;
    }

    public double getHeight() {
        return CONTAINER_HEIGHT;
    }
}
