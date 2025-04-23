package ai;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class HFService {

    private static final String API_URL = "https://api-inference.huggingface.co/models/tiiuae/falcon-7b-instruct";
    private static final String KEY_FILE = "hf.key";

    public static String generatePlan(String prompt) {
        String apiKey = readApiKey();
        if (apiKey == null) return "❌ API key not found.";

        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + apiKey);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String jsonPayload = String.format("""
                {
                    "inputs": "%s"
                }
            """, prompt.replace("\"", "\\\"").replace("\n", "\\n"));

            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonPayload.getBytes("utf-8"));
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) response.append(line);
            reader.close();

            String raw = response.toString();
            int start = raw.indexOf("\"generated_text\":\"") + 18;
            int end = raw.indexOf("\"}", start);
            if (start > 17 && end > start) {
                return raw.substring(start, end).replace("\\n", "\n");
            } else {
                return "✅ Got response but failed to parse.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Error during Hugging Face API call.";
        }
    }

    private static String readApiKey() {
        try (BufferedReader br = new BufferedReader(new FileReader(KEY_FILE))) {
            String key = br.readLine().trim();
            System.out.println("✅ Using HF API Key: " + key);
            return key;
        } catch (IOException e) {
            System.out.println("❌ Couldn't read HF API key from file.");
            return null;
        }
    }
}
