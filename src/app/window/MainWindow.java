package app.window;

import javax.swing.*;
import java.awt.*;

public class MainWindow {
    private final JFrame mainFrame;

    public MainWindow() {
        mainFrame = new JFrame("PS5 Rental Hub");
        mainFrame.setSize(600, 400);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new GridLayout(3, 1));
        mainFrame.setLocationRelativeTo(null);
        createButtons();
    }

    public void active() {
        mainFrame.setVisible(true);
    }

    private void createButtons() {
        JButton checkFreeConsoleButton = new JButton("Наявність вільної консолі");
        JButton getActualRentalsButton = new JButton("Актуальні оренди");
        JButton exitButton = new JButton("Вийти");
        mainFrame.add(checkFreeConsoleButton);
        mainFrame.add(getActualRentalsButton);
        mainFrame.add(exitButton);

        checkFreeConsoleButton.addActionListener(event -> {
            DatePickerWindow datePickerWindow = new DatePickerWindow();
            datePickerWindow.active();
        });

        getActualRentalsButton.addActionListener(event -> {
            ActualRentalsWindow actualRentalsWindow = new ActualRentalsWindow();
            actualRentalsWindow.active();
        });

        exitButton.addActionListener(event -> System.exit(0));
    }
}
