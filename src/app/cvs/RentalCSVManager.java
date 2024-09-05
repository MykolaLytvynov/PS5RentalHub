package app.cvs;

import app.config.ConfigManager;
import app.model.Rental;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RentalCSVManager {
    private static final String TITLE = "Початок оренди,Кінець оренди,Ім'я замовника," +
            "Паспортні дані,Телефон,Серійний номер консолі";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private static RentalCSVManager rentalCSVManager;

    private RentalCSVManager() {}

    public static RentalCSVManager getInstance() {
        if (rentalCSVManager == null) {
            rentalCSVManager = new RentalCSVManager();
        }
        return rentalCSVManager;
    }

    // Метод для створення файлу та запису заголовків
    private void createFileWithHeader() {
        String filePath = getPath();
        File file = new File(filePath);
        // Створення файлу, якщо він не існує
        if (!file.exists()) {
            try (FileWriter fileWriter = new FileWriter(filePath);
                 BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                 PrintWriter printWriter = new PrintWriter(bufferedWriter)) {
                // Запис заголовків у файл
                printWriter.println(TITLE);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void saveToCSV(Rental rental) {
        createFileWithHeader();
        try (FileWriter fileWriter = new FileWriter(getPath(), true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter printWriter = new PrintWriter(bufferedWriter)) {

            String csvItem = String.format("%s,%s,%s,%s,%s,%s",
                    dateFormat.format(rental.getStart()),
                    dateFormat.format(rental.getEnd()),
                    rental.getEmployeeName(),
                    rental.getPassportId(),
                    rental.getPhone(),
                    rental.getSerialNumberOfConsole());

            printWriter.println(csvItem);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Rental> readAllFromCSV() {
        createFileWithHeader();
        List<Rental> rentals = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(getPath()))) {
            String line;
            bufferedReader.readLine(); // Пропустити заголовок

            while ((line = bufferedReader.readLine()) != null) {
                Rental rental = fromCSV(line);
                rentals.add(rental);
            }

            bufferedReader.lines()
                    .map(this::fromCSV)
                    .forEach(rentals::add);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return rentals;
    }

    private Rental fromCSV(String csvLine) {
        String[] fields = csvLine.split(",");
        Date start, end;
        try {
            start = dateFormat.parse(fields[0]);
            end = dateFormat.parse(fields[1]);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        String employeeName = fields[2];
        String passportId = fields[3];
        String phone = fields[4];
        String serialNumberOfConsole = fields[5];

        return new Rental(start, end, employeeName, passportId, phone, serialNumberOfConsole);
    }

    private String getPath() {
        return new StringBuilder()
                .append(ConfigManager.getConfigValue("csv.file.path"))
                .append("/rentals.csv")
                .toString();
    }
}

