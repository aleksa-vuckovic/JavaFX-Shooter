module com.example.dz1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.dz1 to javafx.fxml;
    exports com.example.dz1;
    exports com.example.dz1.field;
    opens com.example.dz1.field to javafx.fxml;
    exports com.example.dz1.gunman;
    opens com.example.dz1.gunman to javafx.fxml;
    exports com.example.dz1.gunman.gun;
    opens com.example.dz1.gunman.gun to javafx.fxml;
    exports com.example.dz1.gunman.body;
    opens com.example.dz1.gunman.body to javafx.fxml;
    exports com.example.dz1.ui;
    opens com.example.dz1.ui to javafx.fxml;
}