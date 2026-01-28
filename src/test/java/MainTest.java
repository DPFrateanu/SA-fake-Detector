import org.example.Main;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


public class MainTest {

    @Test
    void testExtractContentFromGoogleResponse() {
        // Simulăm un răspuns complex de la Google API
        String exGoogleResponse = """
            {
              "candidates": [
                {
                  "content": {
                    "parts": [
                      {
                        "text": "```json\\n{ \\"is_fake\\": true, \\"reason\\": \\"Test reusit\\" }\\n```"
                      }
                    ]
                  }
                }
              ]
            }
            """;

        String result = Main.extractContent(exGoogleResponse);

        // Verificăm dacă a curățat "```json" și a scos textul corect
        assertFalse(result.contains("```json"), "Textul nu ar trebui să conțină markdown");
        assertTrue(result.contains("Test reusit"), "Textul extras ar trebui să conțină motivul");
    }

    @Test
    void testEnvironmentVariable() {
        String key = System.getenv("GEMINI_API_KEY");
        if (key != null) {
            assertFalse(key.isEmpty(), "Cheia API nu ar trebui să fie goală");
        } else {
            System.out.println("GEMINI_API_KEY nu este setat în Environment Variables pentru teste.");
        }
    }

    @Test
    void testJsoupExtraction() {
        String url = "https://example.com";
        try {
            Document doc = Jsoup.connect(url).get();
            String text = doc.body().text();

            assertNotNull(text);
            assertTrue(text.contains("Example Domain"), "Ar trebui să găsească textul 'Example Domain'");
            System.out.println("Text extras cu succes: " + text.substring(0, 20) + "...");

        } catch (IOException e) {
            fail("Nu s-a putut conecta la example.com: " + e.getMessage());
        }
    }
}
