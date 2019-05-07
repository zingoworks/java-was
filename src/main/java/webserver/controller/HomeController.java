package webserver.controller;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.IOException;

public class HomeController extends AbstractController {

    public void doGet(HttpRequest request, HttpResponse response) throws IOException {
        System.out.println("HomeController detected : " + request.getPath());
        response.forward("/index.html");
    }

}
