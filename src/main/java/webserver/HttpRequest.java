package webserver;

import db.DataBase;
import model.User;
import util.HttpRequestUtils;
import util.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static webserver.HttpMethod.GET;
import static webserver.HttpMethod.POST;

public class HttpRequest {

    private HttpMethod method;
    private String path;
    private Map<String, String> header = new HashMap<>();
    private Map<String, String> parameter = new HashMap<>();

    public HttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        String[] requestLine = br.readLine().split(" ");
        this.method = HttpMethod.of(requestLine[0]);
        this.path = requestLine[1];

        while(true) {
            String requestHeader = br.readLine();

            if(requestHeader.equals("")) {
                break;
            }

            HttpRequestUtils.Pair headerPair = HttpRequestUtils.parseHeader(requestHeader);
            header.put(headerPair.getKey(), headerPair.getValue());
        }

        if(method.equals(GET)) {
            if(path.contains("?")) {
                String[] tokens = HttpRequestUtils.getTokens(path, "\\?");
                for (String token : tokens) {
                    parameter.put(token.split("=")[0], token.split("=")[1]);
                }
            }
        }

        if(method.equals(POST)) {
            int contentLength = 0;
            if(header.containsKey("Content-Length")) {
                contentLength = Integer.parseInt(getHeader("Content-Length"));
            }

            String requestBody = IOUtils.readData(br, contentLength);
            parameter = HttpRequestUtils.parseQueryString(requestBody);

            if(path.endsWith("create")) {
                User user = new User(
                        parameter.get("userId"),
                        parameter.get("password"),
                        parameter.get("name"),
                        parameter.get("email")
                );

                DataBase.addUser(user);
            }
        }
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHeader(String key) {
        if(!header.containsKey("key")) {
            throw new IllegalArgumentException("존재하지 않는 Header Key 입니다.");
        }
        return header.get(key);
    }

    public String getParameter(String key) {
        if(parameter.isEmpty() || !parameter.containsKey(key)) {
            throw new IllegalArgumentException("인자가 없거나 존재하지 않는 Key 입니다.");
        }
        return parameter.get(key);
    }


}
