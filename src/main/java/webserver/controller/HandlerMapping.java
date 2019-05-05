package webserver.controller;

import webserver.request.HttpRequest;

import java.util.HashMap;
import java.util.Map;

public class HandlerMapping {

    private static Map<String, Controller> mappings;

    public HandlerMapping() {
        mappings = new HashMap<>();
        mappings.put("/user/login", new LoginController());
        mappings.put("/user/create", new JoinController());
        mappings.put("/user/list", new ListController());
    }

    public static Controller getController(HttpRequest request) {
        return mappings.get(request.getPath());
    }

}
