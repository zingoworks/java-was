package webserver;

import util.HttpRequestUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpHeader {

    private Map<String, String> header = new HashMap<>();

    public HttpHeader(BufferedReader br) throws IOException {
        while(true) {
            String requestHeader = br.readLine();

            if(requestHeader.equals("")) {
                break;
            }

            HttpRequestUtils.Pair headerPair = HttpRequestUtils.parseHeader(requestHeader);
            header.put(headerPair.getKey(), headerPair.getValue());
        }
    }

    public String getHeader(String key) {
        return header.get(key);
    }

    //TODO body, contentlength 관련 리팩토링 필요
    public int getContentLength() {
        return Integer.parseInt(header.getOrDefault("Content-Length", "0"));
    }

}
