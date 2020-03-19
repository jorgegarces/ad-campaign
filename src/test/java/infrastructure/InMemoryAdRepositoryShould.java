package infrastructure;

import domain.ad.Ad;
import domain.exceptions.AdDoesNotExistException;
import infrastructure.inMemory.InMemoryAdRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;

public class InMemoryAdRepositoryShould {

    Ad testAd = new Ad.AdBuilder()
            .title("Test ad title")
            .description("Test ad description")
            .publicationDate(LocalDate.of(2019, 10, 1))
            .build();

    AdRepository testRepo = new InMemoryAdRepository();

    @Test
    public void add_and_remove_ads() {

        AdRepository emptyRepo = new InMemoryAdRepository();

        testRepo.add(testAd);
        testRepo.remove(testAd.getId());

        Assert.assertEquals(emptyRepo, testRepo);
    }

    @Test
    public void throw_an_error_when_trying_to_remove_an_ad_that_does_not_exist() throws RuntimeException {

        Assertions.assertThrows(AdDoesNotExistException.class, () -> testRepo.remove(testAd.getId()));
    }
}
