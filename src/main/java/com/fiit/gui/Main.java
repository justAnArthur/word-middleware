package com.fiit.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("mainLayout.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 700);

        GuiController mainLayoutController = fxmlLoader.getController();
        new GameController(mainLayoutController);

        stage.setTitle("Word Middleware!");
        stage.setScene(scene);
        stage.show();
    }
}