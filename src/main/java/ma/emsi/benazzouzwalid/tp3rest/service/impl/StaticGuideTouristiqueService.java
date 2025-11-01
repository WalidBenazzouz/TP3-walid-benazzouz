package ma.emsi.benazzouzwalid.tp3rest.service.impl;

import ma.emsi.benazzouzwalid.tp3rest.dto.InfosTouristiques;
import ma.emsi.benazzouzwalid.tp3rest.service.GuideTouristiqueService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import static java.util.Map.entry;

/**
 * Mode degrade : fournit quelques recommandations statiques quand le LLM n'est pas disponible.
 */
final class StaticGuideTouristiqueService implements GuideTouristiqueService {

    private static final Map<String, LieuData> DONNEES = Map.ofEntries(
        entry("paris", new LieuData("Paris", List.of("Tour Eiffel", "Musee du Louvre", "Montmartre"), "25 EUR")),
        entry("maroc", new LieuData("Maroc", List.of("Place Jemaa el-Fna", "Jardins Majorelle", "Volubilis"), "120 MAD")),
        entry("casablanca", new LieuData("Casablanca", List.of("Mosquee Hassan II", "Quartier Habous", "Corniche d'Ain Diab"), "90 MAD")),
        entry("nice", new LieuData("Nice", List.of("Promenade des Anglais", "Vieux Nice", "Colline du Chateau"), "20 EUR")),
        entry("marrakech", new LieuData("Marrakech", List.of("Medina", "Palais Bahia", "Jardins de la Menara"), "110 MAD")),
        entry("londres", new LieuData("Londres", List.of("British Museum", "Tower Bridge", "Hyde Park"), "18 GBP"))
    );

    @Override
    public InfosTouristiques genererRecommandations(String lieuOuPays, int nombreEndroits) {
        int nb = Math.max(1, nombreEndroits);
        LieuData data = Optional.ofNullable(lieuOuPays)
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .map(s -> DONNEES.get(s.toLowerCase(Locale.ROOT)))
            .orElseGet(() -> new LieuData(capitaliser(lieuOuPays), List.of(
                "Office du tourisme local",
                "Principale place centrale",
                "Musee emblematique"),
                "20 EUR"));

        List<String> suggestions = new ArrayList<>(data.endroits());
        if (suggestions.isEmpty()) {
            suggestions.add("Office du tourisme local");
        }

        if (nb < suggestions.size()) {
            suggestions = new ArrayList<>(suggestions.subList(0, nb));
        } else {
            while (suggestions.size() < nb) {
                suggestions.add(suggestions.get(suggestions.size() - 1));
            }
        }

        return new InfosTouristiques(data.nom(), List.copyOf(suggestions), data.prixMoyenRepas());
    }

    private static String capitaliser(String valeur) {
        if (valeur == null || valeur.isBlank()) {
            return "Destination inconnue";
        }
        String trimmed = valeur.trim();
        return trimmed.substring(0, 1).toUpperCase(Locale.ROOT) + trimmed.substring(1);
    }

    private record LieuData(String nom, List<String> endroits, String prixMoyenRepas) {
    }
}
