package ma.emsi.benazzouzwalid.tp3rest.llm;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.util.List;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Wrapper around LangChain4j to interact with Gemini as a tourist guide assistant.
 */
@ApplicationScoped
public class TourGuideClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(TourGuideClient.class);
    private static final int MEMORY_WINDOW = 8;
    private static final List<String> FALLBACK_SPOTS = List.of(
        "Office du tourisme",
        "Centre historique",
        "Musee principal"
    );

    private TourGuideModel tourGuideModel;

    @PostConstruct
    void init() {
        String apiKey = resolveApiKey();
        if (apiKey == null) {
            LOGGER.warn("Aucune cle GEMINI trouvee, utilisation du mode fallback.");
            return;
        }

        ChatModel chatModel = GoogleAiGeminiChatModel.builder()
            .apiKey(apiKey)
            .modelName("gemini-2.5-flash")
            .temperature(0.4)
            .timeout(Duration.ofSeconds(20))
            .build();

        tourGuideModel = AiServices.builder(TourGuideModel.class)
            .chatModel(chatModel)
            .chatMemory(MessageWindowChatMemory.withMaxMessages(MEMORY_WINDOW))
            .build();

        LOGGER.info("Client Gemini initialise avec succes.");
    }

    public String obtenirItineraire(String destination, int nbEndroits) {
        int demande = Math.max(0, nbEndroits);
        if (!isPret()) {
            return genererFallback(destination, demande);
        }

        try {
            return tourGuideModel.genererGuide(destination, demande);
        } catch (Exception e) {
            LOGGER.warn("Erreur lors de l'appel Gemini, retour au mode fallback: {}", e.getMessage());
            LOGGER.debug("Detail de l'exception", e);
            return genererFallback(destination, demande);
        }
    }

    public boolean isPret() {
        return tourGuideModel != null;
    }

    private String genererFallback(String destination, int nbEndroits) {
        String lieuNormalise = normaliserDestination(destination);
        int nombreSuggestions = nbEndroits > 0 ? nbEndroits : FALLBACK_SPOTS.size();

        StringBuilder builder = new StringBuilder();
        builder.append('{')
            .append("\"ville_ou_pays\":\"").append(lieuNormalise).append("\",")
            .append("\"endroits_a_visiter\":[");

        for (int i = 0; i < nombreSuggestions; i++) {
            String suggestion = FALLBACK_SPOTS.get(i % FALLBACK_SPOTS.size());
            builder.append('\"').append(suggestion).append('\"');
            if (i < nombreSuggestions - 1) {
                builder.append(',');
            }
        }

        builder.append("],")
            .append("\"prix_moyen_repas\":\"25 EUR\",")
            .append("\"mode\":\"fallback\"}");

        return builder.toString();
    }

    private String normaliserDestination(String destination) {
        if (destination == null || destination.isBlank()) {
            return "Destination inconnue";
        }
        String trimmed = destination.trim();
        return trimmed.substring(0, 1).toUpperCase(Locale.ROOT) + trimmed.substring(1);
    }

    private String resolveApiKey() {
        String primary = System.getenv("GEMINI_KEY");
        if (primary != null && !primary.isBlank()) {
            return primary.trim();
        }
        String secondary = System.getenv("GEMINI_API_KEY");
        if (secondary != null && !secondary.isBlank()) {
            return secondary.trim();
        }
        return null;
    }
}
