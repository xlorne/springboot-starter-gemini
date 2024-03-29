package com.codingapi.gemini.pojo;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Generate {

    public static Request creatTextChart(String text) {
        Request request = new Request("models/gemini-pro");
        Request.Chat chat = new Request.Chat();
        chat.setRole("user");
        chat.getParts().add(new Request.TextPart(text));
        request.getContents().add(chat);
        return request;
    }

    public static Request creatImageChart(String text, File image) throws IOException {
        Request request = new Request("models/gemini-pro-vision");
        Request.Chat chat = new Request.Chat();
        chat.getParts().add(new Request.TextPart(text));
        chat.getParts().add(new Request.ImagePart(image));
        request.getContents().add(chat);
        return request;
    }


    public static String toAnswer(Response response) {
        if (response == null || response.getCandidates() == null || response.getCandidates().isEmpty()) {
            return null;
        }
        return response.getCandidates().get(0).getAnswer();
    }


    @Setter
    @Getter
    public static class SafetySetting {
        private String category;
        private String threshold;
    }


    @Setter
    @Getter
    public static class GenerationConfig {
        private List<String> stopSequences;
        private float temperature;
        private int maxOutputTokens;
        private float topP;
        private float topK;


        public GenerationConfig(List<String> stopSequences,
                                float temperature,
                                int maxOutputTokens,
                                float topP,
                                float topK) {
            this.stopSequences = stopSequences;
            this.temperature = temperature;
            this.maxOutputTokens = maxOutputTokens;
            this.topP = topP;
            this.topK = topK;
        }
    }


    @Setter
    @Getter
    public static class Request {
        private List<Chat> contents;

        @JSONField(serialize = false)
        private String model;

        private List<SafetySetting> safetySettings;
        private GenerationConfig generationConfig;

        public Request(String model) {
            this.model = model;
            this.contents = new ArrayList<>();
        }

        public void addSafetySetting(String category, String threshold) {
            if (safetySettings == null) {
                safetySettings = new ArrayList<>();
            }
            SafetySetting safetySetting = new SafetySetting();
            safetySetting.setCategory(category);
            safetySetting.setThreshold(threshold);
            safetySettings.add(safetySetting);
        }

        public String toJSONString() {
            return JSONObject.toJSONString(this);
        }


        public static class Part {

        }

        @Setter
        @Getter
        public static class TextPart extends Part {
            private String text;

            public TextPart(String text) {
                this.text = text;
            }

        }

        @Setter
        @Getter
        public static class ImagePart extends Part {

            @JSONField(name = "inline_data")
            private InlineData inlineData;

            public ImagePart(File file) throws IOException {
                this.inlineData = new InlineData();
                BufferedImage bufferedImage = ImageIO.read(file);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "jpeg", outputStream);
                String base64 = Base64.getEncoder().encodeToString(outputStream.toByteArray());
                this.inlineData.setData(base64);
                this.inlineData.setMimeType("image/jpeg");
            }

        }


        @Setter
        @Getter
        public static class InlineData {
            @JSONField(name = "mime_type")
            private String mimeType;
            private String data;
        }

        @Setter
        @Getter
        public static class Chat {
            private String role;
            private List<Part> parts;

            public Chat() {
                this.parts = new ArrayList<>();
            }
        }
    }

    @Setter
    @Getter
    public static class TotalToken{
        private int totalTokens;
    }

    @Setter
    @Getter
    public static class Response {

        private List<Candidate> candidates;
        private PromptFeedback promptFeedback;


        @Setter
        @Getter
        public static class Candidate {
            private Content content;
            private String finishReason;
            private int index;
            private List<SafetyRating> safetyRatings;

            public String getAnswer() {
                if (content == null || content.getParts() == null || content.getParts().isEmpty()) {
                    return null;
                }
                return content.getParts().get(0).getText();
            }
        }

        @Setter
        @Getter
        public static class Content {
            private List<Part> parts;
            private String role;
        }

        @Setter
        @Getter
        public static class Part {
            private String text;
        }

        @Setter
        @Getter
        public static class SafetyRating {
            private String category;
            private String probability;
        }

        @Setter
        @Getter
        public static class PromptFeedback {
            private List<SafetyRating> safetyRatings;
        }

    }
}
