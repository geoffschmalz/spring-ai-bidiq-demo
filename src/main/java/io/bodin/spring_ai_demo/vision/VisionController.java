package io.bodin.spring_ai_demo.vision;

import io.bodin.spring_ai_demo.product.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vision")
public class VisionController {
    private final VisionService service;

    public VisionController(VisionService service) {
        this.service = service;
    }

    @PostMapping(value = "/chat")
    public ResponseEntity<String> chat(@RequestBody String request) {
        var response = service.call(request);

        return ResponseEntity.ok(response);
    }
}