package infrastructure;

import domain.ad.Ad;
import domain.ad.dto.AdDTO;
import domain.exceptions.AdDoesNotExistException;
import domain.exceptions.DuplicateAdException;
import infrastructure.inMemory.InMemoryAdRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

public class InMemoryAdRepositoryShould {

    Ad testAd = new Ad.AdBuilder()
            .title("Test ad title")
            .description("Test ad description")
            .publicationDate(LocalDate.of(2019, 10, 1))
            .build();
    Ad testAd2 = new Ad.AdBuilder()
            .title("Test ad title 2")
            .description("Test ad description 2")
            .publicationDate(LocalDate.of(2019, 10, 2))
            .build();
    Ad testAd3 = new Ad.AdBuilder()
            .title("Test ad title 3")
            .description("Test ad description 3")
            .publicationDate(LocalDate.of(2019, 10, 3))
            .build();
    Ad testAd4 = new Ad.AdBuilder()
            .title("Test ad title 4")
            .description("Test ad description 5")
            .publicationDate(LocalDate.of(2019, 10, 4))
            .build();

    AdRepository testRepo = new InMemoryAdRepository();
    AdRepository expectedRepo = new InMemoryAdRepository();

    @Test
    public void add_and_remove_ads() {

        testRepo.add(testAd);
        testRepo.remove(testAd.getId());

        Assert.assertEquals(expectedRepo, testRepo);
    }

    @Test
    public void throw_an_error_when_trying_to_remove_an_ad_that_does_not_exist() throws RuntimeException {

        Assertions.assertThrows(AdDoesNotExistException.class, () -> testRepo.remove(testAd.getId()));
    }

    @Test
    public void not_allow_more_than_one_ad_with_same_title_and_description() throws RuntimeException {

        Ad sameDataAd = new Ad.AdBuilder()
                .title("Test ad title")
                .description("Test ad description")
                .publicationDate(LocalDate.of(2019, 10, 1))
                .build();
        testRepo.add(testAd);

        Assertions.assertThrows(DuplicateAdException.class, () -> testRepo.add(sameDataAd));
    }

    @Test
    public void list_the_ads_in_the_catalog() {

        testRepo.add(testAd2);
        testRepo.add(testAd);

        Assert.assertEquals("2019-10-02 Test ad title 2\nTest ad description 2\n-------------\n" +
                "2019-10-01 Test ad title\nTest ad description\n-------------\n", testRepo.list().toString());
    }

    @Test
    public void purge_ads_older_than_a_given_date_not_including_given_day() {

        testRepo.add(testAd2);
        testRepo.add(testAd4);
        testRepo.add(testAd);
        testRepo.add(testAd3);
        expectedRepo.add(testAd2);
        expectedRepo.add(testAd);

        testRepo.purgeAdsOlderThan(LocalDate.of(2019, 10, 2));

        Assert.assertEquals(expectedRepo, testRepo);
    }

    @Test
    public void remove_oldest_ad_when_a_new_one_is_added_on_maxed_out_capacity_of_100() {

        int length = 5;
        boolean useLetters = true;
        boolean useNumbers = false;

        Ad oldAd = new Ad.AdBuilder()
                .title("Old test ad title")
                .description("Old test ad description")
                .publicationDate(LocalDate.of(1490, 12, 9))
                .build();

        for (int i = 0; i < 99; i++) {
            Ad randomAd = new Ad.AdBuilder()
                    .title("Loop ad Title")
                    .description(RandomStringUtils.random(length, useLetters, useNumbers))
                    .publicationDate(LocalDate.of(1490, 12, 10))
                    .build();
            testRepo.add(randomAd);
        }

        testRepo.add(oldAd);
        testRepo.add(testAd);
        testRepo.purgeAdsOlderThan(LocalDate.of(1490, 12, 9));

        Assert.assertEquals(expectedRepo, testRepo);
    }

    @Test
    public void return_an_ad_by_its_id() {

        testRepo.add(testAd);

        Assert.assertEquals("2019-10-01 Test ad title\nTest ad description\n-------------\n", testRepo.get(testAd.getId()).toString());
    }

    @Test
    public void increase_an_ads_visits_counter_whenever_it_is_requested() {

        testRepo.add(testAd);
        testRepo.get(testAd.getId());
        AdDTO dto = testRepo.get(testAd.getId());

        Assert.assertEquals(2, dto.visits);
    }

}
