package application.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import application.views.CourseView;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.stage.Screen;

public class PopupWindow {
    private static double duration = 20; // 設定彈出廣告視窗的初始間隔秒數
    private static boolean isPopupEnabled = true; // 控制彈出式視窗的開關
    static KeyCode[] cheatCode = { // 觸發切換開關的作弊碼序列
            KeyCode.UP, KeyCode.UP, KeyCode.DOWN, KeyCode.DOWN,
            KeyCode.LEFT, KeyCode.RIGHT, KeyCode.LEFT, KeyCode.RIGHT,
            KeyCode.B, KeyCode.A
        };
    static int currentIndex = 0; // 當前已輸入的作弊碼序列索引
    private static List<Stage> openPopups = new ArrayList<>();

	public static void start(Stage primaryStage) {
        startTimer(primaryStage);
    }

    private static void startTimer(Stage primaryStage) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(duration), event -> {
                    showPopup(primaryStage);
                })
        );

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        
        if(duration == 20) {
        	Timeline timeline2 = new Timeline(new KeyFrame(Duration.seconds(60), event -> {
                duration = 5;
                timeline.stop();
                timeline.getKeyFrames().setAll(new KeyFrame(Duration.seconds(duration), event2 -> {
                    showPopup(primaryStage);
                }));
                timeline.play();
            }));
            timeline2.play();
        }
        else if(duration == 5) {
        	Timeline timeline2 = new Timeline(new KeyFrame(Duration.seconds(20), event -> {
                duration = 1;
                timeline.stop();
                timeline.getKeyFrames().setAll(new KeyFrame(Duration.seconds(duration), event2 -> {
                    showPopup(primaryStage);
                }));
                timeline.play();
            }));
            timeline2.play();
        }
        else if(duration == 1) {
        	Timeline timeline2 = new Timeline(new KeyFrame(Duration.seconds(10), event -> {
                duration = 0.1;
                timeline.stop();
                timeline.getKeyFrames().setAll(new KeyFrame(Duration.seconds(duration), event2 -> {
                    showPopup(primaryStage);
                }));
                timeline.play();
            }));
            timeline2.play();
        }
    }

    private static void showPopup(Stage primaryStage) {
    	Scene popupScene = null;
        Stage popupStage = new Stage();
        openPopups.add(popupStage);

        // 設定彈出式視窗為模態視窗
        popupStage.initModality(Modality.NONE);
        popupStage.initOwner(primaryStage);
        popupStage.setTitle("這是一個廣告視窗");

        CourseView courseView = new CourseView();
        popupScene = courseView.getScene();
     
        popupScene.addEventFilter(KeyEvent.KEY_PRESSED, event -> handleKeyPressed(event));
        popupStage.setScene(popupScene);

        // 設定視窗的坐標位置
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double x = generateRandomCoordinate(screenBounds.getMinX(), screenBounds.getMaxX());
        double y = generateRandomCoordinate(screenBounds.getMinY(), screenBounds.getMaxY());
        popupStage.setX(x);
        popupStage.setY(y);

        // 設定視窗樣式為有邊框
        popupStage.initStyle(StageStyle.DECORATED);

        // 綁定點擊事件處理程序，將被點擊的視窗置頂
        popupStage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            popupStage.toFront();
        });

        if(isPopupEnabled) popupStage.show();
    }

    public static void handleKeyPressed(KeyEvent event) {
    	KeyCode keyCode = event.getCode();

        if (keyCode == cheatCode[currentIndex]) {
            currentIndex++;
            if (currentIndex == cheatCode.length) {
                togglePopupEnabled(); // 觸發切換彈出式視窗開關
                currentIndex = 0;
                
                // 关闭所有弹出窗口
                for (Stage popup : openPopups) {
                    popup.close();
                }
                openPopups.clear();
            }
        } else {
            currentIndex = 0;
        }
    }
    
    private static void togglePopupEnabled() {
        isPopupEnabled = !isPopupEnabled;
        if (isPopupEnabled) {
            System.out.println("ON");
        } else {
            System.out.println("OFF");
        }
    }

    private static double generateRandomCoordinate(double min, double max) {
    	double coordinate = ThreadLocalRandom.current().nextDouble(min, max);
        double screenWidth = max - min;
        
        // 如果座標超出螢幕範圍，進行調整
        if (coordinate < min || coordinate > max - screenWidth / 4) {
            coordinate = min + screenWidth / 4; // 將座標調整到螢幕內的一部分
        }
        
        return coordinate;
    }
}
