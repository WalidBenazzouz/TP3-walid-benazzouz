package ma.emsi.benazzouzwalid.tp3rest.resources;

import jakarta.ws.rs.WebApplicationException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import ma.emsi.benazzouzwalid.tp3rest.llm.TourGuideClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GuideTouristiqueResourceTest {

    private GuideTouristiqueResource resource;
    private AtomicReference<String> destinationCapture;
    private AtomicInteger nombreCapture;

    @BeforeEach
    void setUp() {
        destinationCapture = new AtomicReference<>();
        nombreCapture = new AtomicInteger();

        resource = new GuideTouristiqueResource(new TourGuideClient() {
            @Override
            public String obtenirItineraire(String destination, int nbEndroits) {
                destinationCapture.set(destination);
                nombreCapture.set(nbEndroits);
                return '{' + "\"destination\":\"" + destination + "\",\"nb\":" + nbEndroits + '}';
            }
        });
    }

    @Test
    void shouldTrimDestinationAndDefaultCount() {
        String response = resource.recupererGuide("  Marrakech  ", null);

        assertEquals("{\"destination\":\"Marrakech\",\"nb\":0}", response);
        assertEquals("Marrakech", destinationCapture.get());
        assertEquals(0, nombreCapture.get());
    }

    @Test
    void shouldClampNegativeCountToZero() {
        resource.recupererGuide("Rabat", -5);

        assertEquals(0, nombreCapture.get());
        assertEquals("Rabat", destinationCapture.get());
    }

    @Test
    void shouldRejectBlankDestination() {
        assertThrows(WebApplicationException.class, () -> resource.recupererGuide("   ", 2));
    }
}
