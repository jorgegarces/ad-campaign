package domain;

import domain.ad.Ad;
import domain.ad.AdId;
import infrastructure.AdRepository;
import infrastructure.Country;

import java.time.LocalDate;

public class AdCatalogService {

    private  AdRepository adRepository;

    public AdCatalogService(AdRepository adRepository) {
        this.adRepository = adRepository;
    }

    public void newAd(Ad ad, Country country) {
        adRepository.add(ad, country);
    }

    public void removeAd(AdId id) {
        adRepository.remove(id);
    }

    public void purgeFromDate(LocalDate purgeDate) {
        adRepository.purgeAdsOlderThan(purgeDate);
    }

    public void retrieveAds() {
        adRepository.list();
    }
}
