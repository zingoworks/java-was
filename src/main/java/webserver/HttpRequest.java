package webserver;

import util.HttpRequestUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HttpRequest {

    private String requestLine;
    private String method;
    private String url;
    private List<HttpRequestUtils.Pair> header = new ArrayList<>();

    public HttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        requestLine = br.readLine();
        System.out.println(requestLine);

        method = HttpRequestUtils.parseMethod(requestLine);
        url = HttpRequestUtils.parseUrl(requestLine);

        while(true) {
            requestLine = br.readLine();
            System.out.println(requestLine);

            if(requestLine.equals("")) {
                break;
            }

            header.add(HttpRequestUtils.parseHeader(requestLine));
        }
    }

    public String getRequestLine() {
        return requestLine;
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<HttpRequestUtils.Pair> getHeader() {
        return header;
    }

}
