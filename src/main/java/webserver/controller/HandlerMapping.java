package webserver.controller;

import java.util.HashMap;
import java.util.Map;

public class HandlerMapping {

    private Map<String, Controller> mappings;

    public HandlerMapping() {
        mappings = new HashMap<>();
        mappings.put("/user/login", new LoginController());
    }

    public Controller getController(String path) {
        return mappings.get(path);
    }

}
