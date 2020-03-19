package infrastructure;

import domain.ad.Ad;
import domain.exceptions.AdDoesNotExistException;
import domain.exceptions.DuplicateAdException;
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

    @Test
    public void not_allow_more_than_one_ad_with_same_title_and_description() throws RuntimeException {

        Ad testAd2 = new Ad.AdBuilder()
                .title("Test ad title")
                .description("Test ad description")
                .publicationDate(LocalDate.of(2019, 10, 1))
                .build();
        testRepo.add(testAd);

        Assertions.assertThrows(DuplicateAdException.class, () -> testRepo.add(testAd2));
    }

    @Test
    public void list_the_ads_in_the_catalog() {

        Ad testAd2 = new Ad.AdBuilder()
                .title("Test ad title 2")
                .description("Test ad description 2")
                .publicationDate(LocalDate.of(2005, 10, 1))
                .build();

        testRepo.add(testAd2);
        testRepo.add(testAd);


        Assert.assertEquals("2019-10-01 Test ad title\nTest ad description\n-------------\n" +
                "2005-10-01 Test ad title 2\nTest ad description 2\n-------------\n", testRepo.list().toString());
    }
}
