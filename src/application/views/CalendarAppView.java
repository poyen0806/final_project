package application.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
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
import application.controllers.PopupWindow;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import application.components.TaskBarChart;

public class CalendarAppView {

    private static final int CONTAINER_WIDTH = 672;
    private static final int CONTAINER_HEIGHT = 660;

    private BorderPane view;
    private Button createTaskButton;
    private Button clearTaskButton;

    public CalendarAppView(Stage stage) {
    	TimeAxis timeAxis = new TimeAxis(); // 時間軸
        TaskBarChart taskBarChart = new TaskBarChart(stage); // 任務Bar
        createTaskButton = new Button("Create Task");
        createTaskButton.setOnAction(event -> {
            try {
            	switchToCreateTaskPage(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        clearTaskButton = new Button("Clear Task");
        clearTaskButton.setOnAction(event -> {
            try {
            	deleteTask(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

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

        // 创建一个HBox作为按钮容器
        HBox buttonContainer = new HBox(50); // 设置按钮之间的间距为10
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
        buttonContainer.setPadding(new Insets(5));
        buttonContainer.getChildren().addAll(createTaskButton, clearTaskButton);
        view.setTop(buttonContainer);
    }

    private void switchToCreateTaskPage(Stage stage) {
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
        
        // 在createTaskStage关闭时设置一个事件处理程序
        createTaskStage.setOnHiding(event -> {
        	CalendarAppView calendarAppView = new CalendarAppView(stage);
        	Scene scene = new Scene(calendarAppView.getView(), calendarAppView.getWidth(), calendarAppView.getHeight());
        	scene.addEventFilter(KeyEvent.KEY_PRESSED, event2 -> PopupWindow.handleKeyPressed(event2));
        	stage.setScene(scene);
            stage.setTitle("Calendar App");
            stage.show();
        });
        
        createTaskStage.show();
        stage.close();
    }

    private void deleteTask(Stage stage) {
    	Connection connection = null;
        try {
          // create a database connection
          
          connection = DriverManager.getConnection("jdbc:sqlite:task_db.db");
          Statement statement = connection.createStatement();
          statement.setQueryTimeout(30);  // set timeout to 30 sec.
          statement.executeUpdate("DELETE FROM task_db");
        }
        catch(SQLException e) {
          // if the error message is "out of memory",
          // it probably means no database file is found
          System.err.println(e.getMessage());
        }
        finally {
          try  {
            if(connection != null)
              connection.close();
          }
          catch(SQLException e) {
            // connection close failed.
            System.err.println(e.getMessage());
          }
        }
    	
    	stage.close();
    	CalendarAppView calendarAppView = new CalendarAppView(stage);
    	Scene scene = new Scene(calendarAppView.getView(), calendarAppView.getWidth(), calendarAppView.getHeight());
    	scene.addEventFilter(KeyEvent.KEY_PRESSED, event2 -> PopupWindow.handleKeyPressed(event2));
    	stage.setScene(scene);
        stage.setTitle("Calendar App");
        stage.show();
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
