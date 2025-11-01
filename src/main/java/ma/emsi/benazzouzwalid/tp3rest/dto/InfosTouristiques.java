package ma.emsi.benazzouzwalid.tp3rest.dto;

import jakarta.json.bind.annotation.JsonbProperty;

import java.util.List;

/**
 * Record JSON-B contenant les ?l?ments attendus par le client REST.
 */
public record InfosTouristiques(
    @JsonbProperty("ville_ou_pays") String villeOuPays,
    @JsonbProperty("endroits_a_visiter") List<String> endroitsAVisiter,
    @JsonbProperty("prix_moyen_repas") String prixMoyenRepas
) {
}
