package ai;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class HFService {

    private static final String KEY_FILE = "hf.key";

    public static String generatePlan(String prompt) {
        return generatePlan(prompt, "Mixtral");
    }

    public static String generatePlan(String prompt, String modelChoice) {
        String apiKey = readApiKey();
        if (apiKey == null) return "❌ API key not found.";

        // Safe model selection
        String modelEndpoint;
        switch (modelChoice) {
            case "Custom Model":
                modelEndpoint = "your-username/custom-model";
                break;
            case "HuggingFace - Mixtral":
            case "Mixtral":
                modelEndpoint = "mistralai/Mixtral-8x7B-Instruct-v0.1";
                break;
            default:
                modelEndpoint = "mistralai/Mixtral-8x7B-Instruct-v0.1";
                break;
        }

        System.out.println("✅ Using HF API Key: " + apiKey);
        System.out.println("🤖 Using Model: " + modelEndpoint);

        try {
            URL url = new URL("https://api-inference.huggingface.co/models/" + modelEndpoint);
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

            // Safely extract generated_text
            String raw = response.toString();
            int start = raw.indexOf("\"generated_text\":\"");
            if (start == -1) return "✅ Got response but couldn't parse text.";

            start += 18;
            int end = raw.indexOf("\"", start);
            if (end == -1) end = raw.length();

            return raw.substring(start, end).replace("\\n", "\n").replace("\\\"", "\"");

        } catch (IOException e) {
            e.printStackTrace();
            return "❌ Error during Hugging Face API call.";
        }
    }

    private static String readApiKey() {
        try (BufferedReader br = new BufferedReader(new FileReader(KEY_FILE))) {
            return br.readLine().trim();
        } catch (IOException e) {
            System.out.println("❌ Couldn't read HF API key from file.");
            return null;
        }
    }
}
