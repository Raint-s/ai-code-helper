package com.rain.aicodehelper.ai;

import dev.langchain4j.service.SystemMessage;

public interface AiCodeHelperService {

    // 用注解注入系统提示词，可以从本地直接读文件的
    @SystemMessage(fromResource = "system-prompt.txt")
    String chat(String userMessage);
}
