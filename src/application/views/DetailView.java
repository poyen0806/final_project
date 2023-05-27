package application.views;

import application.controllers.PopupWindow;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class DetailView {

	private Parent root;
	
	public void start(Stage primaryStage) throws Exception {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/DetailView.fxml"));
    	root = loader.load();
		
		Scene scene = new Scene(root);

        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> PopupWindow.handleKeyPressed(event));
        primaryStage.setScene(scene);
        primaryStage.show();
    }
	
	public Parent getRoot() {
		return root;
	}
}
