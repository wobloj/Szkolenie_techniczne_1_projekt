module com.mycompany.szkolenie_techniczne_1_projekt {
    requires javafx.controls;
    requires javafx.fxml;
    requires okhttp3;
    requires org.json;

    opens com.mycompany.szkolenie_techniczne_1_projekt to javafx.fxml;
    exports com.mycompany.szkolenie_techniczne_1_projekt;
}
