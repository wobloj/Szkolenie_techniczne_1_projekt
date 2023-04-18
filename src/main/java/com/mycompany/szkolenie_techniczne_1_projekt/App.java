package com.mycompany.szkolenie_techniczne_1_projekt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.image.Image;


/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"),600, 400);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Kantor walut");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        stage.show();
        String css = this.getClass().getResource("styles/style.css").toExternalForm();
        scene.getStylesheets().add(css);
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}