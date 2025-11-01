package ma.emsi.benazzouzwalid.tp3rest.config;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * Configuration JAX-RS principale : fixe la racine '/api' pour tous les endpoints REST du TP.
 */
@ApplicationPath("/api")
public class RestApplication extends Application {
}
