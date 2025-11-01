package ma.emsi.benazzouzwalid.tp3rest.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ma.emsi.benazzouzwalid.tp3rest.dto.InfosTouristiques;
import ma.emsi.benazzouzwalid.tp3rest.llm.LlmClientForGuideTouristique;
import ma.emsi.benazzouzwalid.tp3rest.service.GuideTouristiqueService;
import ma.emsi.benazzouzwalid.tp3rest.service.exception.GuideTouristiqueConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation principale qui delegue a LangChain4j (Gemini) avec un mode degrade en local.
 */
@ApplicationScoped
public class LangChainGuideTouristiqueService implements GuideTouristiqueService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LangChainGuideTouristiqueService.class);

    private final LlmClientForGuideTouristique llmClient;
    private final GuideTouristiqueService fallbackService;

    public LangChainGuideTouristiqueService() {
        this(null);
    }

    @Inject
    public LangChainGuideTouristiqueService(LlmClientForGuideTouristique llmClient) {
        this.llmClient = llmClient;
        this.fallbackService = new StaticGuideTouristiqueService();
    }

    @Override
    public InfosTouristiques genererRecommandations(String lieuOuPays, int nombreEndroits) {
        if (llmClient != null && llmClient.isReady()) {
            try {
                return llmClient.demanderInfos(lieuOuPays, nombreEndroits);
            } catch (GuideTouristiqueConfigurationException e) {
                LOGGER.warn("Client LLM indisponible : {}. Utilisation du mode degrade.", e.getMessage());
            } catch (Exception e) {
                LOGGER.error("Erreur lors de l'appel au LLM : {}", e.getMessage(), e);
            }
        }

        LOGGER.info("Utilisation des donnees statiques pour {}.", lieuOuPays);
        return fallbackService.genererRecommandations(lieuOuPays, nombreEndroits);
    }
}
