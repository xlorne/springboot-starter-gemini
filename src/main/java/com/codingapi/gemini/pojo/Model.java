package com.codingapi.gemini.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class Model {

    private String name;
    private String version;
    private String displayName;
    private String description;
    private int inputTokenLimit;
    private int outputTokenLimit;
    private List<String> supportedGenerationMethods;
    private float temperature;
    private float topP;
    private float topK;
}
