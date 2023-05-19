package application.views;

import application.controllers.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CreateTask {
	
	public void start(Stage primaryStage) throws Exception{
	
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/CreateMission.fxml"));
		Parent root = loader.load();
		
		Scene scene = new Scene(root);
		
		Controller cn = loader.getController();
		cn.getButton().setOnAction(event->{
			cn.submit(event);
			cn.closeScene(event, primaryStage);
		});
	
		primaryStage.initModality(Modality.NONE);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Task Planner");
		primaryStage.showAndWait();
	}
}