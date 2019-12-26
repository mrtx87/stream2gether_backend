package com.section9.stream2gether.models;

import java.util.HashMap;
import java.util.Map;

public class DataTransferContainer {
    Map<String, Object> data = new HashMap<>();

    public Object getValue(String key) {
        return data.get(key);
    }

    public Object setValue(String key, Object value) {
        return data.put(key, value);
    }

    public void removeValue(String key) {
        data.remove(key);
    }

}
