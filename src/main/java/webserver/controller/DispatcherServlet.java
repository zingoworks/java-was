package webserver.controller;

import webserver.HttpRequest;
import webserver.HttpResponse;

public class DispatcherServlet {

    private static HandlerMapping handlerMapping = new HandlerMapping();

    public static void process(HttpRequest request, HttpResponse response) {
        handlerMapping.getController(request.getPath()).handleRequest(request, response);
    }

}
