package application.views;

import java.util.Random;

import application.controllers.Controller;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CreateTask {
	
	private Pane root;
	private Image catPawImage = new Image(getClass().getResource("/application/images/catPaw.png").toExternalForm());
    private ImageView catPawImageView = new ImageView(catPawImage);  // 负责显示猫爪图片的ImageView对象
	
	public void start(Stage primaryStage) throws Exception{
		catPawImageView.setX(-360);
	
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/CreateMission.fxml"));
		root = loader.load();
		root.getChildren().add(catPawImageView);
		
		Scene scene = new Scene(root);
		
		scene.getStylesheets().add(getClass().getResource("/application/css/mainPage.css").toExternalForm());
		Controller cn = loader.getController();
		cn.getButton().setOnAction(event->{
			if(cn.submit(event).equals("success")) {
				cn.closeScene(event, primaryStage);
			}
		});
		
		primaryStage.initModality(Modality.NONE);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Task Planner");
		primaryStage.show();
		
		catAnimation();
	}
	
	private void catAnimation() {
    	Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(6), event -> {
                	// 创建TranslateTransition对象，控制X轴上的平移动画
                	TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(3), catPawImageView);
                	
                	// 设置平移的起始位置和终点位置
                	translateTransition.setFromX(-360);
                	translateTransition.setToX(240);

                	// 创建一个随机数生成器，用于生成Y轴的随机坐标
                    Random random = new Random();

                    // 设置平移的Y轴坐标生成器
                    int minY = 50;
                    int maxY = 400;
                    translateTransition.setByY(random.nextInt(maxY - minY + 1) + minY);

                    // 设置动画循环次数和自动反向播放
                    translateTransition.setCycleCount(Animation.INDEFINITE);
                    translateTransition.setAutoReverse(true);

                    // 创建一个事件处理器，在每次动画完成后更新Y轴坐标
                    translateTransition.setOnFinished(event2 -> {
                        translateTransition.setByY(random.nextInt(maxY - minY + 1) + minY);
                    });
                    translateTransition.play();
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
	
	public Parent getRoot() {
		return root;
	}
}