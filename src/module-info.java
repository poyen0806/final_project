module final_project {
    requires javafx.controls;
    requires transitive javafx.graphics;
	requires javafx.fxml;
	requires java.sql;
	requires javafx.base;

    opens application to javafx.graphics;
    opens application.controllers to javafx.fxml;

    exports application;
    exports application.controllers to javafx.fxml;
}
