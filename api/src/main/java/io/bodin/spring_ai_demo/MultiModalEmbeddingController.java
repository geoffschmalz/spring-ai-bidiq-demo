package io.bodin.spring_ai_demo;

import org.springframework.ai.chat.client.ResponseEntity;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.http.HttpStatus;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@RestController
public class MultiModalEmbeddingController {

    private final MultiModalEmbeddingService embeddingService;

    public MultiModalEmbeddingController(MultiModalEmbeddingService embeddingService) {
        this.embeddingService = embeddingService;
    }

    @PostMapping("/embedding/image")
    public ResponseEntity<EmbeddingResponse, HttpStatus> getEmbedding(@RequestParam("image") @NotNull MultipartFile imageFile) {
        EmbeddingResponse response = embeddingService.getEmbedding(
                MimeType.valueOf(imageFile.getContentType()),
                imageFile.getResource());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}