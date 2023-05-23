package application.controllers;

import java.util.concurrent.ThreadLocalRandom;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.stage.Screen;

public class PopupWindow {
    private static double duration = 5; // 設定彈出廣告視窗的初始間隔秒數
    private static boolean isPopupEnabled = true; // 控制彈出式視窗的開關
    static KeyCode[] cheatCode = { // 觸發切換開關的作弊碼序列
            KeyCode.UP, KeyCode.UP, KeyCode.DOWN, KeyCode.DOWN,
            KeyCode.LEFT, KeyCode.RIGHT, KeyCode.LEFT, KeyCode.RIGHT,
            KeyCode.B, KeyCode.A
        };
    static int currentIndex = 0; // 當前已輸入的作弊碼序列索引

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
        
        Timeline timeline2 = new Timeline(new KeyFrame(Duration.seconds(10), event -> {
            if (duration > 1) {
                duration --;
            }
            else {
            	duration = 0.1;
            }
            timeline.stop();
            timeline.getKeyFrames().setAll(new KeyFrame(Duration.seconds(duration), event2 -> {
                showPopup(primaryStage);
            }));
            timeline.play();
        }));
        timeline2.setCycleCount(Animation.INDEFINITE);
        timeline2.play();
    }

    private static void showPopup(Stage primaryStage) {
        Stage popupStage = new Stage();

        // 設定彈出式視窗為模態視窗
        popupStage.initModality(Modality.NONE);
        popupStage.initOwner(primaryStage);
        popupStage.setTitle("廣告");

        VBox vbox = new VBox();
        Label label = new Label("歡迎廣告商置入廣告");
        label.setFont(Font.font("Microsoft JhengHei", FontWeight.BOLD, 32));
        vbox.getChildren().add(label);
        vbox.setAlignment(Pos.CENTER);

        Scene popupScene = new Scene(vbox, 400, 300);
        popupScene.addEventFilter(KeyEvent.KEY_PRESSED, event -> handleKeyPressed(event));
        popupStage.setScene(popupScene);

        // 設定視窗的坐標位置
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double x = generateRandomCoordinate(screenBounds.getMinX(), screenBounds.getMaxX() - vbox.getWidth());
        double y = generateRandomCoordinate(screenBounds.getMinY(), screenBounds.getMaxY() - vbox.getHeight());
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
        return ThreadLocalRandom.current().nextDouble(min, max);
    }
}
