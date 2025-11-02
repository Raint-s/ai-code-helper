package com.rain.aicodehelper.ai;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiCodeHelperServiceFactory {

    @Resource
    private ChatModel qwenChatModel;

    @Resource
    private ContentRetriever contentRetriever;

    @Bean
    public AiCodeHelperService aiCodeHelperService() {
        /**
         * 定义一个基于消息窗口的Memory对象，每个用户最多保存10条消息（消息记忆支持区分用户）
         * 这个是基于内存，基于消息数量进行淘汰的Memory
         * 改进点1.如果要让消息持久化，需要实现一个ChatMemoryStore的接口，把里面增删改的方法重写，数据源换成自己的，读取等方式用自己的即可
         * 改进点2.如果有多个用户，每个用户有多个对话，每个对话需要隔离，那么可以加入memoryId（类似聊天室的房间号）即可
         */
        MessageWindowChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);

        /**
         * 构造AiService的时候不直接用create，而是通过builder建造者模式来构造
         * 如果在AiHelperService那里加入了@MemoryId注解引入了单独的MessageId，那么这里可以用chatMeomryProvider来实现会话记忆隔离
         */
        AiCodeHelperService aiCodeHelperService = AiServices.builder(AiCodeHelperService.class)
                .chatModel(qwenChatModel)
                .chatMemory(chatMemory)  //会话记忆
                .contentRetriever(contentRetriever)  //RAG检索增强生成
                .build();

        return aiCodeHelperService;
    }
}
