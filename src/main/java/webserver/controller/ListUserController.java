package webserver.controller;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class ListUserController extends AbstractController {

    public void doGet(HttpRequest request, HttpResponse response) {

    }

    public boolean isLogin(String cookie) {
        return true;
    }

}
