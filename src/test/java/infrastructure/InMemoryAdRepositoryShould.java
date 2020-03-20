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
            .description("Test ad description 4")
            .publicationDate(LocalDate.of(2019, 10, 4))
            .build();

    AdRepository testRepo = new InMemoryAdRepository();
    AdRepository expectedRepo = new InMemoryAdRepository();
    Country country = Country.DATE_COUNTRY;

    @Test
    public void add_and_remove_ads() {

        testRepo.add(testAd, country);
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
        testRepo.add(testAd, country);

        Assertions.assertThrows(DuplicateAdException.class, () -> testRepo.add(sameDataAd, country));
    }

    @Test
    public void list_the_ads_in_the_catalog() {

        testRepo.add(testAd2, country);
        testRepo.add(testAd, country);

        Assert.assertEquals("2019-10-02 Test ad title 2\nTest ad description 2\n-------------\n" +
                "2019-10-01 Test ad title\nTest ad description\n-------------\n", testRepo.list().toString());
    }

    @Test
    public void purge_ads_older_than_a_given_date_not_including_given_day() {

        testRepo.add(testAd2, country);
        testRepo.add(testAd4, country);
        testRepo.add(testAd, country);
        testRepo.add(testAd3, country);
        expectedRepo.add(testAd2, country);
        expectedRepo.add(testAd, country);

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

        for (int i = 0; i < 100; i++) {
            Ad randomAd = new Ad.AdBuilder()
                    .title("Loop ad Title")
                    .description(RandomStringUtils.random(length, useLetters, useNumbers))
                    .publicationDate(LocalDate.of(1490, 12, 10))
                    .build();
            expectedRepo.add(randomAd, country);
        }

        expectedRepo.add(oldAd, country);
        expectedRepo.add(testAd, country);

        Assert.assertNull(expectedRepo.get(oldAd.getId()));
    }

    @Test
    public void return_an_ad_by_its_id() {

        testRepo.add(testAd, country);

        Assert.assertEquals("2019-10-01 Test ad title\nTest ad description\n-------------\n", testRepo.get(testAd.getId()).toString());
    }

    @Test
    public void increase_an_ads_visits_counter_whenever_it_is_requested() {

        testRepo.add(testAd, country);
        testRepo.get(testAd.getId());
        AdDTO dto = testRepo.get(testAd.getId());

        Assert.assertEquals(2, dto.visits);
    }

    @Test
    public void remove_less_visited_ad_when_a_new_one_is_added_on_maxed_out_capacity_of_100() {

        country = Country.VISITS_COUNTRY;
        int length = 5;
        boolean useLetters = true;
        boolean useNumbers = false;

        for (int i = 0; i < 100; i++) {
            Ad randomAd = new Ad.AdBuilder()
                    .title("Loop ad Title")
                    .description(RandomStringUtils.random(length, useLetters, useNumbers))
                    .publicationDate(LocalDate.of(1490, 12, 10))
                    .build();
            expectedRepo.add(randomAd, country);
            expectedRepo.get(randomAd.getId());
        }

        expectedRepo.add(testAd, country);
        expectedRepo.add(testAd2, country);

        Assert.assertNull(expectedRepo.get(testAd.getId()));
    }

}
