package infrastructure.inMemory;

import domain.ad.Ad;
import domain.ad.AdId;
import domain.ad.dto.AdDTOList;
import domain.exceptions.AdDoesNotExistException;
import domain.exceptions.DuplicateAdException;
import infrastructure.AdRepository;

import java.util.ArrayList;
import java.util.Objects;

public class InMemoryAdRepository implements AdRepository {

    private ArrayList<Ad> catalog = new ArrayList<Ad>();

    @Override
    public void add(Ad newAd) {
        for (Ad ad : catalog) {
            if (ad.equals(newAd)) throw new DuplicateAdException();
        }

        catalog.add(newAd);
    }

    @Override
    public void remove(AdId adId) throws RuntimeException {
       if (!catalog.removeIf(ad -> ad.getId() == adId)) throw new AdDoesNotExistException();
    }

    @Override
    public AdDTOList list() {
        AdDTOList adDTOList = new AdDTOList();
        for (Ad ad : catalog) adDTOList.add(ad.createDTO());

        return adDTOList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InMemoryAdRepository that = (InMemoryAdRepository) o;
        return Objects.equals(catalog, that.catalog);
    }

    @Override
    public int hashCode() {
        return Objects.hash(catalog);
    }
}
