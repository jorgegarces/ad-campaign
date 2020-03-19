package infrastructure;

import domain.ad.Ad;
import domain.ad.AdId;
import domain.ad.dto.AdDTOList;

public interface AdRepository {

    void add(Ad ad);
    void remove(AdId adId);
    AdDTOList list();

}
