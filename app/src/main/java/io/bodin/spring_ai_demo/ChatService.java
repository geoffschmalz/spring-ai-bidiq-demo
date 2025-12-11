package io.bodin.spring_ai_demo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.document.Document;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.generation.augmentation.ContextualQueryAugmenter;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

@Service
public class ChatService {

    @Autowired
    private ChatClient client;

    @Autowired
    private VectorStore vectorStore;

    private String systemPrompt;

    private ChatMemory chatMemory = MessageWindowChatMemory.builder()
            .maxMessages(10)
            .build();

    private MessageChatMemoryAdvisor chatMemoryAdvisor = MessageChatMemoryAdvisor
            .builder(chatMemory)
            .build();

    public void clearHistory(){
        this.chatMemory.clear(ChatMemory.DEFAULT_CONVERSATION_ID);
    }

    public String call(String userMessage) {
        var rag1 = RetrievalAugmentationAdvisor.builder()
                .documentRetriever(VectorStoreDocumentRetriever.builder()
                        .vectorStore(vectorStore)
                        .build())
                .queryAugmenter(ContextualQueryAugmenter.builder()
                    .allowEmptyContext(true)
                    .build())
                .build();

        var rag2 = QuestionAnswerAdvisor.builder(vectorStore).build();

        var prompt = this.client.prompt()
                .user(userMessage)
                .advisors(
                    rag1,
                    this.chatMemoryAdvisor
                );

        if(this.systemPrompt != null) {
            prompt = prompt.system(this.systemPrompt);
        }

        var response = prompt.call();

        return response.content();
    }

    public void loadFacts(String text) {
        var doc = Document.builder().text(text).build();

        this.vectorStore.add(TokenTextSplitter.builder().build().split(doc));
    }

    public String getSystemPrompt() {
        return systemPrompt;
    }

    public void setSystemPrompt(String systemPrompt) {
        this.systemPrompt = systemPrompt;
    }
}