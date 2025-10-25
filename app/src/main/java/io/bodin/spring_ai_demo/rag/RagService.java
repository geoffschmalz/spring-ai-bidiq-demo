package io.bodin.spring_ai_demo.rag;

import io.bodin.spring_ai_demo.product.ProductService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.aop.Advisor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RagService {

    private final VectorStore vectorStore;
    private final ChatClient client;

    public RagService(ChatClient client, VectorStore vectorStore) {
        this.client = client;
        this.vectorStore = vectorStore;
    }

    public String callQA(String userMessage) {

        var response = this.client.prompt()
                .user(userMessage)
                .advisors(QuestionAnswerAdvisor.builder(vectorStore).build())
                .call();

        return response.content();
    }

    public String callRag(String userMessage) {

        var rag = RetrievalAugmentationAdvisor.builder()
                .documentRetriever(VectorStoreDocumentRetriever.builder()
                        .vectorStore(vectorStore)
                        .build())
                .build();

        var response = this.client.prompt()
                .user(userMessage)
                .advisors(rag)
                .call();

        return response.content();
    }

    public void load(String text) {
        var doc = Document.builder().text(text).build();

        vectorStore.add(TokenTextSplitter.builder().build().split(doc));
    }
}
