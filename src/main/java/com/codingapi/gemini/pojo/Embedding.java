package com.codingapi.gemini.pojo;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


public class Embedding {

    public static Request creat(String text) {
        Request request = new Request();
        request.setModel("models/embedding-001");
        Request.Content content = new Request.Content();
        content.getParts().add(new Request.Parts(text));
        request.setContent(content);
        return request;
    }

    public static List<Double> toAnswer(Response response){
        if(response == null || response.getEmbedding() == null){
            return null;
        }
        return response.getEmbedding().getValues();
    }


    @Setter
    @Getter
    public static class Request {

        private String model;
        private Content content;

        public String toJSONString() {
            return JSONObject.toJSONString(this);
        }

        @Setter
        @Getter
        public static class Content {
            private List<Parts> parts;

            public Content() {
                this.parts = new ArrayList<>();
            }
        }

        @Setter
        @Getter
        public static class Parts {
            private String text;

            public Parts(String text) {
                this.text = text;
            }
        }

    }

    @Setter
    @Getter
    public static class Response {

        private EmbeddingValue embedding;

        @Setter
        @Getter
        public static class EmbeddingValue {
            private List<Double> values;
        }
    }


}
