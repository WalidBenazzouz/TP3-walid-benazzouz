package ma.emsi.benazzouzwalid.tp3rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * Petit endpoint de demonstration pour valider le deploiement.
 */
@Path("/hello")
public class HelloResource {

    @GET
    @Path("personnes/{nom}")
    @Produces(MediaType.TEXT_PLAIN)
    public String saluer(@PathParam("nom") String nom) {
        return "Bienvenue, " + nom + " !";
    }
}
