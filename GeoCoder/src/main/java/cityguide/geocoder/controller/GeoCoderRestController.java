package cityguide.geocoder.controller;

import java.util.List;

import cityguide.geocoder.dto.Suggestion;

public interface GeoCoderRestController {

    public List<Suggestion> getSuggestions(String objectAddress);
}
