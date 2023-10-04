package com.example.nepalilandconverter;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

enum ButtonType {
    PARENT, CHILD
}

class AreaButtonConfig {
    String name;
    ButtonType type; //PARENT | CHILD

    AreaButtonConfig(String name, ButtonType type) {
        this.name = name;
        this.type = type;
    }
}

public class MainController {
    @FXML
    private Label welcomeText;

    @FXML
    private AnchorPane side_anchorpane;

    @FXML
    private HBox inputHbox;

    private Map<String, JFXButton> jfxButtonList;


    @FXML
    public void initialize() {
        AreaButtonConfig[] areaButtonConfigs = {
                new AreaButtonConfig("Square Feet", ButtonType.PARENT),
                new AreaButtonConfig("Square Meter", ButtonType.PARENT),
                new AreaButtonConfig("Ropani-Aana-Paisa-Daam", ButtonType.PARENT),
                new AreaButtonConfig("Ropani", ButtonType.CHILD),
                new AreaButtonConfig("Aana", ButtonType.CHILD),
                new AreaButtonConfig("Paisa", ButtonType.CHILD),
                new AreaButtonConfig("Daam", ButtonType.CHILD),
                new AreaButtonConfig("Bigha-Kattha-Dhur", ButtonType.PARENT),
                new AreaButtonConfig("Bigha", ButtonType.CHILD),
                new AreaButtonConfig("Kattha", ButtonType.CHILD),
                new AreaButtonConfig("Dhur", ButtonType.CHILD)
        };

        this.jfxButtonList = new HashMap<>();
        int layoutY = 41;
        for (AreaButtonConfig areaButtonConfig : areaButtonConfigs) {
            JFXButton jfxButton = new JFXButton(areaButtonConfig.name);
            jfxButton.getStyleClass().add("squareFeetButton");
            jfxButton.setId(areaButtonConfig.name + "Button");
            jfxButton.setAlignment(Pos.TOP_LEFT);
            jfxButton.setPrefHeight(25);
            jfxButton.setPrefWidth(areaButtonConfig.type == ButtonType.PARENT ? 180 : 150);
            jfxButton.setLayoutX(areaButtonConfig.type == ButtonType.PARENT ? 4 : 30);
            jfxButton.setLayoutY(layoutY);
            side_anchorpane.getChildren().add(jfxButton);
            layoutY += 35;
            jfxButton.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

            jfxButtonList.put(areaButtonConfig.name, jfxButton);
        }

    }

    private JFXButton getJfxButton(String buttonName) {
        return jfxButtonList.get(buttonName);
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}