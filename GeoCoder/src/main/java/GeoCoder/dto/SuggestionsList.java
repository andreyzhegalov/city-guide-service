package GeoCoder.dto;

import java.util.ArrayList;
import java.util.List;

public class SuggestionsList {
    private List<Suggestion> suggestions;

    public SuggestionsList() {
        suggestions = new ArrayList<>();
    }

    public SuggestionsList(List<Suggestion> suggestionsList) {
        this.suggestions = suggestionsList;
    }

    public List<Suggestion> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<Suggestion> suggestions) {
        this.suggestions = suggestions;
    }

    @Override
    public String toString() {
        return "SuggestionsList{" + "suggestions = " + getSuggestions() + "}";
    }
}

