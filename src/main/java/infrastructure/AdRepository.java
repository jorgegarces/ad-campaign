package infrastructure;

import domain.ad.Ad;
import domain.ad.AdId;
import domain.ad.dto.AdDTO;
import domain.ad.dto.AdDTOList;

import java.time.LocalDate;

public interface AdRepository {

    void add(Ad ad);
    void remove(AdId adId);
    void purgeAdsOlderThan(LocalDate purgeDate);
    AdDTOList list();
    AdDTO get(AdId adId);
}
