package webserver.controller;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import static webserver.HttpMethod.GET;
import static webserver.HttpMethod.POST;

public abstract class AbstractController implements Controller {

    @Override
    public void handleRequest(HttpRequest request, HttpResponse response) {
        if(request.isMethod(GET)) {
            doGet(request, response);
        }

        if(request.isMethod(POST)) {
            doPost(request, response);
        }
    }

    public void doGet(HttpRequest request, HttpResponse response) {}

    public void doPost(HttpRequest request, HttpResponse response) {}

}
