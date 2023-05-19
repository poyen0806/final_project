package application.components;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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

    private Pane view;

    public TaskBarChart() {
        view = createTaskBarChart();
    }

    private Pane createTaskBarChart() {
        Pane taskContainer = new Pane();

        // 生成一天內的任務範例
        List<Task> tasks = generateTasks();

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

            // 计算任务条的位置
            double startY = startMinutes + (i == 0 ? 30 : 35);
            double endY = endMinutes  + (i == 0 ? 30 : 45);
            
            // 定位Task Bar
            taskBar.setLayoutX(TIME_LABEL_WIDTH);
            taskBar.setLayoutY(startY);

            // 根据任务的持续时间调整Task Bar的長度
            taskBar.setHeight(endY - startY);

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
        String time = task.getStartTime().toString() + " - " + task.getEndTime().toString();
        String location = task.getLocation();
        String description = task.getDescription();

        DetailView.showEventDetails(time, location, description);
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
              LocalTime startTime = LocalTime.of(Integer.parseInt(rs.getString("hr1")), Integer.parseInt(rs.getString("mn1")));
              LocalTime endTime = LocalTime.of(Integer.parseInt(rs.getString("hr2")), Integer.parseInt(rs.getString("mn2")));
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
