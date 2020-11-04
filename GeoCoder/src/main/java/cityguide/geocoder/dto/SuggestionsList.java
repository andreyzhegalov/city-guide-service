package cityguide.geocoder.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SuggestionsList {
    private List<Suggestion> suggestions = new ArrayList<>();
}
