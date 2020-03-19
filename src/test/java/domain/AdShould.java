package domain;

import domain.ad.Ad;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class AdShould {

    @Test
    public void not_allow_building_without_a_title() {

        String expected = "";

        try {
            new Ad.AdBuilder()
                    .description("Test ad description")
                    .publicationDate(LocalDate.of(2019, 10, 1))
                    .build();
        } catch (IllegalStateException e) {
            expected = e.getMessage();
        }

        Assert.assertEquals("Title cannot be empty", expected);
    }

    @Test
    public void not_allow_building_without_a_description() {

        String expected = "";

        try {
            new Ad.AdBuilder()
                    .title("Test ad Title")
                    .publicationDate(LocalDate.of(2019, 10, 1))
                    .build();
        } catch(IllegalStateException e) {
            expected = e.getMessage();
        }

        Assert.assertEquals("Description cannot be empty", expected);
    }
}