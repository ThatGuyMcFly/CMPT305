module com.example.milestone2 {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.milestone2 to javafx.fxml;
    exports com.example.milestone2;
}