package ma.emsi.benazzouzwalid.tp3rest.llm;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.Duration;
import ma.emsi.benazzouzwalid.tp3rest.dto.InfosTouristiques;
import ma.emsi.benazzouzwalid.tp3rest.service.exception.GuideTouristiqueConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Client LangChain4j dedie au role de guide touristique.
 * Instancie le modele Gemini et l'interface generee automatiquement par LangChain4j.
 */
@ApplicationScoped
public class LlmClientForGuideTouristique {

    private static final Logger LOGGER = LoggerFactory.getLogger(LlmClientForGuideTouristique.class);
    private static final String DEFAULT_MODEL = "gemini-2.0-flash";

    private GuideTouristique guideTouristique;
    private ChatMemory chatMemory;

    @PostConstruct
    void init() {
        final String apiKey = System.getenv("GEMINI_API_KEY");
        if (apiKey == null || apiKey.isBlank()) {
            LOGGER.warn("Cle GEMINI_API_KEY absente : le client LangChain4j fonctionnera en mode degrade.");
            return;
        }

        this.chatMemory = MessageWindowChatMemory.withMaxMessages(10);

        ChatLanguageModel chatModel = GoogleAiGeminiChatModel.builder()
            .apiKey(apiKey)
            .modelName(DEFAULT_MODEL)
            .temperature(0.3)
            .timeout(Duration.ofSeconds(20))
            .logRequestsAndResponses(false)
            .build();

        this.guideTouristique = AiServices.builder(GuideTouristique.class)
            .chatModel(chatModel)
            .chatMemory(chatMemory)
            .build();

        LOGGER.info("Client LangChain4j initialise avec le modele {}", DEFAULT_MODEL);
    }

    /**
     * Indique si le LLM est disponible (cle configuree et modele initialise).
     */
    public boolean isReady() {
        return guideTouristique != null;
    }

    /**
     * Demande au LLM les informations touristiques pour un lieu.
     */
    public InfosTouristiques demanderInfos(String lieuOuPays, int nombreEndroits) {
        if (!isReady()) {
            throw new GuideTouristiqueConfigurationException("Le client LangChain4j n'est pas configure (cle GEMINI_API_KEY manquante).");
        }
        return guideTouristique.genererInfos(lieuOuPays, nombreEndroits);
    }

    /**
     * Reinitialise la memoire de conversation (utile si l'on change de contexte).
     */
    public void reinitialiserMemoire() {
        if (chatMemory != null) {
            chatMemory.clear();
        }
    }
}
