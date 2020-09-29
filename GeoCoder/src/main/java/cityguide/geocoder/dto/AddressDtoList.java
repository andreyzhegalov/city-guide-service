package cityguide.geocoder.dto;

import java.util.List;

public class AddressDtoList {
    private List<AddressDto> addressDtoList;

    public AddressDtoList() {
    }

    public void setAddressDtoList(List<AddressDto> addressDtoList) {
        this.addressDtoList = addressDtoList;
    }

    public List<AddressDto> getAddressDtoList() {
        return addressDtoList;
    }

}

