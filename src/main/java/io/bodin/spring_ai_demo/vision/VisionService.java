package io.bodin.spring_ai_demo.vision;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.content.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

@Service
public class VisionService {

    private final ChatClient client;

    @Autowired
    private ResourceLoader resourceLoader;

    public VisionService(@Qualifier("visionClient") ChatClient client) {
        this.client = client;
    }
    public String call(String userMessage) {
        var m = UserMessage.builder()
                .media(loadMedia())
                .text(userMessage)
                .build();

        var response = this.client.prompt()
                .messages(m)
                .call();

        return response.content();
    }

    public Media loadMedia() {
        return new Media(
                MimeTypeUtils.IMAGE_JPEG,
                resourceLoader.getResource("classpath:image.jpeg")
        );
    }
}
