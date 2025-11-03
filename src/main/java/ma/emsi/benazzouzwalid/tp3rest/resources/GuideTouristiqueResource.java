package ma.emsi.benazzouzwalid.tp3rest.resources;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Objects;
import ma.emsi.benazzouzwalid.tp3rest.llm.TourGuideClient;

/**
 * Ressource REST exposant un guide touristique propulse par Gemini.
 */
@Path("/guide")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class GuideTouristiqueResource {

    @Inject
    private TourGuideClient tourGuideClient;

    GuideTouristiqueResource(TourGuideClient tourGuideClient) {
        this.tourGuideClient = tourGuideClient;
    }

    public GuideTouristiqueResource() {
        // requis par CDI
    }

    @GET
    @Path("lieu/{destination}")
    public String recupererGuide(
        @PathParam("destination") String destination,
        @QueryParam("nb") Integer nbEndroits
    ) {
        String lieu = validerDestination(destination);
        int nb = normaliserNombre(nbEndroits);
        return client().obtenirItineraire(lieu, nb);
    }

    private TourGuideClient client() {
        return Objects.requireNonNull(tourGuideClient, "TourGuideClient non initialise");
    }

    private String validerDestination(String destination) {
        if (destination == null || destination.isBlank()) {
            throw new WebApplicationException("La destination est obligatoire", Response.Status.BAD_REQUEST);
        }
        return destination.trim();
    }

    private int normaliserNombre(Integer nbEndroits) {
        if (nbEndroits == null) {
            return 0;
        }
        return Math.max(0, nbEndroits);
    }
}
