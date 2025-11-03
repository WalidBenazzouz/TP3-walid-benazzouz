# 🚀 TP3 - Application REST avec LangChain4j

<div align="center">

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![LangChain4j](https://img.shields.io/badge/LangChain4j-276DC3?style=for-the-badge&logo=chainlink&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)

*Application REST moderne intégrant LangChain4j pour des fonctionnalités d'IA avancées*

[Fonctionnalités](#-fonctionnalités) • [Installation](#-installation) • [Utilisation](#-utilisation) • [API](#-api) • [Technologies](#-technologies)

</div>

---

## 📋 Table des matières

- [À propos](#-à-propos)
- [Fonctionnalités](#-fonctionnalités)
- [Prérequis](#-prérequis)
- [Installation](#-installation)
- [Configuration](#-configuration)
- [Utilisation](#-utilisation)
- [Documentation API](#-documentation-api)
- [Technologies utilisées](#-technologies-utilisées)
- [Architecture](#-architecture)
- [Auteur](#-auteur)

---

## 🎯 À propos

Ce projet est une application REST développée avec **Spring Boot** et **LangChain4j**, permettant d'intégrer des capacités d'intelligence artificielle dans une architecture microservices moderne. L'application expose des endpoints RESTful pour interagir avec des modèles de langage et offrir des fonctionnalités d'IA conversationnelle.

### Objectifs du projet

- Démontrer l'intégration de LangChain4j dans une application Spring Boot
- Créer une API REST robuste et scalable
- Implémenter des patterns de développement modernes
- Fournir une architecture extensible pour les applications IA

---

## ✨ Fonctionnalités

- 🤖 **Intégration IA** : Utilisation de LangChain4j pour des interactions avec des modèles de langage
- 🌐 **API RESTful** : Endpoints bien structurés et documentés
- 🔄 **Architecture moderne** : Design patterns et bonnes pratiques Spring Boot
- 📊 **Gestion des données** : Persistence et manipulation efficace des données
- 🔐 **Sécurité** : Implémentation des standards de sécurité
- 📝 **Documentation** : API documentée avec Swagger/OpenAPI
- ⚡ **Performance** : Optimisation des requêtes et mise en cache

---

## 🔧 Prérequis

Avant de commencer, assurez-vous d'avoir installé :

- **Java** 17 ou supérieur
- **Maven** 3.8+
- **Git**
- Un IDE (IntelliJ IDEA, Eclipse, VS Code recommandés)
- Une clé API pour le service LLM (OpenAI, Anthropic, etc.)

---

## 📦 Installation

### Cloner le repository

```bash
git clone https://github.com/WalidBenazzouz/TP3-walid-benazzouz.git
cd TP3-walid-benazzouz
```

### Installer les dépendances

```bash
mvn clean install
```

### Compiler le projet

```bash
mvn compile
```

---

## ⚙️ Configuration

### 1. Variables d'environnement

Créez un fichier `.env` à la racine du projet :

```env
# Configuration API LLM
LLM_API_KEY=votre_clé_api
LLM_MODEL=gpt-4
LLM_TEMPERATURE=0.7

# Configuration Spring
SPRING_PROFILES_ACTIVE=dev
SERVER_PORT=8080

# Configuration Base de données
DB_URL=jdbc:postgresql://localhost:5432/tp3_db
DB_USERNAME=votre_username
DB_PASSWORD=votre_password
```

### 2. Fichier application.properties

Configurez le fichier `src/main/resources/application.properties` :

```properties
# Server Configuration
server.port=${SERVER_PORT:8080}

# LangChain4j Configuration
langchain4j.api-key=${LLM_API_KEY}
langchain4j.model=${LLM_MODEL:gpt-4}
langchain4j.temperature=${LLM_TEMPERATURE:0.7}

# Logging
logging.level.root=INFO
logging.level.com.example=DEBUG
```

---

## 🚀 Utilisation

### Démarrer l'application

```bash
mvn spring-boot:run
```

L'application sera accessible sur `http://localhost:8080`

### Accéder à la documentation API

Une fois l'application démarrée, accédez à :

- **Swagger UI** : `http://localhost:8080/swagger-ui.html`
- **OpenAPI Spec** : `http://localhost:8080/v3/api-docs`

---

## 📡 Documentation API

### Endpoints principaux

#### 1. Chat Completion

```http
POST /api/chat
Content-Type: application/json

{
  "message": "Votre message ici",
  "conversationId": "optional-conversation-id"
}
```

**Réponse :**
```json
{
  "response": "Réponse de l'IA",
  "conversationId": "conversation-id",
  "timestamp": "2025-11-03T10:30:00Z"
}
```

#### 2. Historique des conversations

```http
GET /api/conversations/{conversationId}
```

**Réponse :**
```json
{
  "conversationId": "conversation-id",
  "messages": [
    {
      "role": "user",
      "content": "Message utilisateur",
      "timestamp": "2025-11-03T10:30:00Z"
    },
    {
      "role": "assistant",
      "content": "Réponse de l'IA",
      "timestamp": "2025-11-03T10:30:05Z"
    }
  ]
}
```

#### 3. Health Check

```http
GET /actuator/health
```

---

## 🛠️ Technologies utilisées

| Technologie | Version | Description |
|-------------|---------|-------------|
| **Java** | 17+ | Langage de programmation |
| **Spring Boot** | 3.x | Framework backend |
| **LangChain4j** | Latest | Intégration LLM |
| **Maven** | 3.8+ | Gestion des dépendances |
| **Lombok** | Latest | Réduction du boilerplate |
| **Spring Data JPA** | 3.x | Couche de persistence |
| **PostgreSQL** | 14+ | Base de données |
| **Swagger/OpenAPI** | 3.0 | Documentation API |
| **JUnit 5** | 5.x | Tests unitaires |

---

## 🏗️ Architecture

```
TP3-walid-benazzouz/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/tp3/
│   │   │       ├── controller/      # Contrôleurs REST
│   │   │       ├── service/         # Logique métier
│   │   │       ├── model/           # Entités et DTOs
│   │   │       ├── repository/      # Accès aux données
│   │   │       ├── config/          # Configuration Spring
│   │   │       └── exception/       # Gestion des exceptions
│   │   └── resources/
│   │       ├── application.properties
│   │       └── static/              # Ressources statiques
│   └── test/
│       └── java/                    # Tests unitaires et d'intégration
├── pom.xml
├── .gitignore
└── README.md
```

### Diagramme de l'architecture

```
┌─────────────┐         ┌──────────────┐         ┌─────────────┐
│   Client    │────────>│  Controller  │────────>│   Service   │
└─────────────┘         └──────────────┘         └─────────────┘
                                                         │
                                                         ▼
                        ┌──────────────┐         ┌─────────────┐
                        │  LangChain4j │<────────│ Repository  │
                        └──────────────┘         └─────────────┘
                               │                        │
                               ▼                        ▼
                        ┌──────────────┐         ┌─────────────┐
                        │  LLM API     │         │  Database   │
                        └──────────────┘         └─────────────┘
```

---

## 🧪 Tests

### Exécuter tous les tests

```bash
mvn test
```

### Exécuter un test spécifique

```bash
mvn test -Dtest=NomDuTest
```

### Rapport de couverture

```bash
mvn jacoco:report
```

Le rapport sera disponible dans `target/site/jacoco/index.html`

---

## 📈 Développement futur

- [ ] Ajout de l'authentification JWT
- [ ] Implémentation du rate limiting
- [ ] Support multi-langues
- [ ] Cache distribué avec Redis
- [ ] Containerisation avec Docker
- [ ] Pipeline CI/CD
- [ ] Monitoring avec Prometheus et Grafana
- [ ] Support de modèles LLM additionnels

---

## 🤝 Contribution

Les contributions sont les bienvenues ! Pour contribuer :

1. Forkez le projet
2. Créez une branche (`git checkout -b feature/AmazingFeature`)
3. Committez vos changements (`git commit -m 'Add some AmazingFeature'`)
4. Pushez vers la branche (`git push origin feature/AmazingFeature`)
5. Ouvrez une Pull Request

---

## 📝 License

Ce projet est développé dans le cadre d'un TP académique.

---

## 👤 Auteur

**Walid Benazzouz**

- GitHub: [@WalidBenazzouz](https://github.com/WalidBenazzouz)
- Email: [benazzouz.walid@outlook.com]
- LinkedIn: https://www.linkedin.com/in/walid-ben-azzouz/

---
**Encadrant**
-M.Richard Grin 
-UCA
## 📞 Support

Si vous rencontrez des problèmes ou avez des questions :

1. Consultez la [documentation](https://github.com/WalidBenazzouz/TP3-walid-benazzouz/wiki)
2. Ouvrez une [issue](https://github.com/WalidBenazzouz/TP3-walid-benazzouz/issues)
3. Contactez l'auteur

---

<div align="center">

### ⭐ N'oubliez pas de mettre une étoile si ce projet vous a aidé !

**Fait avec ❤️ par Walid Benazzouz**

</div>
