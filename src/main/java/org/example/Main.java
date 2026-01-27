package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Main {

    private static final String GEMINI_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent";
    private static final String API_KEY = System.getenv("GEMINI_API_KEY");

    public static void main(String[] args) {
        // Verificăm dacă avem input
        String textToAnalyze = "";

        if(args.length > 0){
            textToAnalyze = args[0];
        }
        else
        {
            textToAnalyze = "COVID-19 a izbucnit in 2023";
        }


        if (API_KEY == null || API_KEY.isEmpty()) {
            printError("Lipseste API Key-ul Gemini");
            return;
        }

        String input = textToAnalyze;

        try {
            // 2. Dacă e link, extragem textul din el (Scraping)
            if (input.startsWith("http")) {

                Document doc = Jsoup.connect(input).get();
                textToAnalyze = doc.body().text();

                // Limităm textul dacă e prea lung
                if (textToAnalyze.length() > 30000) {
                    textToAnalyze = textToAnalyze.substring(0, 30000);
                }
            }

            String jsonResponse = callGemini(textToAnalyze);

            // 4. Afișăm rezultatul final la STDOUT (pentru a fi preluat de aplicatia mare)
            System.out.println(jsonResponse);

        } catch (Exception e) {
            printError("Eroare la procesare: " + e.getMessage());
        }
    }

    private static String callGemini(String text) throws IOException, InterruptedException {
        // Prompt Engineering: Instruim Gemini să fie un detector strict JSON
        String prompt = """
                Analizează următorul text și decide dacă este Fake News sau Clickbait.
                Text: "%s"
                
                Răspunde STRICT în format JSON (fără markdown ```json), cu următoarele câmpuri:
                {
                    "is_fake": true/false,
                    "confidence": (număr 1-100),
                    "reason": "scurta explicație în română"
                }
                """.formatted(text.replace("\"", "'")); // Evităm stricarea JSON-ului prin ghilimele interne

        // Construim structura JSON cerută de Google API
        JSONObject userPart = new JSONObject().put("text", prompt);
        JSONObject parts = new JSONObject().put("parts", new JSONArray().put(userPart));
        JSONObject content = new JSONObject().put("contents", new JSONArray().put(parts));

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(GEMINI_URL + "?key=" + API_KEY))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(content.toString()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            // Extragem răspunsul text din structura complexă Google
            JSONObject jsonRes = new JSONObject(response.body());
            String rawText = jsonRes.getJSONArray("candidates")
                    .getJSONObject(0)
                    .getJSONObject("content")
                    .getJSONArray("parts")
                    .getJSONObject(0)
                    .getString("text");

            return rawText.replace("```json", "").replace("```", "").trim();
        } else {
            return new JSONObject()
                    .put("error", "Eroare API Gemini: " + response.statusCode())
                    .put("details", response.body())
                    .toString();
        }
    }

    private static void printError(String message) {
        JSONObject errorJson = new JSONObject();
        errorJson.put("is_fake", false);
        errorJson.put("reason", "Eroare internă: " + message);
        System.out.println(errorJson.toString());
    }
}