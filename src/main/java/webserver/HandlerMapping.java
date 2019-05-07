package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.*;
import webserver.request.HttpRequest;

import java.util.HashMap;
import java.util.Map;

public class HandlerMapping {

    private static Map<String, Controller> mappings = new HashMap<>();

    private static void initMapping() {
        mappings.put("/", new HomeController());
        mappings.put("/user/create", new CreateUserController());
        mappings.put("/user/list", new ListUserController());
        mappings.put("/login", new LoginController());
    }

    public static Controller getController(HttpRequest request) {
        initMapping();
        if(!mappings.containsKey(request.getPath())) {
            return new ViewController();
        }

        return mappings.get(request.getPath());
    }

}
