package app;

import app.cvs.RentalCSVManager;
import app.window.MainWindow;

public class PS5RentalHubApp {
    public static void main(String[] args) {
        RentalCSVManager.getInstance().refreshRentals();
        MainWindow mainWindow = new MainWindow();
        mainWindow.active();
    }
}
