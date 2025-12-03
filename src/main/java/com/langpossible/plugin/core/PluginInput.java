package com.langpossible.plugin.core;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class PluginInput {

    private Map<String, Object> parameters = new HashMap<>();

    public PluginInput addParameter(String name, Object value) {
        parameters.put(name, value);
        return this;
    }

}
