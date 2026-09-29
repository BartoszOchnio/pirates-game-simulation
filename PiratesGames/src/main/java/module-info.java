module com.example.piratesgames {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;

    opens com.example.piratesgames to javafx.fxml;
    opens com.example.piratesgames.conf to com.fasterxml.jackson.databind;

    exports com.example.piratesgames;
    exports com.example.piratesgames.conf;
}