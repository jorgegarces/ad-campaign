package domain.ad.dto;

import java.util.ArrayList;

public class AdDTOList {

    ArrayList<AdDTO> adDTOlist = new ArrayList<>();

    public void add(AdDTO dto) {
        adDTOlist.add(dto);
    }

    @Override
    public String toString() {

        adDTOlist.sort((a, b) -> b.date.compareTo(a.date));

        StringBuilder formattedAdDTOList = new StringBuilder();

        for (AdDTO adDTO : adDTOlist) {
            formattedAdDTOList.append(adDTO.date)
                    .append(" ")
                    .append(adDTO.title)
                    .append("\n")
                    .append(adDTO.description)
                    .append("\n-------------\n");
        }

        return formattedAdDTOList.toString();
    }

}
