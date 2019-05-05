package webserver.controller;

import db.DataBase;
import model.User;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class LoginController implements Controller{

    @Override
    public String handleRequest(HttpRequest request, HttpResponse response) {
        System.out.println("로그인 시도");

        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        User loginTarget = DataBase.findUserById(userId);
        if(loginTarget.isMatchPassword(password)) {
            System.out.println("로그인 성공");
            return "/index.html";
        }

        System.out.println("로그인 실패");
        return "/user/login_failed.html";
    }

}
