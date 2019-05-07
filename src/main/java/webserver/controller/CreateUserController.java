package webserver.controller;

import db.DataBase;
import model.User;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.IOException;
import java.util.Map;

public class CreateUserController extends AbstractController {

    @Override
    public void doPost(HttpRequest request, HttpResponse response) throws IOException {
        User user = mapper(request.getParameters());
        if(DataBase.findUserById(user.getUserId()) != null) {
            throw new IllegalArgumentException("중복ID, 회원가입불가");
        }

        DataBase.addUser(user);
        response.sendRedirect("/index.html");
    }

    private static User mapper(Map<String, String> userParameter) {
        User user = new User(
                userParameter.get("userId"),
                userParameter.get("password"),
                userParameter.get("name"),
                userParameter.get("email"));
        return user;
    }

}
