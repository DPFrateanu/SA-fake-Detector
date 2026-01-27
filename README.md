# SA-fake-Detector

A Java-based fake news and clickbait detector powered by Google's Gemini AI API.

## Overview

SA-fake-Detector is a command-line application that analyzes text content to determine whether it contains fake news or clickbait. The application leverages Google's Gemini AI API to perform intelligent content analysis and provide confidence scores along with explanations in Romanian.

## Objective

The primary objective of this project is to combat misinformation by providing an automated tool that can:
- Detect fake news in text content
- Identify clickbait patterns
- Analyze web pages by extracting their content
- Provide confidence scores and detailed explanations for each analysis

## Features

- **Text Analysis**: Analyze any text input to detect fake news or clickbait
- **URL Support**: Automatically scrape and analyze content from web pages
- **AI-Powered Detection**: Uses Google's Gemini AI API for intelligent analysis
- **JSON Output**: Returns structured JSON responses for easy integration
- **Romanian Language Support**: Provides explanations in Romanian
- **Confidence Scoring**: Includes confidence levels (1-100) for each detection

## Technologies Used

- **Java 21**: Core programming language
- **Maven**: Build and dependency management
- **Google Gemini API**: AI-powered content analysis
- **Jsoup**: Web scraping library for HTML parsing
- **org.json**: JSON processing

## Requirements

- Java 21 or higher
- Maven 3.x
- Google Gemini API Key
- Internet connection (for API calls and web scraping)

## Setup

1. **Clone the repository**:
   ```bash
   git clone https://github.com/DPFrateanu/SA-fake-Detector.git
   cd SA-fake-Detector
   ```

2. **Set up Gemini API Key**:
   
   Obtain an API key from [Google AI Studio](https://aistudio.google.com/app/apikey) and set it as an environment variable:
   
   ```bash
   export GEMINI_API_KEY="your-api-key-here"
   ```

3. **Build the project**:
   ```bash
   mvn clean package
   ```

## Usage

### Running with Maven

> **Note**: The project currently has a configuration mismatch in `pom.xml`. The Maven shade plugin is configured with `org.example.cli.GeminiDetector` as the main class, but the actual main class is `org.example.Main`. When running with Maven exec plugin directly (as shown below), this works correctly.

**Analyze text directly**:
```bash
mvn exec:java -Dexec.mainClass="org.example.Main" -Dexec.args="Your text to analyze"
```

**Analyze a web page**:
```bash
mvn exec:java -Dexec.mainClass="org.example.Main" -Dexec.args="https://example.com/article"
```

### Running the JAR

> **Note**: Due to the pom.xml configuration issue mentioned above, the shaded JAR built by Maven may not execute properly. To fix this, the `mainClass` in pom.xml should be changed from `org.example.cli.GeminiDetector` to `org.example.Main`, then rebuild with `mvn clean package`.

After fixing and building, you can run the generated JAR file:

```bash
java -jar target/fake-detector-1.0-SNAPSHOT.jar "Your text to analyze"
```

### Default Behavior

If no arguments are provided, the application will analyze a default test text:
```bash
java -jar target/fake-detector-1.0-SNAPSHOT.jar
```

## Output Format

The application returns JSON output with the following structure:

```json
{
    "is_fake": true,
    "confidence": 85,
    "reason": "Explicație detaliată în limba română"
}
```

**Fields**:
- `is_fake` (boolean): Whether the content is detected as fake news or clickbait
- `confidence` (number): Confidence level from 1 to 100
- `reason` (string): Detailed explanation in Romanian

## Error Handling

In case of errors, the application returns a JSON response with error details:

```json
{
    "is_fake": false,
    "reason": "Eroare internă: description"
}
```

Or for API errors:

```json
{
    "error": "Eroare API Gemini: status_code",
    "details": "error details"
}
```

## Project Structure

```
SA-fake-Detector/
├── src/
│   └── main/
│       └── java/
│           └── org/
│               └── example/
│                   └── Main.java          # Main application class
├── pom.xml                                # Maven configuration
└── README.md                              # This file
```

## Configuration

### API Endpoint

The application uses Google's Gemini AI API (model identifier in code: gemini-2.5-flash):
```
https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent
```

### Text Length Limit

When analyzing web pages, content is automatically truncated to 30,000 characters to ensure efficient processing.

## Dependencies

- **org.json** (20231013): JSON processing
- **jsoup** (1.17.2): HTML parsing and web scraping
- **junit-jupiter** (5.10.0): Testing framework (test scope)

## License

This project is part of a Software Architecture course assignment.

## Contributing

This is an educational project. Contributions are welcome through pull requests.

## Notes

- The API key must be kept secure and should never be committed to version control
- The application requires an active internet connection for both API calls and web scraping
- Analysis is performed in Romanian language by default
- The Gemini API may have rate limits depending on your account tier
