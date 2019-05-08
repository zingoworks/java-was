package webserver.controller;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.IOException;

public class ListUserController extends AbstractController {

    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws IOException {
        if(request.isLogined()) {
            System.out.println("로그인하셨네요 리스트 보여드릴게요");
            return;
        }

        System.out.println("로그인안됐어요 로그인페이지로 가시죠?");
        response.sendRedirect("/user/login.html");
    }

}
