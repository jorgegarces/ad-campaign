package domain.ad;

import domain.ad.dto.AdDTO;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;

public class Ad {

    private final AdId id;
    private final String title;
    private final String description;
    private final LocalDate publicationDate;
    private int visits = 0;

    private Ad(AdBuilder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.publicationDate = builder.publicationDate;
    }

    public AdId getId() {
        return this.id;
    }

    public LocalDate getDate() {
        return this.publicationDate;
    }

    private void incrementVisits() {
        this.visits += 1;
    }

    public static final Comparator<Ad> VISIT_SORT = new Comparator<Ad>() {
        public int compare(Ad a, Ad b) {
            return b.visits - a.visits;
        }
    };

    public static final Comparator<Ad> DATE_SORT = new Comparator<Ad>() {
        public int compare(Ad a, Ad b) {
            return b.publicationDate.compareTo(a.publicationDate);
        }
    };

    public AdDTO createDTO() {
        this.incrementVisits();
        AdDTO adDTO = new AdDTO();
        adDTO.title = this.title;
        adDTO.description = this.description;
        adDTO.date = this.publicationDate;
        adDTO.visits = this.visits;

        return adDTO;
    }

    public static final class AdBuilder {

        private AdId id;
        private String title;
        private String description;
        private LocalDate publicationDate;

        public AdBuilder title(String title) {
            this.title = title;
            return this;
        }

        public AdBuilder description(String description) {
            this.description = description;
            return this;
        }

        public AdBuilder publicationDate(LocalDate date) {
            this.publicationDate = date;
            return this;
        }

        public Ad build() {
            this.id = new AdId();
            if (title == null) throw new IllegalStateException("Title cannot be empty");
            if (description == null) throw new IllegalStateException("Description cannot be empty");
            if (publicationDate == null) throw new IllegalStateException("Publication date cannot be empty");
            if (title.length() > 50) throw new IllegalStateException("Title must be max 50 characters long");
            if (title.equals(description)) throw new IllegalStateException("Title and description cannot be the same");
            return new Ad(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ad ad = (Ad) o;
        return Objects.equals(title, ad.title) &&
                Objects.equals(description, ad.description);
    }

    @Override
    public String toString() {
        return "Ad{" +
                "publicationDate=" + publicationDate +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description);
    }
}
