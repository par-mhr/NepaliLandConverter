package com.nepalilandconverter;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

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
    private Label currentLabel;

    @FXML
    private AnchorPane side_anchorpane;

    @FXML
    private HBox inputHbox;

    @FXML
    private HBox inputHbox1;

    @FXML
    private GridPane outputGridPane;

    // Control bar elements
    @FXML
    private CheckBox alwaysOnTopCheckBox;

    @FXML
    private JFXButton themeToggleButton;

    @FXML
    private JFXButton aboutButton;

    // Copy buttons
    @FXML
    private JFXButton copySquareFeetButton;

    @FXML
    private JFXButton copySquareMeterButton;

    @FXML
    private JFXButton copyRAPDButton;

    @FXML
    private JFXButton copyRopaniButton;

    @FXML
    private JFXButton copyAanaButton;

    @FXML
    private JFXButton copyPaisaButton;

    @FXML
    private JFXButton copyDaamButton;

    @FXML
    private JFXButton copyBKDhButton;

    @FXML
    private JFXButton copyBighaButton;

    @FXML
    private JFXButton copyKatthaButton;

    @FXML
    private JFXButton copyDhurButton;

    private Map<String, JFXButton> jfxButtonList;
    private AreaCalculator calculator;
    private String selectedConversionUnit = "Square Feet";
    
    // Store output TextFields by row index for easy access
    private final List<TextField> outputFields = new ArrayList<>();

    // Track currently selected button
    private JFXButton currentlySelectedButton = null;


    @FXML
    public void initialize() {
        calculator = new AreaCalculator();

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

            // Add action handler for button
            jfxButton.setOnAction(event -> handleUnitSelection(areaButtonConfig.name, jfxButton));

            jfxButtonList.put(areaButtonConfig.name, jfxButton);
        }

        // Collect all output TextFields from GridPane
        for (var node : outputGridPane.getChildren()) {
            if (node instanceof TextField textField) {
                outputFields.add(textField);
            }
        }

        // Setup copy button handlers
        setupCopyButtonHandlers();

        // Setup input validation for all text fields
        setupInputValidation();

        // Add change listeners to input fields
        addInputChangeListeners();

        // Sync the visible input state with the default highlighted unit
        handleUnitSelection("Square Feet", jfxButtonList.get("Square Feet"));

        // Control bar setup will be done from MainApplication after stage is shown
    }

    private void setupCopyButtonHandlers() {
        copySquareFeetButton.setOnAction(e -> copyToClipboard(outputFields.get(0)));
        copySquareMeterButton.setOnAction(e -> copyToClipboard(outputFields.get(1)));
        copyRAPDButton.setOnAction(e -> copyToClipboard(outputFields.get(2)));
        copyRopaniButton.setOnAction(e -> copyToClipboard(outputFields.get(3)));
        copyAanaButton.setOnAction(e -> copyToClipboard(outputFields.get(4)));
        copyPaisaButton.setOnAction(e -> copyToClipboard(outputFields.get(5)));
        copyDaamButton.setOnAction(e -> copyToClipboard(outputFields.get(6)));
        copyBKDhButton.setOnAction(e -> copyToClipboard(outputFields.get(7)));
        copyBighaButton.setOnAction(e -> copyToClipboard(outputFields.get(8)));
        copyKatthaButton.setOnAction(e -> copyToClipboard(outputFields.get(9)));
        copyDhurButton.setOnAction(e -> copyToClipboard(outputFields.get(10)));

        // Set initial icons based on current theme
        updateCopyButtonIcons();
    }

    private void updateCopyButtonIcons() {
        boolean isDarkMode = ThemeManager.getCurrentTheme() == ThemeManager.Theme.DARK;
        String iconFileName = isDarkMode ? "copy_dark.png" : "copy_light.png";

        try {
            var iconResource = MainController.class.getResource("/images/" + iconFileName);
            if (iconResource == null) {
                System.err.println("Icon resource not found: " + iconFileName);
                return;
            }
            String iconPath = iconResource.toExternalForm();
            Image icon = new Image(iconPath);

            // Update all copy buttons with the same icon
            copySquareFeetButton.setGraphic(new ImageView(icon));
            copySquareMeterButton.setGraphic(new ImageView(icon));
            copyRAPDButton.setGraphic(new ImageView(icon));
            copyRopaniButton.setGraphic(new ImageView(icon));
            copyAanaButton.setGraphic(new ImageView(icon));
            copyPaisaButton.setGraphic(new ImageView(icon));
            copyDaamButton.setGraphic(new ImageView(icon));
            copyBKDhButton.setGraphic(new ImageView(icon));
            copyBighaButton.setGraphic(new ImageView(icon));
            copyKatthaButton.setGraphic(new ImageView(icon));
            copyDhurButton.setGraphic(new ImageView(icon));

            // Apply sizing to all graphics
            for (JFXButton btn : new JFXButton[]{copySquareFeetButton, copySquareMeterButton, copyRAPDButton, copyRopaniButton,
                    copyAanaButton, copyPaisaButton, copyDaamButton, copyBKDhButton, copyBighaButton, copyKatthaButton, copyDhurButton}) {
                if (btn.getGraphic() instanceof ImageView iv) {
                    iv.setFitHeight(16);
                    iv.setFitWidth(16);
                    iv.setPreserveRatio(true);
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading copy button icon: " + e.getMessage());
        }
    }

    private void copyToClipboard(TextField textField) {
        String text = textField.getText();
        if (text != null && !text.isEmpty()) {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(text);
            clipboard.setContent(content);
        }
    }

    private void setupInputValidation() {
        // Apply validation to single input field
        if (!inputHbox1.getChildren().isEmpty() && inputHbox1.getChildren().get(0) instanceof TextField) {
            TextField inputField = (TextField) inputHbox1.getChildren().get(0);
            applyNumberFilter(inputField);
        }

        // Apply validation to all fields in inputHbox
        for (var child : inputHbox.getChildren()) {
            if (child instanceof TextField textField) {
                applyNumberFilter(textField);
            }
        }
    }

    private void applyNumberFilter(TextField textField) {
        TextFormatter<String> formatter = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            // Allow only numbers and decimal points
            if (newText.matches("[0-9.]*")) {
                return change;
            }
            return null;
        });
        textField.setTextFormatter(formatter);
    }

    private void setButtonSelected(JFXButton button) {
        // Remove selected style from previous button
        if (currentlySelectedButton != null) {
            currentlySelectedButton.getStyleClass().remove("squareFeetButtonSelected");
            currentlySelectedButton.getStyleClass().add("squareFeetButton");
        }

        // Add selected style to new button
        button.getStyleClass().remove("squareFeetButton");
        button.getStyleClass().add("squareFeetButtonSelected");
        currentlySelectedButton = button;
    }

     private void addInputChangeListeners() {
         // Add listener to inputHbox1 (single value input)
         if (!inputHbox1.getChildren().isEmpty()) {
             TextField singleInput = (TextField) inputHbox1.getChildren().get(0);
             singleInput.textProperty().addListener((obs, oldVal, newVal) -> handleSingleUnitConversion(newVal));
         }

         // Add listeners to inputHbox (RAPD and BKDH inputs)
         for (var child : inputHbox.getChildren()) {
             if (child instanceof TextField textField) {
                 textField.textProperty().addListener((obs, oldVal, newVal) -> handleCompositeUnitConversion());
             }
         }
     }

    private void handleSingleUnitConversion(String inputValue) {
        if (inputValue == null || inputValue.trim().isEmpty()) {
            clearOutputFields();
            return;
        }

        try {
            double value = Double.parseDouble(inputValue);
            if (value <= 0) {
                clearOutputFields();
                return;
            }

            // Convert input to square feet first
            double sqFeetValue = switch (selectedConversionUnit) {
                case "Square Feet" -> value;
                case "Square Meter" -> calculator.convertFromSquareMeter(value);
                case "Ropani" -> calculator.convertFromRopani(value);
                case "Aana" -> calculator.convertFromAnna(value);
                case "Paisa" -> calculator.convertFromPaisa(value);
                case "Daam" -> calculator.convertFromDaam(value);
                case "Bigha" -> calculator.convertFromBigha(value);
                case "Kattha" -> calculator.convertFromKattha(value);
                case "Dhur" -> calculator.convertFromDhur(value);
                default -> 0.0;
            };

            // Convert from square feet to all units
            Map<String, String> results = calculator.convertFromSquareFeet(sqFeetValue);

            // Update output fields
            if (outputFields.size() >= 11) {
                outputFields.get(0).setText(results.get("squareFeet"));
                outputFields.get(1).setText(results.get("squareMeter"));
                outputFields.get(2).setText(results.get("rapd"));
                outputFields.get(3).setText(results.get("ropani"));
                outputFields.get(4).setText(results.get("anna"));
                outputFields.get(5).setText(results.get("paisa"));
                outputFields.get(6).setText(results.get("daam"));
                outputFields.get(7).setText(results.get("bkdh"));
                outputFields.get(8).setText(results.get("bigha"));
                outputFields.get(9).setText(results.get("kattha"));
                outputFields.get(10).setText(results.get("dhur"));
            }
        } catch (NumberFormatException e) {
            clearOutputFields();
        }
    }

     private void handleCompositeUnitConversion() {
          // Get values from inputHbox TextFields
          List<TextField> inputs = new ArrayList<>();
          for (var child : inputHbox.getChildren()) {
              if (child instanceof TextField textField) {
                  // Only add visible fields
                  if (textField.isVisible()) {
                      inputs.add(textField);
                  }
              }
          }

          // For Ropani-Aana-Paisa-Daam we need 4 fields, for Bigha-Kattha-Dhur we need 3
          int requiredFields = selectedConversionUnit.equals("Bigha-Kattha-Dhur") ? 3 : 4;
          if (inputs.size() < requiredFields) {
              return;
          }

          try {
             String firstStr = inputs.get(0).getText().trim();
             String secondStr = inputs.get(1).getText().trim();
             String thirdStr = inputs.get(2).getText().trim();

             if (selectedConversionUnit.equals("Ropani-Aana-Paisa-Daam")) {
                 // For RAPD we need all 4 fields
                 if (inputs.size() < 4) {
                     clearOutputFields();
                     return;
                 }
                 String fourthStr = inputs.get(3).getText().trim();

                 if (firstStr.isEmpty() || secondStr.isEmpty() || thirdStr.isEmpty() || fourthStr.isEmpty()) {
                     clearOutputFields();
                     return;
                 }

                 int ropani = Integer.parseInt(firstStr);
                 int aana = Integer.parseInt(secondStr);
                 int paisa = Integer.parseInt(thirdStr);
                 double daam = Double.parseDouble(fourthStr);

                 double sqFeetValue = calculator.convertFromRAPD(ropani, aana, paisa, daam);

                 // Convert from square feet to all units
                 Map<String, String> results = calculator.convertFromSquareFeet(sqFeetValue);

                 // Update output fields
                 if (outputFields.size() >= 11) {
                     outputFields.get(0).setText(results.get("squareFeet"));
                     outputFields.get(1).setText(results.get("squareMeter"));
                     outputFields.get(2).setText(results.get("rapd"));
                     outputFields.get(3).setText(results.get("ropani"));
                     outputFields.get(4).setText(results.get("anna"));
                     outputFields.get(5).setText(results.get("paisa"));
                     outputFields.get(6).setText(results.get("daam"));
                     outputFields.get(7).setText(results.get("bkdh"));
                     outputFields.get(8).setText(results.get("bigha"));
                     outputFields.get(9).setText(results.get("kattha"));
                     outputFields.get(10).setText(results.get("dhur"));
                 }
             } else if (selectedConversionUnit.equals("Bigha-Kattha-Dhur")) {
                 if (firstStr.isEmpty() || secondStr.isEmpty() || thirdStr.isEmpty()) {
                     clearOutputFields();
                     return;
                 }

                 int bigha = Integer.parseInt(firstStr);
                 int kattha = Integer.parseInt(secondStr);
                 double dhur = Double.parseDouble(thirdStr);

                 double sqFeetValue = calculator.converFromBKDH(bigha, kattha, dhur);

                 // Convert from square feet to all units
                 Map<String, String> results = calculator.convertFromSquareFeet(sqFeetValue);

                 // Update output fields
                 if (outputFields.size() >= 11) {
                     outputFields.get(0).setText(results.get("squareFeet"));
                     outputFields.get(1).setText(results.get("squareMeter"));
                     outputFields.get(2).setText(results.get("rapd"));
                     outputFields.get(3).setText(results.get("ropani"));
                     outputFields.get(4).setText(results.get("anna"));
                     outputFields.get(5).setText(results.get("paisa"));
                     outputFields.get(6).setText(results.get("daam"));
                     outputFields.get(7).setText(results.get("bkdh"));
                     outputFields.get(8).setText(results.get("bigha"));
                     outputFields.get(9).setText(results.get("kattha"));
                     outputFields.get(10).setText(results.get("dhur"));
                 }
             }
         } catch (NumberFormatException e) {
             clearOutputFields();
        }
    }

    private void handleUnitSelection(String unitName, JFXButton selectedButton) {
        selectedConversionUnit = unitName;
        currentLabel.setText(unitName);

        // Highlight selected button
        setButtonSelected(selectedButton);

        // Show/hide input boxes based on unit type and set placeholder text
        if (unitName.equals("Ropani-Aana-Paisa-Daam")) {
            inputHbox.setVisible(true);
            inputHbox1.setVisible(false);

            // Show all 4 fields for Ropani-Aana-Paisa-Daam
            int fieldIndex = 0;
            for (var child : inputHbox.getChildren()) {
                if (child instanceof TextField textField) {
                    textField.setVisible(true);
                    textField.setManaged(true);
                    // Set placeholder text
                    if (fieldIndex == 0) textField.setPromptText("Ro");
                    else if (fieldIndex == 1) textField.setPromptText("Aa");
                    else if (fieldIndex == 2) textField.setPromptText("Pa");
                    else if (fieldIndex == 3) textField.setPromptText("Da");
                }
                fieldIndex++;
            }
        } else if (unitName.equals("Bigha-Kattha-Dhur")) {
            inputHbox.setVisible(true);
            inputHbox1.setVisible(false);

            // Hide the 4th field for Bigha-Kattha-Dhur (only 3 fields needed)
            int fieldIndex = 0;
            for (var child : inputHbox.getChildren()) {
                if (child instanceof TextField textField) {
                    // Hide the 4th field (index 3)
                    textField.setVisible(fieldIndex < 3);
                    textField.setManaged(fieldIndex < 3);
                    // Set placeholder text
                    if (fieldIndex == 0) textField.setPromptText("Bi");
                    else if (fieldIndex == 1) textField.setPromptText("Ka");
                    else if (fieldIndex == 2) textField.setPromptText("Dh");
                }
                fieldIndex++;
            }
        } else {
            inputHbox.setVisible(false);
            inputHbox1.setVisible(true);

            // Set placeholder text for single input field based on unit
            if (!inputHbox1.getChildren().isEmpty() && inputHbox1.getChildren().get(0) instanceof TextField) {
                TextField inputField = (TextField) inputHbox1.getChildren().get(0);
                String placeholderText = "Enter the area in " + unitName + " to convert.";
                inputField.setPromptText(placeholderText);
            }
        }

        // Clear output fields
        clearOutputFields();

         // Clear input fields
         if (inputHbox.isVisible()) {
             for (var child : inputHbox.getChildren()) {
                 if (child instanceof TextField textField) {
                     textField.clear();
                 }
             }
         } else {
             if (!inputHbox1.getChildren().isEmpty() && inputHbox1.getChildren().get(0) instanceof TextField) {
                 ((TextField) inputHbox1.getChildren().get(0)).clear();
             }
         }
    }

    private void clearOutputFields() {
        for (TextField field : outputFields) {
            field.clear();
        }
    }

    public void setupControlBar(Stage stage) {
        // Always on top checkbox handler
        if (alwaysOnTopCheckBox != null) {
            alwaysOnTopCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
                stage.setAlwaysOnTop(newVal);
            });
        }

        // Update theme icons initially
        updateThemeIcon();
        updateAboutButtonIcon();
        updateCopyButtonIcons();

        // Theme toggle button handler
        if (themeToggleButton != null) {
            themeToggleButton.setOnAction(e -> {
                Scene scene = themeToggleButton.getScene();
                ThemeManager.toggleTheme(scene);
                updateThemeIcon();
                updateAboutButtonIcon();
                updateCopyButtonIcons();
            });
        }

        // About button handler
        if (aboutButton != null) {
            aboutButton.setOnAction(e -> {
                AboutWindow.show(stage);
            });
        }
    }

    private void updateThemeIcon() {
        if (themeToggleButton == null) {
            return;
        }

        boolean isDarkMode = ThemeManager.getCurrentTheme() == ThemeManager.Theme.DARK;

        try {
            ImageView iconView;
            if (isDarkMode) {
                // In dark mode, show light_mode icon to switch to light mode
                String lightModePath = MainController.class.getResource("/images/light_mode.png").toExternalForm();
                Image lightModeIcon = new Image(lightModePath);
                iconView = new ImageView(lightModeIcon);
                System.out.println("Loading light mode icon from: " + lightModePath);
            } else {
                // In light mode, show dark_mode icon to switch to dark mode
                String darkModePath = MainController.class.getResource("/images/dark_mode.png").toExternalForm();
                Image darkModeIcon = new Image(darkModePath);
                iconView = new ImageView(darkModeIcon);
                System.out.println("Loading dark mode icon from: " + darkModePath);
            }

            iconView.setFitHeight(16);
            iconView.setFitWidth(16);
            iconView.setPreserveRatio(true);

            themeToggleButton.setGraphic(iconView);
            themeToggleButton.setText("");
            themeToggleButton.setStyle("-fx-background-color: transparent; -fx-padding: 2; -fx-background-radius: 3; -fx-border-radius: 3;");
        } catch (Exception e) {
            System.err.println("Error loading theme icon: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateAboutButtonIcon() {
        if (aboutButton == null) {
            return;
        }

        boolean isDarkMode = ThemeManager.getCurrentTheme() == ThemeManager.Theme.DARK;

        try {
            ImageView iconView;
            if (isDarkMode) {
                // In dark mode, show info_dark icon
                String infoDarkPath = MainController.class.getResource("/images/info_light.png").toExternalForm();
                Image infoDarkIcon = new Image(infoDarkPath);
                iconView = new ImageView(infoDarkIcon);
                System.out.println("Loading info dark icon from: " + infoDarkPath);
            } else {
                // In light mode, show info_light icon
                String infoLightPath = MainController.class.getResource("/images/info_dark.png").toExternalForm();
                Image infoLightIcon = new Image(infoLightPath);
                iconView = new ImageView(infoLightIcon);
                System.out.println("Loading info light icon from: " + infoLightPath);
            }

            iconView.setFitHeight(16);
            iconView.setFitWidth(16);
            iconView.setPreserveRatio(true);

            aboutButton.setGraphic(iconView);
            aboutButton.setText("");
            aboutButton.setStyle("-fx-background-color: transparent; -fx-padding: 2; -fx-background-radius: 3; -fx-border-radius: 3;");
        } catch (Exception e) {
            System.err.println("Error loading about button icon: " + e.getMessage());
            e.printStackTrace();
        }
    }
}