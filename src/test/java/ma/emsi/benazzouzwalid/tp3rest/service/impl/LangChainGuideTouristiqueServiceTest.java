package ma.emsi.benazzouzwalid.tp3rest.service.impl;

import ma.emsi.benazzouzwalid.tp3rest.dto.InfosTouristiques;
import ma.emsi.benazzouzwalid.tp3rest.llm.LlmClientForGuideTouristique;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class LangChainGuideTouristiqueServiceTest {

    @Test
    void shouldUseFallbackWhenClientNotReady() {
        LlmClientForGuideTouristique client = Mockito.mock(LlmClientForGuideTouristique.class);
        when(client.isReady()).thenReturn(false);

        LangChainGuideTouristiqueService service = new LangChainGuideTouristiqueService(client);

        InfosTouristiques infos = service.genererRecommandations("Maroc", 2);

        assertEquals("Maroc", infos.villeOuPays());
        assertEquals(2, infos.endroitsAVisiter().size());
    }

    @Test
    void shouldDelegateToLlmWhenReady() {
        LlmClientForGuideTouristique client = Mockito.mock(LlmClientForGuideTouristique.class);
        when(client.isReady()).thenReturn(true);
        InfosTouristiques expected = new InfosTouristiques("Paris", List.of("Tour Eiffel", "Louvre"), "25 EUR");
        when(client.demanderInfos("Paris", 2)).thenReturn(expected);

        LangChainGuideTouristiqueService service = new LangChainGuideTouristiqueService(client);

        InfosTouristiques infos = service.genererRecommandations("Paris", 2);

        assertEquals(expected, infos);
    }
}
