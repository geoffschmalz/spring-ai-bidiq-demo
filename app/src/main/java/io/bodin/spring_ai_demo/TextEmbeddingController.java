package io.bodin.spring_ai_demo;

import org.springframework.ai.chat.client.ResponseEntity;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
public class TextEmbeddingController {

    private final TextEmbeddingService textEmbeddingService;

    public TextEmbeddingController(TextEmbeddingService textEmbeddingService) {
        this.textEmbeddingService = textEmbeddingService;
    }

    @PostMapping("/embedding/text")
    public ResponseEntity<EmbeddingResponse, HttpStatus> getEmbedding(@RequestBody @NotNull String text) {
        EmbeddingResponse response = textEmbeddingService.getEmbedding(text);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
