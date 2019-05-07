package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.*;
import webserver.request.HttpRequest;

import java.util.HashMap;
import java.util.Map;

public class HandlerMapping {

    private static Map<String, Controller> mappings = new HashMap<>();

    static {
        mappings.put("/", new HomeController());
        mappings.put("/user/create", new CreateUserController());
        mappings.put("/user/list", new ListUserController());
        mappings.put("/user/login", new LoginController());
    }

    public static Controller getController(HttpRequest request) {
        if(!mappings.containsKey(request.getPath())) {
            return new ViewController();
        }

        return mappings.get(request.getPath());
    }

}
