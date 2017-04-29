package com.angryelectron.thingspeak;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;


public class QueryBuilder {
    private String url = "";

    public QueryBuilder field(String name, Object value) {
        StringBuilder queryString = new StringBuilder();
        if (!this.url.equals("")) {
            queryString.append("&");
        }
        try {
            queryString.append(name).append("=").append(URLEncoder.encode((value == null) ? "" : value.toString(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        this.url += queryString.toString();
        return this;
    }

    public QueryBuilder fields(Map<String, Object> parameters) {
        if (parameters != null) {
            for (Entry<String, Object> param : parameters.entrySet()) {
                if (param.getValue() instanceof String || param.getValue() instanceof Number || param.getValue() instanceof Boolean) {
                    field(param.getKey(), param.getValue());
                } else {
                    throw new RuntimeException("Parameter \"" + param.getKey() + "\" can't be sent with a GET request because of type: " + param.getValue().getClass().getName());
                }
            }
        }
        return this;
    }

    public String getQuery() {
        return url;
    }
}