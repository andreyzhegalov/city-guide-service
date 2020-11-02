package cityguide.geocoder.controller;

import cityguide.geocoder.dto.SuggestionsList;

public interface GeoCoderRestController {

    public SuggestionsList getSuggestions(String objectAddress);
}
