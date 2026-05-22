package co.edu.uniquindio.manejoEventos;

import javafx.scene.Scene;
import java.net.URL;

public class ThemeManager {
    /**
     * Applies the global Material 3 stylesheet dynamically to the provided Scene.
     * @param scene The scene to style.
     */
    public static void applyTheme(Scene scene) {
        if (scene != null) {
            URL cssUrl = ThemeManager.class.getResource("/co/edu/uniquindio/manejoEventos/material3.css");
            if (cssUrl != null) {
                String cssPath = cssUrl.toExternalForm();
                if (!scene.getStylesheets().contains(cssPath)) {
                    scene.getStylesheets().add(cssPath);
                }
            } else {
                System.err.println("Warning: material3.css not found in resources!");
            }
        }
    }
}
