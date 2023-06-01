package application.views;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class PosterView {
    private StackPane root;
    public double width;
    public double height;
    
    public PosterView() {
        root = new StackPane(); // 初始化 root
    }
    
    public Scene getScene() {
        Image image = new Image(getClass().getResource("/application/images/poster.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        root.getChildren().add(imageView);
        width = image.getWidth();
        height = image.getHeight();
        root.setPrefSize(width, height);
        Scene scene = new Scene(root);
        return scene;
    }
}
