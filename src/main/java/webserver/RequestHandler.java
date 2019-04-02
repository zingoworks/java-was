package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;

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
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = br.readLine();
            log.debug("request line : {}", line);

            if(line == null) {
                return;
            }

            String url = HttpRequestUtils.getUrl(line);
            log.debug("url : {}", url);
            int contentLength = 0;

            if(HttpRequestUtils.getMethod(line).equals("GET")) {
                while (!line.equals("")) {
                    line = br.readLine();
                    log.debug("header : {}", line);
                }
            }

            if (HttpRequestUtils.getMethod(line).equals("POST")) {
                while (!line.equals("")) {
                    line = br.readLine();
                    log.debug("header : {}", line);

                    if(line.startsWith("Content-Length")) {
                        contentLength = Integer.valueOf(HttpRequestUtils.parseHeader(line).getValue());
                    }
                }
            }

            String requestBody = IOUtils.readData(br, contentLength);
            log.debug("requestBody : {}", requestBody);

            DataOutputStream dos = new DataOutputStream(out);

            if(url.equals("/user/create")) {
                DataBase.addUser(HttpRequestUtils.getUserFromRequestParameter(requestBody));
                response302Header(dos, "/index.html");
                return;
            }

            if(url.equals("/user/login")) {
                if(DataBase.findUserById(HttpRequestUtils.getUserFromRequestParameter(requestBody).getUserId()) != null) {
                    try {
                        dos.writeBytes("HTTP/1.1 302 Found \r\n");
                        dos.writeBytes("Location: /index.html \r\n");
                        dos.writeBytes("Set-Cookie: logined=true; Path=/ \r\n");
                        dos.writeBytes("\r\n");
                        return;
                    } catch (IOException e) {
                        log.error(e.getMessage());
                    }
                }

                try {
                    dos.writeBytes("HTTP/1.1 302 Found \r\n");
                    dos.writeBytes("Location: /user/login_failed.html \r\n");
                    dos.writeBytes("Set-Cookie: logined=false; Path=/ \r\n");
                    dos.writeBytes("\r\n");
                    return;
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }

            byte[] body = Files.readAllBytes(new File("./webapp" + url).toPath());

            response200Header(dos, body.length);
            responseBody(dos, body);

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

    private void response302Header(DataOutputStream dos, String locationPath) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + locationPath + "\r\n");
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
