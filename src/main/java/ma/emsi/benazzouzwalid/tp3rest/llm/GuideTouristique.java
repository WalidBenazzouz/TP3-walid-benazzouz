package ma.emsi.benazzouzwalid.tp3rest.llm;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import ma.emsi.benazzouzwalid.tp3rest.dto.InfosTouristiques;

/**
 * Interface LangChain4j dont l'impl?mentation est g?n?r?e dynamiquement pour dialoguer avec Gemini.
 */
public interface GuideTouristique {

    @SystemMessage("""
        Tu es un guide touristique virtuel francophone.
        Ta r?ponse doit imp?rativement respecter le format JSON suivant sans ajout de Markdown ni de prose :
        {
          "ville_ou_pays": "nom de la ville ou du pays",
          "endroits_a_visiter": ["endroit 1", "endroit 2"],
          "prix_moyen_repas": "<prix> <devise du pays>"
        }
        Indique exactement {{nombre}} principaux endroits ? visiter et ajoute un prix moyen r?aliste pour un repas.
        Ne r?ponds pas si tu ne peux pas respecter le format demand?.
        """)
    @UserMessage("""
        Fournis les {{nombre}} principaux endroits ? visiter pour {{lieu}} et le prix moyen d'un repas sur place.
        """)
    InfosTouristiques genererInfos(@V("lieu") String lieuOuPays, @V("nombre") int nombreEndroits);
}
