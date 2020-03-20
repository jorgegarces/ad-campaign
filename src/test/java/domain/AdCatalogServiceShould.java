package domain;

import domain.ad.Ad;
import infrastructure.AdRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.mockito.Mockito.verify;


public class AdCatalogServiceShould {

    @Mock
    AdRepository adRepository;

    @InjectMocks
    AdCatalogService adCatalogService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    Ad testAd = new Ad.AdBuilder()
            .title("Test ad title")
            .description("Test ad description")
            .publicationDate(LocalDate.of(2019, 10, 1))
            .build();


    @Test
    public void command_repo_to_save_a_new_ad() {

        adCatalogService.newAd(testAd);

        verify(adRepository).add(testAd);
    }

    @Test
    public void command_repo_to_remove_an_ad() {

        adCatalogService.removeAd(testAd.getId());

        verify(adRepository).remove(testAd.getId());
    }
}
