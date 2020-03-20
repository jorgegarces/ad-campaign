package domain;

import domain.ad.Ad;
import domain.ad.AdId;
import infrastructure.AdRepository;

public class AdCatalogService {

    private  AdRepository adRepository;

    public AdCatalogService(AdRepository adRepository) {
        this.adRepository = adRepository;
    }

    public void newAd(Ad ad) {
        adRepository.add(ad);
    }

    public void removeAd(AdId id) {
        adRepository.remove(id);
    }
}
