package com.mycompany.szkolenie_techniczne_1_projekt;

import java.io.IOException;
import javafx.fxml.FXML;

public class MenuController {

    @FXML
    private void startExchange() throws IOException {
        App.setRoot("secondary");
    }
}