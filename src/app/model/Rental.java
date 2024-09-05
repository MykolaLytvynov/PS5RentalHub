package app.model;

import java.io.Serializable;
import java.util.Date;

public class Rental implements Serializable {
    private Date start;
    private Date end;
    private String employeeName;
    private String passportId;
    private String phone;
    private String serialNumberOfConsole;

    public Rental(Date start, Date end, String employeeName, String passportId, String phone, String serialNumberOfConsole) {
        this.start = start;
        this.end = end;
        this.employeeName = employeeName;
        this.passportId = passportId;
        this.phone = phone;
        this.serialNumberOfConsole = serialNumberOfConsole;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getPassportId() {
        return passportId;
    }

    public String getPhone() {
        return phone;
    }

    public String getSerialNumberOfConsole() {
        return serialNumberOfConsole;
    }
}
