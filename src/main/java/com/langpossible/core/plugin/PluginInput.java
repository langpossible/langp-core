package com.langpossible.core.plugin;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class PluginInput {

    private Map<String, Object> parameters = new HashMap<>();

    private Map<String, Object> credentials = new HashMap<>();

    public PluginInput addParameter(String name, Object value) {
        parameters.put(name, value);
        return this;
    }

    public PluginInput addCredential(String name, Object value) {
        credentials.put(name, value);
        return this;
    }

}
