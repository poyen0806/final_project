package application.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class PopupWindow {
    private static double duration = 20; // 設定彈出廣告視窗的初始間隔秒數
    private static int times = 0;
    private static boolean isPopupEnabled = true; // 控制彈出式視窗的開關
    static KeyCode[] cheatCode = { // 觸發切換開關的作弊碼序列
        KeyCode.UP, KeyCode.UP, KeyCode.DOWN, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.LEFT, KeyCode.RIGHT,
        KeyCode.B, KeyCode.A
    };
    static int currentIndex = 0; // 當前已輸入的作弊碼序列索引
    private static List<Stage> openPopups = new ArrayList<>();
    private static Timeline timeline2, timeline3, timeline4;

    public static void start(Stage primaryStage) {
        startTimer(primaryStage);
    }

    private static void startTimer(Stage primaryStage) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(duration), event -> {
            if(isPopupEnabled) showPopup(primaryStage);
            times++;
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        Timeline timeManager = new Timeline(new KeyFrame(Duration.seconds(1), manage -> {
            if (duration == 20 && times == 3) {
                duration = 5;
                times = 0;
                timeline.stop();
                timeline2 = new Timeline(new KeyFrame(Duration.seconds(duration), event -> {
                	if(isPopupEnabled) showPopup(primaryStage);
                    times++;
                }));
                timeline2.setCycleCount(Animation.INDEFINITE);
                timeline2.play();
            } else if (duration == 5 && times == 4) {
                duration = 1;
                times = 0;
                timeline2.stop();
                timeline3 = new Timeline(new KeyFrame(Duration.seconds(duration), event -> {
                	if(isPopupEnabled) showPopup(primaryStage);
                    times++;
                }));
                timeline3.setCycleCount(Animation.INDEFINITE);
                timeline3.play();
            } else if (duration == 1 && times == 10) {
                duration = 0.1;
                times = 0;
                timeline3.stop();
                timeline4 = new Timeline(new KeyFrame(Duration.seconds(duration), event -> {
                	if(isPopupEnabled) showPopup(primaryStage);
                    times++;
                }));
                timeline4.setCycleCount(Animation.INDEFINITE);
                timeline4.play();
            }
        }));
        timeManager.setCycleCount(Animation.INDEFINITE);
        timeManager.play();
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

        // 設定視窗樣式為有邊框
        popupStage.initStyle(StageStyle.DECORATED);

        // 綁定點擊事件處理程序，將被點擊的視窗置頂
        popupStage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            popupStage.toFront();
        });

        if (isPopupEnabled) {
            // 設定視窗的坐標位置
        	Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        	double screenWidth = screenBounds.getWidth();
        	double screenHeight = screenBounds.getHeight();

        	Random random = new Random();
        	double x = random.nextDouble() * (screenWidth - 397);
        	double y = random.nextDouble() * (screenHeight - 562);

            popupStage.setX(x);
            popupStage.setY(y);
            popupStage.show();
        }
    }

    public static void handleKeyPressed(KeyEvent event) {
        KeyCode keyCode = event.getCode();

        if (keyCode == cheatCode[currentIndex]) {
            currentIndex++;
            if (currentIndex == cheatCode.length) {
                togglePopupEnabled(); // 觸發切換彈出式視窗開關
                currentIndex = 0;

                // 關閉所有彈出式視窗
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
}
