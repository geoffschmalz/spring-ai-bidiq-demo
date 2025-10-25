package io.bodin.spring_ai_demo.helpdesk;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/helpdesk")
public class HelpDeskController {
    private final HelpDeskService service;

    public HelpDeskController(HelpDeskService service) {
        this.service = service;
    }

    @PostMapping(value = "/clear")
    public ResponseEntity<?> clear() {
        service.clearHistory();

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/chat")
    public ResponseEntity<String> chat(@RequestBody String request) {
        var response = service.call(request);

        return ResponseEntity.ok(response);
    }
}