package ma.emsi.benazzouzwalid.tp3rest.llm;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

/**
 * Prompt contract used by LangChain4j to orchestrate Gemini responses.
 */
public interface TourGuideModel {

    @SystemMessage("""
        Tu es un guide touristique francophone.
        Reponds strictement en JSON respectant la structure suivante :
        {
          "ville_ou_pays": "nom du lieu",
          "endroits_a_visiter": ["lieu 1", "lieu 2"],
          "prix_moyen_repas": "<montant> <devise>"
        }
        Aucune mise en forme Markdown ni texte libre en dehors de ce JSON.
        Ajuste le nombre d'endroits listes selon l'instruction utilisateur.
        """)
    @UserMessage("""
        Prepare un mini-guide pour {{destination}} en proposant {{count}} endroits a decouvrir
        et indique un prix moyen pour un repas sur place.
        """)
    String genererGuide(@V("destination") String destination, @V("count") int nombreEndroits);
}
