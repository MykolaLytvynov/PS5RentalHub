package app.window;

import app.model.Rental;
import app.service.RentalService;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class RequestHandlerWindow {

    private final RentalService rentalService = RentalService.getInstance();
    private final JFrame requestFrame;
    private JTextField phoneNumberField;
    private JTextField nameField;
    private JTextField passportField;
    private Date start;
    private Date end;
    private String serialNumberOfConsole;

    public RequestHandlerWindow() {
        requestFrame = new JFrame("Введіть дані для оренди");
        requestFrame.setSize(400, 200);
        requestFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        requestFrame.setLayout(new GridLayout(4, 2));
        requestFrame.setLocationRelativeTo(null);

        createFormFields();
        createConfirmButton();
    }

    public void request(Date start, Date end, String serialNumberOfConsole) {
        requestFrame.setVisible(true);
        this.start = start;
        this.end = end;
        this.serialNumberOfConsole = serialNumberOfConsole;
    }

    private void createFormFields() {
        phoneNumberField = new JTextField();
        nameField = new JTextField();
        passportField = new JTextField();

        requestFrame.add(new JLabel("Номер телефону:"));
        requestFrame.add(phoneNumberField);
        requestFrame.add(new JLabel("Имя:"));
        requestFrame.add(nameField);
        requestFrame.add(new JLabel("Паспортні дані:"));
        requestFrame.add(passportField);
    }

    private void createConfirmButton() {
        JButton confirmButton = new JButton("Підтвердити");
        confirmButton.addActionListener(e -> handleConfirm());
        requestFrame.add(confirmButton);
    }

    private void handleConfirm() {
        String phoneNumber = phoneNumberField.getText();
        String name = nameField.getText();
        String passportId = passportField.getText();

        Rental rental = new Rental(start, end, name, passportId,
                phoneNumber, serialNumberOfConsole);

        rentalService.add(rental);

        JOptionPane.showMessageDialog(requestFrame,
                "Оренда оформлена на ім'я: " + name,
                "Успіх",
                JOptionPane.INFORMATION_MESSAGE);

        requestFrame.dispose();
    }
}
