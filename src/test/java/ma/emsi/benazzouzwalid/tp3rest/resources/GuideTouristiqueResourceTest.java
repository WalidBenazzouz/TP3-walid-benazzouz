package ma.emsi.benazzouzwalid.tp3rest.resources;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import ma.emsi.benazzouzwalid.tp3rest.dto.InfosTouristiques;
import ma.emsi.benazzouzwalid.tp3rest.service.GuideTouristiqueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class GuideTouristiqueResourceTest {

    private GuideTouristiqueResource resource;

    @BeforeEach
    void setUp() {
        GuideTouristiqueService serviceStub = (lieu, nb) -> new InfosTouristiques(
            lieu,
            IntStream.rangeClosed(1, nb)
                .mapToObj(i -> "Lieu " + i)
                .toList(),
            "20 EUR");

        resource = new GuideTouristiqueResource(serviceStub);
    }

    @Test
    void shouldReturnOkResponseWithDefaultHeaders() {
        Response response = resource.recupererGuideParQuery("Paris", 3);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("*", response.getHeaderString("Access-Control-Allow-Origin"));

        InfosTouristiques body = (InfosTouristiques) response.getEntity();
        assertEquals("Paris", body.villeOuPays());
        assertEquals(List.of("Lieu 1", "Lieu 2", "Lieu 3"), body.endroitsAVisiter());
        assertEquals("20 EUR", body.prixMoyenRepas());
    }

    @Test
    void shouldApplyDefaultNumberWhenNegative() {
        Response response = resource.recupererGuideParQuery("Casablanca", -5);

        InfosTouristiques body = (InfosTouristiques) response.getEntity();
        assertEquals(2, body.endroitsAVisiter().size(), "Nombre d'endroits par d?faut incorrect");
    }

    @Test
    void shouldAcceptPathVariant() {
        Response response = resource.recupererGuideParChemin("Nice", 1);
        InfosTouristiques body = (InfosTouristiques) response.getEntity();

        assertEquals(List.of("Lieu 1"), body.endroitsAVisiter());
    }

    @Test
    void shouldRejectEmptyLieu() {
        assertThrows(WebApplicationException.class, () -> resource.recupererGuideParQuery("   ", 2));
    }
}
