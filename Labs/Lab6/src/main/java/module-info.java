module com.example.lab6_2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;


    opens com.example.lab6_2 to javafx.fxml;
    exports com.example.lab6_2;
}
