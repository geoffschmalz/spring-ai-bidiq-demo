package io.bodin.spring_ai_demo.helpdesk;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.stereotype.Service;

@Service
public class HelpDeskService {

    private static final String PROMPT_GENERAL_INSTRUCTIONS = """
        You'll act as Help Desk Agent to help the user with internet connection issues.          
            
        Below are `common solutions` you should follow in the order they appear in the list to help troubleshoot internet connection problems:
            
        1. Check if your router is turned on.
        2. Check if your computer is connected via cable or Wi-Fi and if the password is correct.
        3. Restart your router and modem.
            
        You should give only one `common solution` per prompt up to 3 solutions.               
    """;

    private final ChatClient client;

    private ChatMemory chatMemory = MessageWindowChatMemory.builder()
            .maxMessages(10)
            .build();

    private MessageChatMemoryAdvisor chatMemoryAdvisor = MessageChatMemoryAdvisor
            .builder(chatMemory)
            .build();

    private SimpleLoggerAdvisor loggingAdvisor = SimpleLoggerAdvisor.builder().build();


    public HelpDeskService(ChatClient client) {
        this.client = client;
    }

    public void clearHistory(){
        this.chatMemory.clear(ChatMemory.DEFAULT_CONVERSATION_ID);
    }

    public String call(String userMessage) {

        var response = this.client.prompt()
                //.system(PROMPT_GENERAL_INSTRUCTIONS)
                .user(userMessage)
                .advisors(this.chatMemoryAdvisor)
                .call();

        return response.content();
    }
}