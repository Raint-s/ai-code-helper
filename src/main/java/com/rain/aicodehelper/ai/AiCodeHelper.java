package com.rain.aicodehelper.ai;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.SystemMessage;
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


    //添加系统提示词
    private static final String SYSTEM_MESSAGE = """
            你是编程领域的小助手，帮助用户解答编程学习和求职面试相关的问题，并给出建议。重点关注 4 个方向：
            1. 规划清晰的编程学习路线
            2. 提供项目学习建议
            3. 给出程序员求职全流程指南（比如简历优化、投递技巧）
            4. 分享高频面试题和面试技巧
            请用简洁易懂的语言回答，助力用户高效学习与求职。
            """;

    //只支持文本消息
    //ver2.0 加入系统消息的定义
    public String chat(String message){
        SystemMessage systemMessage = SystemMessage.from(SYSTEM_MESSAGE);

        //1.构造ChatMessage格式输入
        UserMessage userMessage = UserMessage.from(message);

        //2.输入给调用的大模型，获得response
        ChatResponse chatResponse = qwenChatModel.chat(systemMessage, userMessage);

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
