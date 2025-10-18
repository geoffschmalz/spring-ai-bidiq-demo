package io.bodin.spring_ai_demo.product;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class ProductService {

    private static final String PROMPT_GENERAL_INSTRUCTIONS = """
        You'll act as a Medical Product Specialist helping the user understand information about products.

        You can provide product information, recommendations and comparisons.

        Product details can only be found using the product_lookup_tool.
    """;


    private final ChatClient client;

    public ProductService(ChatClient client) {
        this.client = client;
    }

    public String call(String userMessage) {

        var response = this.client.prompt()
                .system(PROMPT_GENERAL_INSTRUCTIONS)
                .user(userMessage)
                .tools(new Tools())
                .call();

        return response.content();
    }

    static class Tools {
        static Map<String, String> products = new HashMap<>();
        static {
           products.put("1", """
                   {
                       "product_id":1,
                       "name":"Standard Hypodermic Syringe with Needle PrecisionGlide 3mL 1 Inch 23 Gauge NonSafety Thin Wall"
                       "manufacturer_product_id":"309571"
                       "manufacturer_name": "BD",
                       "color":"turquoise"
                       "features":[
                            "Clear barrel featuring bold scale",
                            "Needle helps improve dosage accuracy",
                            "Virtually eliminates needle pop-off"
                       ]
                   }
                   """);

            products.put("2", """
                   {
                       "product_id":2,
                       "name":"Standard Hypodermic Syringe with Needle PrecisionGlide 3mL 1-1/2 Inch 18 Gauge NonSafety Thin Wall",
                       "manufacturer_product_id":"309580",
                       "manufacturer_name": "BD",
                       "color":"pink",
                       "features":[
                            "Clear barrel featuring bold scale",
                            "Needle helps improve dosage accuracy",
                            "Virtually eliminates needle pop-off"
                       ]
                   }
                   """);
        }
        @Tool(description = "product_lookup_tool")
        public static String product(@ToolParam(description="The product identifiers to retrieve information on") String [] productIds){
            System.out.println(Arrays.asList(productIds));
            StringBuilder response = new StringBuilder();
            for(var product : productIds){
                response.append(products.get(product)).append("\n");
            }
            return response.toString();
        }
    }
}