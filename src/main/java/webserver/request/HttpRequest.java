package webserver.request;

import db.DataBase;
import model.User;
import util.HttpRequestUtils;
import util.IOUtils;
import webserver.HttpHeader;
import webserver.HttpMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private HttpMethod method;
    private String path;
    private HttpHeader header;
    private Map<String, String> parameter = new HashMap<>();

    public HttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        String[] requestLine = br.readLine().split(" ");
        this.method = HttpMethod.of(requestLine[0]);
        this.path = requestLine[1];
        this.header = new HttpHeader(br);
    }

    public boolean isMethod(HttpMethod method) {
        return this.method.equals(method);
    }

    public String getPath() {
        return path;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getHeader(String key) {
        return header.getHeader(key);
    }

    public String getParameter(String key) {
        if(parameter.isEmpty() || !parameter.containsKey(key)) {
            throw new IllegalArgumentException("인자가 없거나 존재하지 않는 Key 입니다.");
        }
        return parameter.get(key);
    }

}
