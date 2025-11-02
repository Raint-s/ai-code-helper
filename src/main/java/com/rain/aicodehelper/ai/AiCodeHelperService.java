package com.rain.aicodehelper.ai;

import dev.langchain4j.service.Result;
import dev.langchain4j.service.SystemMessage;

import java.util.List;

public interface AiCodeHelperService {

    // 用注解注入系统提示词，可以从本地直接读文件的
    @SystemMessage(fromResource = "system-prompt.txt")
    String chat(String userMessage);

    //结构化输出方法，返回值改成下面定义的Report class
    @SystemMessage(fromResource = "system-prompt.txt")
    Report chatForReport(String userMessage);

    /**
     * 学习报告
     * 这是Java 14引入的一个新特性，叫做Record Classes（记录类）
     * 下面相当于定义了一个Record Class，然后name 和 suggestionList 就是这个Record Class的属性
     */
    record Report(String name, List<String> suggestionList){};

    //返回封装后的结果
    @SystemMessage(fromResource = "system-prompt.txt")
    Result<String> chatWithRag(String userMessage);
}
