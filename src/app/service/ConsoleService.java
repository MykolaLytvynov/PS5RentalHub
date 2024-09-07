package app.service;

import app.config.ConfigManager;
import app.model.Rental;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ConsoleService {
    private static ConsoleService consoleService;
    private final RentalService rentalService = RentalService.getInstance();

    private ConsoleService() {
    }

    public static ConsoleService getInstance() {
        if (consoleService == null) {
            consoleService = new ConsoleService();
        }
        return consoleService;
    }

    public Set<String> getAllConsoleSerialNumbers() {
        // Отримання всіх консольних серійних номерів
        return Set.of(ConfigManager.getConfigValue("console.serial-numbers")
                        .split(";")
        );
    }

    public Optional<String> getFreeConsole(Date startDate, Date endDate) {
        Set<String> allConsoleSerialNumbers = getAllConsoleSerialNumbers();
        Set<String> usedConsoleSerialNumbers = getUsedConsoleSerialNumbers(startDate, endDate);

        return allConsoleSerialNumbers.stream()
                .filter(con -> !usedConsoleSerialNumbers.contains(con))
                .findFirst();
    }

    private Set<String> getUsedConsoleSerialNumbers(Date startDate, Date endDate) {
        List<Rental> rentals = rentalService.getActualRentals();

        // Всі серійні номери консолей, які в цей період вже зайняті
        return rentals.stream()
                .filter(rental -> (rental.getStart().equals(startDate) || rental.getEnd().equals(endDate))
                        || (rental.getStart().after(startDate) && rental.getStart().before(endDate))
                        || (rental.getEnd().after(startDate) && rental.getEnd().before(endDate)))
                .map(Rental::getSerialNumberOfConsole)
                .collect(Collectors.toSet());
    }
}
