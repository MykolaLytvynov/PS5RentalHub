package app.window;

import app.model.Rental;
import app.service.RentalService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class ActualRentalsWindow {
    private final JFrame actualRentalsFrame = new JFrame("Актуальні оренди");
    private final RentalService rentalService = RentalService.getInstance();
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
    private static final String[] COLUMN_NAMES = {
                                            "Початок оренди",
                                            "Кінець оренди",
                                            "Ім'я замовника",
                                            "Телефон",
                                            "Серійний номер консолі"
                                            };

    public ActualRentalsWindow() {
        actualRentalsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        actualRentalsFrame.setSize(900, 600);
        actualRentalsFrame.setLocationRelativeTo(null);

        // Дані для таблиці
        Object[][] data = getDataOfRentals();
        DefaultTableModel model = new DefaultTableModel(data, COLUMN_NAMES);
        JTable table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        actualRentalsFrame.add(scrollPane, BorderLayout.CENTER);
    }

    public void active() {
        actualRentalsFrame.setVisible(true);
    }

    private Object[][] getDataOfRentals() {
        List<Rental> rentals = rentalService.getActualRentals();
        Object[][] data = new Object[rentals.size()][COLUMN_NAMES.length];

        int count = 0;
        for (Rental rental : rentals) {
            data[count][0] = dateFormatter.format(rental.getEnd());
            data[count][1] = dateFormatter.format(rental.getStart());
            data[count][2] = rental.getEmployeeName();
            data[count][3] = rental.getPhone();
            data[count][4] = rental.getSerialNumberOfConsole();
            count++;
        }
        return data;
    }
}
