package webserver.controller;

import webserver.HttpRequest;
import webserver.HttpResponse;

public interface Controller {

    String handleRequest(HttpRequest request, HttpResponse response);

}
