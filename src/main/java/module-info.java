module lk.acpt.citysuper {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;

    requires java.sql;
    requires com.zaxxer.hikari;
    requires org.slf4j;
    requires mysql.connector.j;
    requires static lombok;

    // Allow JavaFX to load controllers
    opens lk.acpt.citysuper.controller to javafx.fxml;

    // Allow JavaFX TableView to access DTO getters via reflection
    opens lk.acpt.citysuper.dto to javafx.base;

    // Export main package
    exports lk.acpt.citysuper;
}
