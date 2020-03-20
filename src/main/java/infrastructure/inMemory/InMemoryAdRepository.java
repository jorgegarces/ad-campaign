package infrastructure.inMemory;

import domain.ad.Ad;
import domain.ad.AdId;
import domain.ad.dto.AdDTO;
import domain.ad.dto.AdDTOList;
import domain.exceptions.AdDoesNotExistException;
import domain.exceptions.DuplicateAdException;
import infrastructure.AdRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.DoubleToIntFunction;

public class InMemoryAdRepository implements AdRepository {

    private ArrayList<Ad> catalog = new ArrayList<Ad>();

    protected void sortCatalogByDate() {
        catalog.sort((a, b) -> b.getDate().compareTo(a.getDate()));
    }

    protected void sortCatalogByVisits() {
        catalog.sort(Ad.VISIT_SORT);
    }

    @Override
    public void add(Ad newAd) {
        for (Ad ad : catalog) {
            if (ad.equals(newAd)) throw new DuplicateAdException();
        }

        if (catalog.size() == 100) {
            this.sortCatalogByDate();
            catalog.remove(catalog.size() -1);
        }

        catalog.add(newAd);
    }

    @Override
    public void remove(AdId adId) throws RuntimeException {
        if (!catalog.removeIf(ad -> ad.getId() == adId)) throw new AdDoesNotExistException();
    }

    @Override
    public void purgeAdsOlderThan(LocalDate purgeDate) {
        catalog.removeIf(ad -> ad.getDate().compareTo(purgeDate) > 0);
    }

    @Override
    public AdDTOList list() {
        sortCatalogByDate();
        AdDTOList adDTOList = new AdDTOList();
        for (Ad ad : catalog) adDTOList.add(ad.createDTO());

        return adDTOList;
    }

    @Override
    public AdDTO get(AdId adId) throws RuntimeException {
        for (Ad ad : catalog) {
            if (ad.getId() == adId) return ad.createDTO();
        }
        return null;
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

    @Override
    public String toString() {
        return "InMemoryAdRepository{" +
                "catalog=" + catalog +
                '}';
    }
}