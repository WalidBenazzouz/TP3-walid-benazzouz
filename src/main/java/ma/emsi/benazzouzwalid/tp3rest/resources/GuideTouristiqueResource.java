package ma.emsi.benazzouzwalid.tp3rest.resources;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ma.emsi.benazzouzwalid.tp3rest.dto.InfosTouristiques;
import ma.emsi.benazzouzwalid.tp3rest.service.GuideTouristiqueService;

import java.util.Objects;

/**
 * Ressource REST principale du TP : expose les endpoints de guide touristique.
 */
@Path("/guide")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class GuideTouristiqueResource {

    private static final int NB_ENDROITS_PAR_DEFAUT = 2;
    private static final int NB_ENDROITS_MAX = 10;

    @Inject
    private GuideTouristiqueService guideTouristiqueService;

    /**
     * Constructeur d'injection pour les tests unitaires.
     */
    GuideTouristiqueResource(GuideTouristiqueService guideTouristiqueService) {
        this.guideTouristiqueService = guideTouristiqueService;
    }

    /**
     * Constructeur requis par CDI.
     */
    public GuideTouristiqueResource() {
    }

    /**
     * Endpoint principal avec parametre optionnel "nb" en query string.
     */
    @GET
    @Path("lieu/{villeOuPays}")
    public Response recupererGuideParQuery(
        @PathParam("villeOuPays") String lieuOuPays,
        @QueryParam("nb") @DefaultValue("" + NB_ENDROITS_PAR_DEFAUT) int nb
    ) {
        return construireResponse(lieuOuPays, nb);
    }

    /**
     * Variante bonus : nombre d'endroits directement dans l'URL.
     */
    @GET
    @Path("lieu/{villeOuPays}/{nb}")
    public Response recupererGuideParChemin(
        @PathParam("villeOuPays") String lieuOuPays,
        @PathParam("nb") int nb
    ) {
        return construireResponse(lieuOuPays, nb);
    }

    private Response construireResponse(String lieuOuPays, int nb) {
        String lieuNettoye = nettoyerLieu(lieuOuPays);

        if (lieuNettoye.isEmpty()) {
            throw new WebApplicationException("Le nom de la ville ou du pays est obligatoire.", Response.Status.BAD_REQUEST);
        }

        int nbNormalise = normaliserNombre(nb);
        InfosTouristiques infos = service().genererRecommandations(lieuNettoye, nbNormalise);

        return Response.ok(infos)
            .header("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0")
            .header("Pragma", "no-cache")
            .header("Expires", "0")
            .header("Access-Control-Allow-Origin", "*")
            .build();
    }

    private String nettoyerLieu(String lieu) {
        return lieu == null ? "" : lieu.trim();
    }

    private int normaliserNombre(int nbDemande) {
        if (nbDemande <= 0) {
            return NB_ENDROITS_PAR_DEFAUT;
        }
        return Math.min(nbDemande, NB_ENDROITS_MAX);
    }

    private GuideTouristiqueService service() {
        return Objects.requireNonNull(guideTouristiqueService, "GuideTouristiqueService non injecte");
    }
}
