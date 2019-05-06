package webserver.controller;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public interface Controller {

    void handleRequest(HttpRequest request, HttpResponse response);

}
