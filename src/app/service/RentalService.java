package app.service;

import app.cvs.RentalCSVManager;
import app.model.Rental;

public class RentalService {
    private static RentalService rentalService;

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
}
