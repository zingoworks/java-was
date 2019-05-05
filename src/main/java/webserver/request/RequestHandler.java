package webserver.request;

import java.io.*;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.Controller;
import webserver.controller.HandlerMapping;
import webserver.response.HttpResponse;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest request = new HttpRequest(in);
            HttpResponse response = new HttpResponse(out);

            Controller controller = HandlerMapping.getController(request);
            controller.handleRequest(request, response);


//            if(request.getPath().endsWith("list")) {
//                String cookie = "";
//
//                for (HttpRequestUtils.Pair pair : request.getHeader()) {
//                    if(pair.getKey().equals("Cookie")) {
//                        cookie = pair.getValue();
//                    }
//                }
//
//                if(cookie.contains("logined=true")) {
//
//                }
//            }
//
//            if(request.getPath().contains(".")) {
//                byte[] body = Files.readAllBytes(new File("./webapp" + request.getPath()).toPath());
//                DataOutputStream dos = new DataOutputStream(out);
//
//                if(request.getPath().contains("css")) {
//                    response200HeaderWithCss(dos, body.length);
//                    responseBody(dos, body);
//                } else {
//                    response200Header(dos, body.length);
//                    responseBody(dos, body);
//                }
//            } else {
//                byte[] body = "Hello World".getBytes();
//                DataOutputStream dos = new DataOutputStream(out);
//                response200Header(dos, body.length);
//                responseBody(dos, body);
//            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response200HeaderWithCss(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/css;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, String redirectPath) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + redirectPath + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response302HeaderWithLogin(DataOutputStream dos, String redirectPath, boolean logined) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + redirectPath + "\r\n");
            dos.writeBytes("Set-Cookie: logined=" + logined + "; Path=/\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

}
