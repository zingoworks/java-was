package webserver.controller;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.IOException;

public class ViewController extends AbstractController {

    public void doGet(HttpRequest request, HttpResponse response) throws IOException {
        System.out.println("ViewController detected : " + request.getPath());
        response.forward(request.getPath());
    }

    public void doPost(HttpRequest request, HttpResponse response) {

    }

}
