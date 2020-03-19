package domain.ad;

import java.util.UUID;

public class AdId {

    private String id;

    public AdId() {
        this.id = UUID.randomUUID().toString();
    }
}
