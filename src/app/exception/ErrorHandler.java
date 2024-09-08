package app.exception;

import javax.swing.*;

public class ErrorHandler {

    public static void handleError(String message) {
        JOptionPane.showMessageDialog(null,
                "Помилка: " + message,
                "Критична помилка",
                JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }
}
