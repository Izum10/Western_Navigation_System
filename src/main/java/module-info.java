module com.example.wesgeosys {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires org.json;
    requires java.net.http;


    opens com.example.wesgeosys to javafx.fxml;
    exports com.example.wesgeosys;
}