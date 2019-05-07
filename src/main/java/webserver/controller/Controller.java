package webserver.controller;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.IOException;

public interface Controller {

    void handleRequest(HttpRequest request, HttpResponse response) throws IOException;

}
