package developer.ezandro.fipe.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FipeApiClient {
    private static final String BASE_URL = "https://parallelum.com.br/fipe/api/v1";
    private final HttpClient client;

    public FipeApiClient() {
        this.client = HttpClient.newHttpClient();
    }

    public String getData(String endpoint) throws IOException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 400) {
                throw new IOException("HTTP error " + response.statusCode() + " for " + endpoint);            }

            return response.body();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Request to " + endpoint + " was interrupted", e);        }
    }
}
