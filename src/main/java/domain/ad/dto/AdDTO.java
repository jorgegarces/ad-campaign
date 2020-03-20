package domain.ad.dto;

import java.time.LocalDate;

public class AdDTO {
    public String title;
    public String description;
    public LocalDate date;

    @Override
    public String toString() {
        return this.date +
                " " +
                this.title +
                "\n" +
                this.description +
                "\n-------------\n";
    }
}