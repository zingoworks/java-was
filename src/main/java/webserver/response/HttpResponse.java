package webserver.response;

import webserver.HttpBody;
import webserver.HttpHeader;
import webserver.HttpStatus;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

//    private HttpStatus status;
//    private HttpHeader header;
//    private HttpBody body;

    private Map<String, String> header;
    private DataOutputStream dos;

    public HttpResponse(OutputStream out) {
        this.dos = new DataOutputStream(out);
        this.header = new HashMap<>();
    }

    public void addHeader(String key, String value) {
        header.put(key, value);
    }

    public void forward(String path) throws IOException {
        byte[] body = Files.readAllBytes(new File("./webapp" + path).toPath());
        response200Header(body.length);
        processHeaders();
        responseBody(body);
        dos.flush();
    }

    public void forwardBody(String bodyValue) throws IOException {
        byte[] body = bodyValue.getBytes();
        response200Header(body.length);
        processHeaders();
        responseBody(body);
        dos.flush();
    }

    private void responseBody(byte[] body) throws IOException {
        dos.write(body, 0, body.length);
    }

    private void response200Header(int contentLength) throws IOException {
        dos.writeBytes("HTTP/1.1 200 OK \r\n");
        dos.writeBytes("Content-Length: " + contentLength + "\r\n");
    }

    public void sendRedirect(String location) throws IOException {
        dos.writeBytes("HTTP/1.1 302 Found \r\n");
        dos.writeBytes("Location: " + location + "\r\n");
        processHeaders();
        dos.flush();
    }

    private void processHeaders() throws IOException {
        for (String key : header.keySet()) {
            dos.writeBytes(key + ": " + header.get(key) + "\r\n");
        }
        dos.writeBytes("\r\n");
    }

}
