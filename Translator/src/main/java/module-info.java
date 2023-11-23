module com.example.translator {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires freetts;

    opens com.example.translator to javafx.fxml, javafx.graphics;

    exports com.example.translator;
}
