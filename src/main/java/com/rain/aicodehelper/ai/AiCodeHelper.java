package com.rain.aicodehelper.ai;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

// AI编程助手
@Service
@Slf4j
public class AiCodeHelper {

    //    @Bean
    //    @ConditionalOnProperty({"langchain4j.community.dashscope.chat-model.api-key"})
    //    QwenChatModel qwenChatModel(Properties properties) {
    //
    //    }
    @Resource
    private ChatModel qwenChatModel;

    //只支持文本消息
    public String chat(String message){
        //1.构造ChatMessage格式输入
        UserMessage userMessage = UserMessage.from(message);

        //2.输入给调用的大模型，获得response
        ChatResponse chatResponse = qwenChatModel.chat(userMessage);

        //3.从response中取出aiMessage，里面封装了ai回复的内容
        AiMessage aiMessage = chatResponse.aiMessage();

        log.info("AI输出： " + aiMessage.toString());

        //aiMessage回复的文本内容
        return aiMessage.text();
    }

    //调整入参为UserMessage
    public String chatWithMessage(UserMessage userMessage){
        ChatResponse chatResponse = qwenChatModel.chat(userMessage);
        AiMessage aiMessage = chatResponse.aiMessage();
        log.info("AI输出： " + aiMessage.toString());
        return aiMessage.text();
    }
}
