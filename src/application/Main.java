package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import application.controllers.PopupWindow;
import application.views.CalendarAppView;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        CalendarAppView calendarAppView = new CalendarAppView(); // 主視窗

        // 設置場景並顯示舞台
        Scene scene = new Scene(calendarAppView.getView(), calendarAppView.getWidth(), calendarAppView.getHeight());
        
        // 添加事件過濾器，用於處理按鍵事件，即輸入↑↑↓↓←→←→BA會開關彈出廣告視窗功能
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> PopupWindow.handleKeyPressed(event));
        
        stage.setScene(scene);
        stage.setTitle("Calendar App");
        stage.show();
        
        // 啟動彈出廣告視窗功能
        PopupWindow.start(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}