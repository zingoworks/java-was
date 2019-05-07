package webserver.controller;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.IOException;

import static webserver.HttpMethod.GET;
import static webserver.HttpMethod.POST;

public abstract class AbstractController implements Controller {

    @Override
    public void handleRequest(HttpRequest request, HttpResponse response) throws IOException {
        if(request.isMethod(GET)) {
            doGet(request, response);
        }

        if(request.isMethod(POST)) {
            doPost(request, response);
        }
    }

    public void doGet(HttpRequest request, HttpResponse response) throws IOException {}

    public void doPost(HttpRequest request, HttpResponse response) throws IOException {}

}
