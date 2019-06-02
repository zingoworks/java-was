package webserver;

import webserver.controller.*;
import webserver.request.HttpRequest;

import java.util.HashMap;
import java.util.Map;

public class HandlerMapping {

    private static Map<String, Controller> mappings = new HashMap<>();

    static {
        //TODO URI 하드코딩 처리필요
        mappings.put("/", new HomeController());
        mappings.put("/user/create", new CreateUserController());
        mappings.put("/user/list", new ListUserController());
        mappings.put("/user/login", new LoginController());
    }

    public static Controller getController(HttpRequest request) {
        return mappings.containsKey(request.getPath())
                ? mappings.get(request.getPath())
                : new ViewController();
    }

}
