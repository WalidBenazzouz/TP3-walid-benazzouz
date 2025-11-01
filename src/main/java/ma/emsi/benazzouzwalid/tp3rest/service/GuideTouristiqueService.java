package ma.emsi.benazzouzwalid.tp3rest.service;

import ma.emsi.benazzouzwalid.tp3rest.dto.InfosTouristiques;

/**
 * Service m?tier qui encapsule la logique de g?n?ration des informations touristiques.
 */
public interface GuideTouristiqueService {

    /**
     * G?n?re les informations touristiques pour un lieu donn?.
     *
     * @param lieuOuPays     Ville ou pays cibl?.
     * @param nombreEndroits Nombre d'endroits ? sugg?rer (>= 1).
     * @return la r?ponse structur?e pour l'API REST.
     */
    InfosTouristiques genererRecommandations(String lieuOuPays, int nombreEndroits);
}
