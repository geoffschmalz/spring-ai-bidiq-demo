package io.bodin.spring_ai_demo;

import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ToolCallbackProvider weatherTools(WeatherService weatherService) {
        return MethodToolCallbackProvider
                .builder()
                .toolObjects(weatherService)
                .build();
    }

    public record TextInput(String input) {}

    @Bean
    public ToolCallback toUpperCase() {
        return FunctionToolCallback
                .builder("toUpperCase", (TextInput input) -> input.input().toUpperCase())
                .inputType(TextInput.class)
                .description("Put the text to upper case")
                .build();
    }

    @Service
    public static class WeatherService {

        public record WeatherResponse(Current current) {
            public record Current(LocalDateTime time, int interval, double temperature_2m) {}
        }

        @Tool(description = "Get the temperature (in celsius) for a specific location")
        public WeatherResponse getTemperature(
                @ToolParam(description = "The location latitude")
                double latitude,
                @ToolParam(description = "The location longitude")
                double longitude) {

            return RestClient.create()
                    .get()
                    .uri("https://api.open-meteo.com/v1/forecast?latitude={latitude}&longitude={longitude}&current=temperature_2m",
                            latitude, longitude)
                    .retrieve()
                    .body(WeatherResponse.class);
        }
    }
}
