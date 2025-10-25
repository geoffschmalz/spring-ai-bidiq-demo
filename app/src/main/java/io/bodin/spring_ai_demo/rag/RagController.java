package io.bodin.spring_ai_demo.rag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rag")
public class RagController {
    private final RagService service;

    public RagController(RagService service) {
        this.service = service;
    }

    @PostMapping(value = "/load")
    public ResponseEntity<String> load(@RequestBody String request) {
        service.load(request);

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/chat-qa")
    public ResponseEntity<String> chat1(@RequestBody String request) {
        var response = service.callQA(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/chat-rag")
    public ResponseEntity<String> chat2(@RequestBody String request) {
        var response = service.callRag(request);

        return ResponseEntity.ok(response);
    }
}