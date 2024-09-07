package app.service;

import app.cvs.RentalCSVManager;
import app.model.Rental;

import java.util.List;

public class RentalService {
    private static RentalService rentalService;
    private final RentalCSVManager csvManager = RentalCSVManager.getInstance();

    private RentalService() {
    }

    public static RentalService getInstance() {
        if (rentalService == null) {
            rentalService = new RentalService();
        }
        return rentalService;
    }

    public void add(Rental rental) {
        RentalCSVManager.getInstance().saveActualRentalToCSV(rental);
    }

    public List<Rental> getActualRentals() {
        return csvManager.readActualRentalsFromCSV();
    }
}
