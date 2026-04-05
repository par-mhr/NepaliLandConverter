package com.nepalilandconverter;

import javafx.application.Platform;
import javafx.scene.Scene;

import java.nio.charset.StandardCharsets;

public class ThemeManager {
    public enum Theme {
        LIGHT, DARK
    }

    private static Theme currentTheme = Theme.LIGHT;
    private static final String LIGHT_CSS = "light-theme.css";
    private static final String DARK_CSS = "dark-theme.css";

    /**
     * Initialize theme based on system preference
     */
    public static void initializeTheme() {
        currentTheme = detectSystemTheme();
    }

    private static Theme detectSystemTheme() {
        String osName = System.getProperty("os.name", "").toLowerCase();

        if (osName.contains("mac")) {
            try {
                Process process = new ProcessBuilder("defaults", "read", "-g", "AppleInterfaceStyle").start();
                String output = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8).trim();
                int exitCode = process.waitFor();

                if (exitCode == 0 && output.toLowerCase().contains("dark")) {
                    return Theme.DARK;
                }
            } catch (Exception ignored) {
                // Fall through to the default below.
            }
        }

        String uiTheme = System.getProperty("javafx.ui.theme", "").toLowerCase();
        if (uiTheme.contains("dark")) {
            return Theme.DARK;
        }

        return Theme.LIGHT;
    }

    /**
     * Get the current theme
     */
    public static Theme getCurrentTheme() {
        return currentTheme;
    }

    /**
     * Set theme and apply to scene
     */
    public static void setTheme(Theme theme, Scene scene) {
        currentTheme = theme;
        applyTheme(scene);
    }

    /**
     * Toggle between light and dark theme
     */
    @SuppressWarnings("unused")
    public static void toggleTheme(Scene scene) {
        if (currentTheme == Theme.LIGHT) {
            setTheme(Theme.DARK, scene);
        } else {
            setTheme(Theme.LIGHT, scene);
        }
    }

    /**
     * Apply the current theme to a scene
     */
    private static void applyTheme(Scene scene) {
        Platform.runLater(() -> {
            if (scene == null) {
                return;
            }
            scene.getStylesheets().clear();

            // Always add base styles first
            var baseCss = ThemeManager.class.getResource("style.css");
            if (baseCss != null) {
                scene.getStylesheets().add(baseCss.toExternalForm());
            }

            // Then add theme-specific styles
            String cssResource = currentTheme == Theme.DARK ? DARK_CSS : LIGHT_CSS;
            var cssUrl = ThemeManager.class.getResource(cssResource);
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            }
        });
    }
}


