package com.example.hack2v2.github;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GitHubModelsClient {

    private final OkHttpClient client;
    private final String token;

    public GitHubModelsClient(@Value("${github.token}") String token) {
        this.client = new OkHttpClient();
        this.token = token;
    }

    public String getAvailableModels() throws IOException {
        Request request = new Request.Builder()
            .url("https://api.github.com/models")
            .header("Authorization", "Bearer " + token)
            .header("Accept", "application/vnd.github+json")
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Error al obtener modelos: " + response.code());
            }
            return response.body().string();
        }
    }
}
