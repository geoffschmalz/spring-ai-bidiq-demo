package io.bodin.spring_ai_demo;

import io.modelcontextprotocol.client.McpSyncClient;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.model.ollama.autoconfigure.OllamaChatProperties;
import org.springframework.ai.model.tool.DefaultToolExecutionEligibilityPredicate;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

/*    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .oauth2Client(Customizer.withDefaults())
                .csrf(CsrfConfigurer::disable)
                .build();
    }

    @Bean
    WebClient.Builder webClientBuilder(McpSyncClientExchangeFilterFunction filterFunction) {
        return WebClient.builder().apply(filterFunction.configuration());
    }
*/
    @Primary
    @Bean("chatClient")
    public ChatClient chatClient(@Qualifier("chatModel") OllamaChatModel model, List<McpSyncClient> mcpClients) {
        return ChatClient
                .builder(model)
                .defaultToolCallbacks(SyncMcpToolCallbackProvider.builder().mcpClients(mcpClients).build())
                .build();
    }

    @Bean("visionClient")
    public ChatClient visionClient(@Qualifier("visionModel") OllamaChatModel model, List<McpSyncClient> mcpClients) {
        return ChatClient
                .builder(model)
                .defaultToolCallbacks(SyncMcpToolCallbackProvider.builder().mcpClients(mcpClients).build())
                .build();
    }

    @Bean
    public SimpleVectorStore simpleVectorStore(EmbeddingModel embeddingModel) {
        return SimpleVectorStore.builder(embeddingModel).build();
    }

    @Bean(value = "visionModel")
    //Copied from OllamaChatAutoConfiguration
    public OllamaChatModel visionModel(OllamaApi ollamaApi, OllamaChatProperties properties,
                                           ToolCallingManager toolCallingManager) {

        properties.getOptions().setModel("gemma3");

        return OllamaChatModel.builder()
                .ollamaApi(ollamaApi)
                .defaultOptions(properties.getOptions())
                .toolCallingManager(toolCallingManager)
                .toolExecutionEligibilityPredicate(new DefaultToolExecutionEligibilityPredicate())
                .build();
    }

    @Bean("chatModel")
    //Copied from OllamaChatAutoConfiguration
    public OllamaChatModel chatModel(OllamaApi ollamaApi, OllamaChatProperties properties,
                                           ToolCallingManager toolCallingManager) {

        properties.getOptions().setModel("llama3.1");

        return OllamaChatModel.builder()
                .ollamaApi(ollamaApi)
                .defaultOptions(properties.getOptions())
                .toolCallingManager(toolCallingManager)
                .toolExecutionEligibilityPredicate(new DefaultToolExecutionEligibilityPredicate())
                .build();
    }
}
