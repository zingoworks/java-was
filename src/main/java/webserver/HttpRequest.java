package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static webserver.HttpMethod.POST;

public class HttpRequest {
    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private HttpMethod method;
    private String path;
    private Map<String, String> parameters;
    private Map<String, String> headers = new HashMap<>();

    public HttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String requestLine = br.readLine();
        log.debug("request line : {}", requestLine);

        this.method = HttpRequestUtils.getMethod(requestLine);
        this.path = HttpRequestUtils.getPath(requestLine);

        String headerLine = br.readLine();
        log.debug("headers line : {}", headerLine);

        while (!headerLine.equals("")) {
            headers.putAll(HttpRequestUtils.getHeader(headerLine));
            headerLine = br.readLine();
            log.debug("headers line : {}", headerLine);
        }


        if(method == POST) {
            int contentLength = Integer.valueOf(headers.get("Content-Length"));
            String requestBody = IOUtils.readData(br, contentLength);
            log.debug("requestBody : {}", requestBody);

            this.parameters = HttpRequestUtils.getParameter(requestBody);
            return;
        }

        this.parameters = HttpRequestUtils.getParameter(HttpRequestUtils.getQueryString(requestLine));
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getHeader(String key) {
        if(!headers.containsKey(key)) {
            throw new IllegalArgumentException("잘못 된 헤더 키값 입니다.");
        }

        return headers.get(key);
    }

    public String getParameter(String key) {
        if(!parameters.containsKey(key)) {
            throw new IllegalArgumentException("잘못 된 인자 키값 입니다.");
        }
        return parameters.get(key);
    }
}
