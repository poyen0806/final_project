package application.views;


import java.util.Random;

import application.controllers.DetailController;
import application.controllers.PopupWindow;
import application.models.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DetailView {
	
	private Parent root;
	private Task task;
	
	public DetailView(Task task) {
        this.task = task;
    }
	public void start(Stage primaryStage, Stage calendar) throws Exception {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/DetailView.fxml"));
    	root = loader.load();
		
		Scene scene = new Scene(root);
		
		Random random = new Random();
        int randomNumber = random.nextInt(100);

        if (randomNumber <= 20) {
            addImageToScene(scene);
        }
		
		DetailController cn = loader.getController();
		cn.setTask(task);
		cn.getButton().setOnAction(event->{
			cn.submit(event);
			cn.closeScene(event, primaryStage, calendar);
		});
		
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> PopupWindow.handleKeyPressed(event));
        primaryStage.setScene(scene);
        primaryStage.show();
    }
	
	private void addImageToScene(Scene scene) {
		Image image  = new Image(getClass().getResource("/application/images/player.png").toExternalForm());
	    ImageView imageView = new ImageView(image);

	    imageView.setX(300 - imageView.getImage().getWidth());
	    imageView.setY(400 - imageView.getImage().getHeight());

	    AnchorPane rootPane = (AnchorPane) scene.getRoot();
	    rootPane.getChildren().add(imageView);
	}

	
	public Parent getRoot() {
		return root;
	}
}

