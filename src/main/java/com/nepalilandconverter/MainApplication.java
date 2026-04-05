package com.nepalilandconverter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    static {
        // Set macOS dock icon and name for the entire application
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Nepali Land Converter");
        System.setProperty("apple.awt.application.name", "Nepali Land Converter");
    }

    @Override
    public void start(Stage stage) throws IOException {
        // Initialize theme based on system preference
        ThemeManager.initializeTheme();

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Apply the shared base stylesheet first, then the active theme
        var baseCss = MainApplication.class.getResource("style.css");
        if (baseCss != null) {
            scene.getStylesheets().add(baseCss.toExternalForm());
        }

        String cssResource = ThemeManager.getCurrentTheme() == ThemeManager.Theme.DARK
            ? "dark-theme.css"
            : "light-theme.css";
        var themeCss = MainApplication.class.getResource(cssResource);
        if (themeCss != null) {
            scene.getStylesheets().add(themeCss.toExternalForm());
        }

        stage.setTitle("Nepali Land Converter");
        stage.setResizable(false);
        stage.setScene(scene);

        // Set the app icon
        try {
            var iconResource = MainApplication.class.getResource("/images/icon.png");
            if (iconResource != null) {
                stage.getIcons().add(new Image(iconResource.toExternalForm()));
            }
        } catch (Exception e) {
            System.err.println("Error loading app icon: " + e.getMessage());
        }

        // Get the controller and setup control bar handlers
        MainController controller = fxmlLoader.getController();
        controller.setupControlBar(stage);

        stage.show();
    }

    @SuppressWarnings({"unused"})
    public static void main(String[] args) {
        launch();
    }
}