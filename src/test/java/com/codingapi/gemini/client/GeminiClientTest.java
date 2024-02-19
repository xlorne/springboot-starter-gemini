package com.codingapi.gemini.client;

import com.codingapi.gemini.pojo.Embedding;
import com.codingapi.gemini.pojo.Generate;
import com.codingapi.gemini.pojo.Model;
import com.codingapi.gemini.pojo.Models;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
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
    void generateConfiguration() {
        Generate.Request request = Generate.creatTextChart("你好，请用中文简体回答我，你如何看待区块链？");
        request.setGenerationConfig(new Generate.GenerationConfig(List.of("Title"), 1.0f, 1000, 0.8f, 10));
        request.addSafetySetting("HARM_CATEGORY_DANGEROUS_CONTENT", "BLOCK_ONLY_HIGH");
        Generate.Response response = client.generate(request);
        String answer = Generate.toAnswer(response);
        System.out.println(answer);
    }

    @Test
    @SneakyThrows
    void stream() {
        Generate.Request request = Generate.creatTextChart("你好，请用中文简体回答我，你如何看待区块链？");
        client.stream(request, response -> {
            String answer = Generate.toAnswer(response);
            System.out.println(answer);
        });
    }

    @Test
    void generateVision() throws IOException {
        Generate.Request request = Generate.creatImageChart("这是一张什么图片？", new File("./images/test.png"));
        Generate.Response response = client.generate(request);
        String answer = Generate.toAnswer(response);
        System.out.println(answer);
    }

    @Test
    void counts() {
        Generate.Request request = Generate.creatTextChart("你好，请用中文简体回答我，你如何看待区块链？");
        int tokens = client.counts(request);
        System.out.println(tokens);
        assert tokens > 0;
    }

    @Test
    void embedding() {
        Embedding.Request request = Embedding.creat("你好，我是小强");
        Embedding.Response response = client.embedding(request);
        List<Double> answer = Embedding.toAnswer(response);
        System.out.println(answer);
    }

    @Test
    void model() {
        Model model = client.model("models/gemini-pro");
        System.out.println(model);
    }

    @Test
    void models() {
        Models models = client.models();
        System.out.println(models);
    }
}