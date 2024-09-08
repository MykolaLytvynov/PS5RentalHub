package app.window;

import app.model.Rental;
import app.service.RentalService;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.regex.Pattern;

public class RequestHandlerWindow {

    private final RentalService rentalService = RentalService.getInstance();
    private final JFrame requestFrame;
    private JTextField phoneNumberField;
    private JTextField nameField;
    private JTextField passportField;
    private Date start;
    private Date end;
    private String serialNumberOfConsole;
    private static final String OLD_PASSPORT_REGEX = "^[A-Za-z]{2}\\d{6}$";
    private static final String NEW_PASSPORT_REGEX = "^\\d{9}$";

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

        if (!checkPassport(passportId)) return;

        if (!checkEnteredData(rental)) return;

        rentalService.add(rental);

        JOptionPane.showMessageDialog(requestFrame,
                "Оренда оформлена на ім'я: " + name,
                "Успіх",
                JOptionPane.INFORMATION_MESSAGE);

        requestFrame.dispose();
    }

    private boolean checkPassport(String passportId) {
        if(!Pattern.matches(OLD_PASSPORT_REGEX, passportId)
                && !Pattern.matches(NEW_PASSPORT_REGEX, passportId)) {
            JOptionPane.showMessageDialog(requestFrame,
                    "Паспорт неможливий",
                    "Помилка",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean checkEnteredData(Rental rental) {

        String message = """
                Перевірте дані
                
                Номер телефону: %s;
                Ім'я: %s;
                Паспортні дані: %s
                
                Продовжити?
                """.formatted(
                        rental.getPhone(),
                        rental.getEmployeeName(),
                        rental.getPassportId()
        );

        int response = JOptionPane.showConfirmDialog(requestFrame,
                message,
                "Перевірка",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        return response == JOptionPane.OK_OPTION;
    }
}
