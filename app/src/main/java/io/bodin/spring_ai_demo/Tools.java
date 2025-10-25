package io.bodin.spring_ai_demo;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Tools {
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
    @Tool(description = "This tool can be used to look up medical product information. You must have a product ID to call this tool.  Do not call this tool unless the user wants to know information about medical products")
    public static String product(@ToolParam(description="The medical product ids") String [] productIds){
        StringBuilder response = new StringBuilder();
        for(var product : productIds){
            if(product == null) continue;
            if(products.containsKey(product)) {
                response.append(products.get(product)).append("\n");
            }
        }
        if(products.isEmpty()) return null;

        return response.toString();

    }
}