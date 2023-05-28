package application.views;


import application.controllers.DetailController;
import application.controllers.PopupWindow;
import application.models.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class DetailView {
	
	private Parent root;
	private Task task;
	
	public DetailView(Task task) {
        this.task = task;
    }
	public void start(Stage primaryStage) throws Exception {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/DetailView.fxml"));
    	root = loader.load();
		
		Scene scene = new Scene(root);
		
		DetailController cn = loader.getController();
		cn.setTask(task);
		cn.getButton().setOnAction(event->{
			cn.submit(event);
			cn.closeScene(event, primaryStage);
		});
		
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> PopupWindow.handleKeyPressed(event));
        primaryStage.setScene(scene);
        primaryStage.show();
    }
	
	public Parent getRoot() {
		return root;
	}
}

