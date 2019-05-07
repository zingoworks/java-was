package webserver.request;

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
//    private HttpBody body;

    public HttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String[] requestLine = br.readLine().split(" ");
        this.method = HttpMethod.of(requestLine[0]);
        this.path = requestLine[1];
        this.header = new HttpHeader(br);
        String body = "";
        if(header.containsKey("Content-Length")) {
            body = IOUtils.readData(br, Integer.parseInt(header.getHeader("Content-Length")));
        }
        this.parameter = HttpRequestUtils.parseQueryString(body);
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

    public Map<String, String> getParameters() {
        return parameter;
    }

    public String getParameter(String key) {
        return parameter.get(key);
    }

}
