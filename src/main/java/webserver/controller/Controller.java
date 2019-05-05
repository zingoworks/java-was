package webserver.controller;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public interface Controller {

    String handleRequest(HttpRequest request, HttpResponse response);

}
