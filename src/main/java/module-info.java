module com.example.dz1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.dz1 to javafx.fxml;
    exports com.example.dz1;
    exports com.example.dz1.fields;
    opens com.example.dz1.fields to javafx.fxml;
}