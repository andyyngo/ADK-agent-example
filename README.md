# Agent Development Kit Java example

A Java-based AI agent that generates detailed cooking recipes using Google's Gemini 2.5 Pro model. 

## Getting Started

### Prerequisites
- Java 11+
- Gradle 8.13
- Google API credentials (for Gemini model access)

### Installation
```bash
git clone <repository-url>
cd <repository-dir>
```

### Supply your key
Depending on your operating system, set these environments accordingly
Below example is for Linux/Mac

```bash
export GOOGLE_GENAI_USE_VERTEXAI=FALSE
export GOOGLE_API_KEY=<Your key>
```

### Running the Agent
Start the interactive CLI:
```bash
./gradlew run
```

Example session:
```
You > Show me how to make ramen
Thinking...
Agent > Tool 'getRecipe' responded with: {status=success, General steps=1. Make bone soup...}
```

## License
Apache 2.0
