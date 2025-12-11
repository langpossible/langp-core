package com.langpossible.core.plugin.tool;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ToolInput {

    private Map<String, Object> parameters = new HashMap<>();

    private Map<String, Object> credentials = new HashMap<>();

    public ToolInput addParameter(String name, Object value) {
        parameters.put(name, value);
        return this;
    }

    public ToolInput addCredential(String name, Object value) {
        credentials.put(name, value);
        return this;
    }

}
