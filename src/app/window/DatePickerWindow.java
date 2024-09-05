package app.window;

import app.service.ConsoleService;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

public class DatePickerWindow {
    private final JFrame dateFrame = new JFrame("Вибір дати");
    private final ConsoleService consoleService = ConsoleService.getInstance();

    public DatePickerWindow() {
        dateFrame.setSize(500, 300);
        dateFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dateFrame.setLayout(new GridLayout(5, 3));
        dateFrame.setLocationRelativeTo(null);

        JSpinner startDateSpinner = createSpinner();
        JSpinner endDateSpinner = createSpinner();

        JButton confirmButton = new JButton("Підтвердити");
        confirmButton.addActionListener(e -> handleConfirm(startDateSpinner, endDateSpinner));

        dateFrame.add(new JLabel("Початок оренди:"));
        dateFrame.add(startDateSpinner, Component.CENTER_ALIGNMENT);
        dateFrame.add(new JLabel("Кінець оренди:"));
        dateFrame.add(endDateSpinner, Component.RIGHT_ALIGNMENT);
        dateFrame.add(confirmButton);
    }

    public void active() {
        dateFrame.setVisible(true);
    }

    private JSpinner createSpinner() {
        // Встановлюємо початковий час на початок дня (00:00:00)
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Date date = calendar.getTime();
        SpinnerDateModel dateModel = new SpinnerDateModel(date, null, null, Calendar.DAY_OF_MONTH);

        JSpinner spinner = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinner, "dd-MM-yyyy");
        spinner.setEditor(dateEditor);

        return spinner;
    }

    private void handleConfirm(JSpinner startDateSpinner, JSpinner endDateSpinner) {
        Date startDate = ((SpinnerDateModel) startDateSpinner.getModel()).getDate();
        Date endDate = ((SpinnerDateModel) endDateSpinner.getModel()).getDate();

        Optional<String> freeConsole = consoleService.getFreeConsole(startDate, endDate);

        freeConsole.ifPresentOrElse(
                console -> {
                    RequestHandlerWindow requestHandlerWindow = new RequestHandlerWindow();
                    requestHandlerWindow.request(startDate, endDate, freeConsole.get());
                },
                () -> JOptionPane.showMessageDialog(dateFrame,
                        "Немає вільної консолі",
                        "Немає вільної консолі",
                        JOptionPane.WARNING_MESSAGE)
        );

        // Закриття вікна після підтвердження
        dateFrame.dispose();
    }
}
