package com.example.hack2v2.integration.github;

import org.springframework.stereotype.Component;

@Component
public class GitHubModelsClient {

    public String enviarPromptChat(String prompt) {
        return "Respuesta simulada para chat";
    }

    public String enviarPromptCompletion(String prompt) {
        return "Respuesta simulada para completion";
    }

    public String enviarPromptMultimodal(String prompt) {
        return "Respuesta simulada para multimodal";
    }

    public String getAvailableModels() {
        return "[\"openai/gpt-4\", \"openai/text-davinci-003\", \"deepseek/deepseek-vl\"]";
    }
}
