import javafx.scene.Scene;

public class ThemeManager {
    private static boolean darkMode = false;

    public static void toggleTheme(Scene scene) {
        darkMode = !darkMode;
        applyTheme(scene);
    }

    public static void applyTheme(Scene scene) {
        scene.getStylesheets().clear();
        if (darkMode) {
            scene.getStylesheets().add(ThemeManager.class.getResource("/dark.css").toExternalForm());
        } else {
            scene.getStylesheets().add(ThemeManager.class.getResource("/light.css").toExternalForm());
        }
    }
}
