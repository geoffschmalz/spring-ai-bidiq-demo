package io.bodin.spring_ai_demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class ChatController {

    @Autowired
    private ChatService service;

    @PostMapping("/chat")
    public String init(Model model, @RequestParam("text") String prompt) {
        String response = this.service.call(prompt);

        model.addAttribute("now", new Date());
        model.addAttribute("response", response);

        return "chat-response";
    }

    @PostMapping(value = "/clear-memory")
    public ResponseEntity<?> clear() {
        this.service.clearHistory();

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/load-fact")
    public ResponseEntity<String> load(@RequestBody String request) {
        this.service.loadFacts(request);

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/system-prompt")
    public ResponseEntity<String> system(@RequestBody String prompt) {
        this.service.setSystemPrompt(prompt);

        return ResponseEntity.ok().build();
    }
}