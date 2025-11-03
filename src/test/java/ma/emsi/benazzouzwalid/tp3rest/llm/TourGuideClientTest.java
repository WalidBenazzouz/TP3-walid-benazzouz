package ma.emsi.benazzouzwalid.tp3rest.llm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TourGuideClientTest {

    @Test
    void shouldProvideFallbackJsonWhenModelUnavailable() {
        TourGuideClient client = new TourGuideClient();

        String json = client.obtenirItineraire("casablanca", 2);

        assertTrue(json.contains("\"ville_ou_pays\":\"Casablanca\""));
        assertTrue(json.contains("\"mode\":\"fallback\""));
        assertTrue(json.contains("\"endroits_a_visiter\""));
    }

    @Test
    void fallbackShouldRepeatSuggestionsToReachRequestedCount() {
        TourGuideClient client = new TourGuideClient();

        String json = client.obtenirItineraire("Rabat", 5);

        int start = json.indexOf('[');
        int end = json.indexOf(']', start);
        String arraySegment = json.substring(start + 1, end);
        int elementCount = arraySegment.isBlank() ? 0 : arraySegment.split(",").length;

        assertEquals(5, elementCount);
    }
}
