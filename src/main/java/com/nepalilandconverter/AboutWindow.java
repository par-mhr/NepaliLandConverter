package com.nepalilandconverter;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AboutWindow {
    public static void show(Stage parentStage) {
        Stage aboutStage = new Stage();
        aboutStage.setTitle("About Nepali Land Converter");
        aboutStage.setWidth(550);
        aboutStage.setHeight(480);
        aboutStage.initOwner(parentStage);
        aboutStage.setResizable(false);

        // Create content
        VBox root = new VBox();
        root.setPadding(new Insets(20));
        root.setSpacing(15);

        // Title with Icon - using HBox to place them side by side
        HBox titleBox = new HBox();
        titleBox.setSpacing(15);
        titleBox.setAlignment(Pos.CENTER_LEFT);

        // Title (Left aligned)
        Label titleLabel = new Label("Nepali Land Converter");
        titleLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
        titleBox.getChildren().add(titleLabel);

        // Spacer to push icon to the right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        titleBox.getChildren().add(spacer);

        // App Icon (Right aligned)
        try {
            var iconResource = AboutWindow.class.getResource("/images/icon.png");
            if (iconResource != null) {
                Image iconImage = new Image(iconResource.toExternalForm());
                ImageView iconView = new ImageView(iconImage);
                iconView.setFitWidth(48);
                iconView.setFitHeight(48);
                iconView.setPreserveRatio(true);
                titleBox.getChildren().add(iconView);
            }
        } catch (Exception e) {
            System.err.println("Error loading app icon: " + e.getMessage());
        }


        root.getChildren().add(titleBox);

         // Version
         Label versionLabel = new Label("Version 1.0");
         versionLabel.setStyle("-fx-font-size: 14px;");

         // Date
         LocalDate currentDate = LocalDate.now();
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
         Label dateLabel = new Label("Released: " + currentDate.format(formatter));
         dateLabel.setStyle("-fx-font-size: 13px;");

         // Description
        Label descLabel = new Label(
            "Nepali Land Converter is a simple and efficient utility application " +
            "designed to help users convert between different land measurement units " +
            "commonly used in Nepal. Inspired by the OG \"Soft Area Converter\" app with some added features.\n\n" +
            "Features:\n" +
            "• Support for 11 different land measurement units\n" +
            "• Light and Dark theme support\n" +
            "• Copy results to clipboard\n" +
            "• Fast and accurate conversions\n\n" +
            "Suggestions and bug reports are always welcome.\n" +
            "Thank you!"
        );
        descLabel.setWrapText(true);
        descLabel.setStyle("-fx-font-size: 12px; -fx-line-spacing: 2;");

         // Developer info
         Label devLabel = new Label("Paras Maharjan\n");
         devLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold;");

         // Email hyperlink
         Hyperlink emailLink = new Hyperlink("paras.maharjan10@gmail.com");
         emailLink.setStyle("-fx-font-size: 13px; -fx-padding: 0;");
         emailLink.setOnAction(e -> openEmailClient("paras.maharjan10@gmail.com"));

          Label emailLabel = new Label("Email: ");
          emailLabel.setStyle("-fx-font-size: 13px;");

          root.getChildren().addAll(versionLabel, dateLabel, descLabel, devLabel, emailLabel, emailLink);

        Scene scene = new Scene(root);

        // Apply current theme
        String cssResource = ThemeManager.getCurrentTheme() == ThemeManager.Theme.DARK
            ? "dark-theme.css"
            : "light-theme.css";
        var themeCss = AboutWindow.class.getResource(cssResource);
        if (themeCss != null) {
            scene.getStylesheets().add(themeCss.toExternalForm());
        }

        aboutStage.setScene(scene);
        aboutStage.show();
    }

    private static void openEmailClient(String email) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                URI mailto = new URI("mailto:" + email);
                desktop.mail(mailto);
            }
        } catch (Exception e) {
            System.err.println("Error opening email client: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

