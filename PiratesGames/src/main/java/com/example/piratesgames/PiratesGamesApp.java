package com.example.piratesgames;

import com.example.piratesgames.core.Matrix;
import com.example.piratesgames.core.MatrixLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PiratesGamesApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PiratesGamesApp.class.getResource("game_GUI.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1500, 1000);
        stage.setTitle("Pirates Games");
        stage.setScene(scene);

        stage.setMinWidth(1100);
        stage.setMinHeight(700);
        stage.setResizable(true);

        stage.show();
    }

    public static void main(String[] args) throws IOException {
        launch();
    }
}
