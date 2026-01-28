# SA Fake Detector

A Java-based fake news and clickbait detection tool powered by Google's Gemini AI API.

## Overview

SA Fake Detector is a command-line application that analyzes text content or web pages to determine whether they contain fake news or clickbait. The application uses Google's Gemini AI model to provide intelligent analysis with confidence scores and detailed explanations.

## Objective

The primary objective of this project is to provide a simple, automated way to verify the authenticity of news articles and detect clickbait content. By leveraging advanced AI capabilities, the tool helps users make informed decisions about the information they consume online.

## Features

- **Text Analysis**: Analyze text directly provided as input
- **Web Scraping**: Extract and analyze content from URLs
- **AI-Powered Detection**: Utilizes Google Gemini AI for accurate fake news detection
- **Confidence Scoring**: Provides a confidence score (1-100) for each analysis
- **JSON Output**: Structured JSON response for easy integration with other systems
- **Error Handling**: Comprehensive error handling and reporting

## Project Structure

```
SA-fake-Detector/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── org/
│   │           └── example/
│   │               └── Main.java          # Main application logic
│   └── test/
│       └── java/
│           └── MainTest.java              # Unit tests
├── pom.xml                                 # Maven configuration and dependencies
├── .gitignore                              # Git ignore rules
└── README.md                               # This file
```

### Main Components

- **Main.java**: Contains the core application logic including:
  - Command-line argument processing
  - Web scraping functionality using Jsoup
  - Gemini AI API integration
  - JSON response formatting and extraction

- **MainTest.java**: Contains unit tests including:
  - Test for extracting content from Google Gemini API responses
  - Test for environment variable configuration
  - Test for web scraping functionality with Jsoup

## Prerequisites

- **Java 21** or higher
- **Maven** for dependency management and building
- **Gemini API Key**: Required for AI analysis functionality

## Dependencies

The project uses the following dependencies (managed via Maven):

- **org.json:json** (v20231013) - JSON processing
- **org.jsoup:jsoup** (v1.17.2) - HTML parsing and web scraping
- **org.junit.jupiter:junit-jupiter** (v5.10.0) - Unit testing framework

## Build Configuration

The project uses **Maven Shade Plugin** (v3.5.0) to create an executable JAR file with all dependencies bundled. This allows for easy deployment and execution without managing external dependencies.

## Setup

1. **Clone the repository**:
   ```bash
   git clone https://github.com/DPFrateanu/SA-fake-Detector.git
   cd SA-fake-Detector
   ```

2. **Set up your Gemini API Key**:
   - Obtain an API key from [Google AI Studio](https://makersuite.google.com/app/apikey)
   - Set the environment variable:
     ```bash
     export GEMINI_API_KEY="your-api-key-here"
     ```

3. **Build the project**:
   ```bash
   mvn clean package
   ```

## Usage

### Running from Command Line

After building the project, you can run it in several ways:

1. **Analyze text directly**:
   ```bash
   java -jar target/fake-detector-1.0-SNAPSHOT.jar "Your text to analyze here"
   ```

2. **Analyze a web page**:
   ```bash
   java -jar target/fake-detector-1.0-SNAPSHOT.jar "https://example.com/article"
   ```

3. **Run with default example**:
   ```bash
   java -jar target/fake-detector-1.0-SNAPSHOT.jar
   ```
   This will analyze the default text: "COVID-19 a izbucnit in 2023"

### Response Format

The application returns a JSON response with the following structure:

```json
{
  "is_fake": true,
  "confidence": 95,
  "reason": "Explanarea in romana despre de ce textul este considerat fake news"
}
```

### Error Response

In case of errors:

```json
{
  "error": "Error description",
  "details": "Additional error details"
}
```

## How It Works

1. **Input Processing**: The application accepts either plain text or a URL as input
2. **Web Scraping**: If a URL is provided, Jsoup extracts the text content from the web page
3. **Text Limitation**: Content is limited to 30,000 characters to ensure efficient processing
4. **AI Analysis**: The text is sent to Google's Gemini AI with a specialized prompt
5. **Result Parsing**: The AI response is parsed and formatted as JSON
6. **Output**: The final result is printed to stdout for easy integration

## Development

### Building from Source

```bash
mvn clean compile
```

### Running Tests

The project includes comprehensive unit tests covering:
- Content extraction from Gemini API responses
- Environment variable configuration
- Web scraping with Jsoup

Run tests with:
```bash
mvn test
```

### Creating Executable JAR

The project uses Maven Shade Plugin to create a single executable JAR with all dependencies:

```bash
mvn clean package
```

The executable JAR will be created in the `target/` directory as `fake-detector-1.0-SNAPSHOT.jar`.

## Configuration

The application uses the following configuration:

- **Gemini Model**: `gemini-2.5-flash` (fast and efficient)
- **API Endpoint**: Google Generative Language API
- **Text Limit**: 30,000 characters for web content
- **Output Format**: JSON

## License

This project is part of a Software Architecture course assignment.

## Contributing

Contributions are welcome! Please feel free to submit issues or pull requests.

## Authors

- DPFrateanu

## Acknowledgments

- Google Gemini AI for providing the AI capabilities
- Jsoup library for web scraping functionality
