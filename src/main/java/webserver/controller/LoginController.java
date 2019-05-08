package webserver.controller;

import db.DataBase;
import model.User;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.IOException;

public class LoginController extends AbstractController{

    @Override
    public void doPost(HttpRequest request, HttpResponse response) throws IOException {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        User target = DataBase.findUserById(userId);

        if(target == null || !target.isMatchPassword(password)) {
            System.out.println("존재하지 않는 아이디 혹은 비밀번호가 틀려요");
            response.addHeader("Set-Cookie", "logined=false; Path=/");
            response.sendRedirect("/user/login_failed.html");
            return;
        }

        System.out.println("로그인 성공했어요!!");
        response.addHeader("Set-Cookie", "logined=true; Path=/");
        response.sendRedirect("/");
    }

}
