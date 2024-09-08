package app.window;

import app.service.ConsoleService;
import app.util.DateUtil;

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
        Date today = DateUtil.getTodayWithoutTime();
        SpinnerDateModel dateModel = new SpinnerDateModel(today, today, null, Calendar.DAY_OF_MONTH);

        JSpinner spinner = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinner, "dd-MM-yyyy");
        spinner.setEditor(dateEditor);

        return spinner;
    }

    private void handleConfirm(JSpinner startDateSpinner, JSpinner endDateSpinner) {
        Date startDate = ((SpinnerDateModel) startDateSpinner.getModel()).getDate();
        Date endDate = ((SpinnerDateModel) endDateSpinner.getModel()).getDate();

        if(!validateDates(startDate, endDate)) return;

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

    private boolean validateDates(Date startDate, Date endDate) {
        if (startDate.after(endDate)) {
            JOptionPane.showMessageDialog(dateFrame,
                    "Кінець оренди не повинен бути раніше ніж початок оренди.",
                    "Помилка",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }
}
