package application.components;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import application.models.Task;
import application.views.DetailView;

public class TaskBarChart {

    private static final int TIME_LABEL_WIDTH = 50;
    private Stage calendar;
    private Pane view;

    public TaskBarChart(Stage calendar) {
        view = createTaskBarChart();
        this.calendar = calendar;
    }

    private Pane createTaskBarChart() {
        Pane taskContainer = new Pane();
        Image image = new Image(getClass().getResourceAsStream("/application/images/bg.jpg"));
        BackgroundImage backgroundImage = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,     // 设置重复行为
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,     // 设置位置
                BackgroundSize.DEFAULT);        // 设置大小
        Background bg = new Background(backgroundImage);
        taskContainer.setPrefWidth(620);
        taskContainer.setPrefHeight(1440);
        taskContainer.setBackground(bg);

        // 生成一天內的任務範例
        List<Task> tasks = generateTasks();
        String taskBarStyle = "-fx-arc-width: 15; -fx-arc-height: 15; -fx-effect: dropshadow(gaussian, #ffffff, 15, 0.5, 0, 0);";

        // 按照開始時間和持續時間對任務進行排序
        Collections.sort(tasks);

        // 為每個任務創建Task Bar
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            
            // 確定Task Bar的開始和結束位置
            int startMinutes = task.getStartTime().toSecondOfDay() / 60;
            int endMinutes = task.getEndTime().toSecondOfDay() / 60;

            // 創建Task Bar
            Rectangle taskBar = new Rectangle();
            taskBar.setWidth(20); // 設置Task Bar的寬度
            taskBar.setFill(task.getColor()); // 設置Task Bar的顏色
            taskBar.setStyle(taskBarStyle);

            // 计算任务条的位置
            double startY = startMinutes + 22.5 + startMinutes / 30 * 0.375;
            double endY = endMinutes  + 37.5 + endMinutes / 30 * 0.225;
            
            // 定位Task Bar
            taskBar.setLayoutX(TIME_LABEL_WIDTH);
            taskBar.setLayoutY(startY);

            // 根据任务的持续时间调整Task Bar的長度
            taskBar.setHeight((endMinutes - startMinutes != 0) ? endY - startY : 0);

            // Check for overlapping tasks
            for (int j = 0; j < i; j++) {
                Task previousTask = tasks.get(j);
                int previousEndMinutes = previousTask.getEndTime().toSecondOfDay() / 60;

                // If the tasks overlap
                if (startMinutes < previousEndMinutes) {
                    // Update the X position of the task bar
                    double previousTaskX = taskContainer.getChildren().get(j).getLayoutX();
                    taskBar.setLayoutX(previousTaskX + 30);
                }
            }

            // Add the task bar to the task container
            taskContainer.getChildren().add(taskBar);
            // Add click event handler to the task bar
            taskBar.setOnMouseClicked(event -> {
                showTaskDetails(task); // Display the details of the clicked task
            });
        }

        return taskContainer;
    }
    
    private void showTaskDetails(Task task) {
    	Stage detailStage = new Stage();
    	detailStage.initModality(Modality.NONE);

        Pane detailViewPane = new Pane();

        DetailView detailView = new DetailView(task);
        try {
        	detailView.start(detailStage, calendar);
        } catch (Exception e) {
            e.printStackTrace();
        }

        detailViewPane.getChildren().add(detailView.getRoot());
        detailStage.setScene(new Scene(detailViewPane));
        detailStage.show();
    }
   
    public Pane getView() {
        return view;
    }

    private List<Task> generateTasks() {
    	
    	List<Task> tasks = new ArrayList<>();

    	Connection connection = null;
        try {
          // create a database connection
          
          connection = DriverManager.getConnection("jdbc:sqlite:task_db.db");
          Statement statement = connection.createStatement();
          statement.setQueryTimeout(30);  // set timeout to 30 sec.
          ResultSet rs = statement.executeQuery("select * from task_db");
          while (rs.next()) {
              // 从数据库中获取任务信息
        	  int hr1 = rs.getInt("hr1");
        	  int mn1 = rs.getInt("mn1");
        	  int hr2 = rs.getInt("hr2");
        	  int mn2 = rs.getInt("mn2");
        	  
        	  LocalTime startTime = LocalTime.of(hr1, mn1);
        	  LocalTime endTime = LocalTime.of(hr2, mn2);
              String colorString = rs.getString("color");
              String location = rs.getString("place");
              String description = rs.getString("mission");

              // 检查颜色是否为null，如果为null，则使用默认的蓝色
              Color color = colorString != null ? Color.web(colorString) : Color.BLUE;

              // 创建任务对象并添加到任务列表中
              Task task = new Task(startTime, endTime, color, location, description);
              tasks.add(task);
          }
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

        return tasks;
    }
}
