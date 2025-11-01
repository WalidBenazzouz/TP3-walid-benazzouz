package ma.emsi.benazzouzwalid.tp3rest.service.exception;

/**
 * Exception lev?e lorsque la configuration du LLM est incompl?te (par exemple cl? API manquante).
 */
public class GuideTouristiqueConfigurationException extends RuntimeException {

    public GuideTouristiqueConfigurationException(String message) {
        super(message);
    }

    public GuideTouristiqueConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
