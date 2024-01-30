package com.codingapi.gemini.client;

import com.codingapi.gemini.pojo.Embedding;
import com.codingapi.gemini.pojo.Generate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.List;

@SpringBootTest
class GeminiClientTest {

    @Autowired
    private GeminiClient client;

    @Test
    void generate() {
        Generate.Request request = Generate.creatTextChart("你好，请用中文简体回答我，你如何看待区块链？");
        Generate.Response response = client.generate(request);
        String answer = Generate.toAnswer(response);
        System.out.println(answer);
    }

    @Test
    void generateVision() {
        Generate.Request request = Generate.creatImageChart("这是一张什么图片？", new File("./images/test.png"));
        Generate.Response response = client.generate(request);
        String answer = Generate.toAnswer(response);
        System.out.println(answer);
    }

    @Test
    void embedding() {
        Embedding.Request request = Embedding.creat("你好，我是小强");
        Embedding.Response response = client.embedding(request);
        List<Double> answer = Embedding.toAnswer(response);
        System.out.println(answer);
    }
}