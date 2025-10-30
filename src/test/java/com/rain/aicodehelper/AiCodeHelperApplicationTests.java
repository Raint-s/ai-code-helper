package com.rain.aicodehelper;

import com.rain.aicodehelper.ai.AiCodeHelper;
import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AiCodeHelperApplicationTests {

    @Resource
    private AiCodeHelper aiCodeHelper;

    @Test
    void chat() {
        aiCodeHelper.chat("你好，我是程序员Rain");
    }

    @Test
    void chatWithMessage() {
        UserMessage userMessage = UserMessage.from(
                TextContent.from("请描述图片"),
                ImageContent.from("https://image.baidu.com/search/detail?ct=503316480&z=0&tn=baiduimagedetail&ipn=d&cl=2&cm=1&sc=0&sa=vs_ala_img_datu&lm=-1&ie=utf8&pn=0&rn=1&di=7562963243866521601&ln=0&word=%E6%88%98%E5%9B%BD%E5%85%B0%E6%96%AF%E5%9B%BE%E7%89%87&os=3066881505%2C1672843942&cs=2871504099%2C1183148972&objurl=http%3A%2F%2Ft13.baidu.com%2Fit%2Fu%3D2871504099%2C1183148972%26fm%3D225%26app%3D113%26f%3DJPEG%3Fw%3D1346%26h%3D1002%26s%3DB687F5045AD27FF5142C1D16030080B2&bdtype=0&simid=2871504099%2C1183148972&pi=0&adpicid=0&timingneed=&spn=0&is=0%2C0&lid=dae6bcda012d1d0a")
        );
        aiCodeHelper.chatWithMessage(userMessage);
    }
}
