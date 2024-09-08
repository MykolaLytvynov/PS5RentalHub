package app.cvs;

import app.config.ConfigManager;
import app.model.Rental;
import app.util.DateUtil;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RentalCSVManager {
    private static final String TITLE_FOR_RENTALS = "Початок оренди,Кінець оренди,Ім'я замовника," +
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
    private void createFileWithHeader(String filePath) {
        File file = new File(filePath);
        // Створення файлу, якщо він не існує
        if (!file.exists()) {
            try (FileWriter fileWriter = new FileWriter(filePath);
                 BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                 PrintWriter printWriter = new PrintWriter(bufferedWriter)) {
                // Запис заголовків у файл
                printWriter.println(TITLE_FOR_RENTALS);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void saveActualRentalToCSV(Rental rental) {
        String actualRentalsPath = getActualRentalsPath();
        saveToCSV(rental, actualRentalsPath);
    }

    public void saveToCSV(Rental rental, String filePath) {
        createFileWithHeader(filePath);
        try (FileWriter fileWriter = new FileWriter(filePath, true);
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

    public List<Rental> readActualRentalsFromCSV() {
        String actualRentalsPath = getActualRentalsPath();
        return readAllFromCSV(actualRentalsPath);
    }

    public List<Rental> readAllFromCSV(String filePath) {
        createFileWithHeader(filePath);
        List<Rental> rentals = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
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

    private String getHistoryPath() {
        return new StringBuilder()
                .append(ConfigManager.getConfigValue("csv.file.path"))
                .append("/history.csv")
                .toString();
    }

    private String getActualRentalsPath() {
        return new StringBuilder()
                .append(ConfigManager.getConfigValue("csv.file.path"))
                .append("/actual-rentals.csv")
                .toString();
    }

    public void refreshRentals() {
        List<Rental> checkingActualRentals = readActualRentalsFromCSV();
        if (!checkingActualRentals.isEmpty()) {
            List<Rental> checkedActualRentals = new ArrayList<>();

            Date today = DateUtil.getTodayWithoutTime();

            for (Rental checkingRental : checkingActualRentals) {
                if (checkingRental.getEnd().before(today)) {
                    saveToCSV(checkingRental, getHistoryPath());
                } else checkedActualRentals.add(checkingRental);
            }

            // Видаляємо старий файл
            String actualRentalsPath = getActualRentalsPath();
            File actualRentalsFile = new File(actualRentalsPath);
            actualRentalsFile.delete();

            // Перезаписуємо файл новими значеннями через stream
            checkedActualRentals.stream().forEach(this::saveActualRentalToCSV);
        }
    }

}

