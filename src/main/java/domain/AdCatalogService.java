package domain;

import domain.ad.Ad;
import domain.exceptions.DuplicateAdException;
import infrastructure.AdRepository;

public class AdCatalogService {

    private  AdRepository adRepository;

    public AdCatalogService(AdRepository adRepository) {
        this.adRepository = adRepository;
    }

    public void newAd(Ad ad) {
        adRepository.add(ad);
    }
}
