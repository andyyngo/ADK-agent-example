# Recipe Agent

A Java-based AI agent that generates detailed cooking recipes using Google's Gemini 2.5 Pro model. The agent provides:
- Complete recipe instructions
- Ingredient lists
- Step-by-step cooking directions
- Nutritional information (when available)

## Features

### Current Capabilities
- Pre-configured with ramen recipe template
- Extensible architecture for adding new recipes
- Interactive command line interface
- JSON-based recipe response format

### Planned Features
- [ ] Database of common recipes
- [ ] Measurement unit conversions
- [ ] Dietary restriction adaptations
- [ ] Multimedia recipe generation

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

## API Reference
```java
// Get recipe from Java code
Map<String,String> recipe = RecipeAgent.getRecipe("dishName");

// Available keys in response:
// - status: "success" or "error"
// - General steps: Cooking instructions
// - report: Error message when status="error"
```

## Configuration
Edit `src/main/java/agents/multitool/RecipeAgent.java` to:
- Change default model parameters
- Add new recipe functions
- Modify agent instructions

## Development

### Build
```bash
./gradlew build
```

### Test
```bash
./gradlew test
```

## Contributing
Pull requests welcome! Please ensure:
1. New recipes include all required sections
2. Maintain consistent JSON response format
3. Include accompanying tests

## License
Apache 2.0
