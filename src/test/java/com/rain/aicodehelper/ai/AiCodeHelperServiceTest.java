package com.rain.aicodehelper.ai;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AiCodeHelperServiceTest {

    @Resource
    private AiCodeHelperService aiCodeHelperService;

    @Test
    void chat() {
        String result = aiCodeHelperService.chat("你好，我是程序员Rain");
        System.out.println(result);
    }

    //用来测试带记忆的AiService实现
    @Test
    void chatWithMemory() {
        //这里断点调试看看aiCodeHelperService携带的内容，能看到chatMomery的中间值
        String result = aiCodeHelperService.chat("你好，我是程序员Rain");
        System.out.println(result);

        result = aiCodeHelperService.chat("你好，我是谁来着？");
        System.out.println(result);
    }

    @Test
    void chatForReport() {
        String userMessage = "你好，我是程序员Rain，学编程两年半，请帮我制定学习报告";
        AiCodeHelperService.Report report = aiCodeHelperService.chatForReport(userMessage);
        System.out.println(report);
    }
}