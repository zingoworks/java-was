package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
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
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String requestHeader = br.readLine();
            System.out.println(requestHeader);

            String method = HttpRequestUtils.parseMethod(requestHeader);
            String url = HttpRequestUtils.parseUrl(requestHeader);

            List<HttpRequestUtils.Pair> header = new ArrayList<>();

            while(true) {
                requestHeader = br.readLine();
                System.out.println(requestHeader);

                if(requestHeader.equals("")) {
                    break;
                }

                header.add(HttpRequestUtils.parseHeader(requestHeader));
            }

            if(method.equals("POST")) {
                int contentLength = 0;

                for (HttpRequestUtils.Pair pair : header) {
                    if(pair.getKey().equals("Content-Length")) {
                        contentLength = Integer.parseInt(pair.getValue());
                    }
                }

                String requestBody = IOUtils.readData(br, contentLength);
                Map<String, String> userValues = HttpRequestUtils.parseQueryString(requestBody);

                User user = new User(
                        userValues.get("userId"),
                        userValues.get("password"),
                        userValues.get("name"),
                        userValues.get("email")
                );

                DataBase.addUser(user);
                System.out.println(requestBody + "\n");

                DataOutputStream dos = new DataOutputStream(out);
                response302Header(dos, "/index.html");
            }

            if(url.contains("?")) {
                String[] tokens = HttpRequestUtils.getTokens(url, "\\?");
                url = tokens[0];
                String queryString = tokens[1];
                Map<String, String> queryData = HttpRequestUtils.parseQueryString(queryString);
            }

            if(url.contains(".")) {
                byte[] body = Files.readAllBytes(new File("./webapp" + url).toPath());
                DataOutputStream dos = new DataOutputStream(out);
                response200Header(dos, body.length);
                responseBody(dos, body);
            } else {
                byte[] body = "Hello World".getBytes();
                DataOutputStream dos = new DataOutputStream(out);
                response200Header(dos, body.length);
                responseBody(dos, body);
            }
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

    private void response302Header(DataOutputStream dos, String redirectPath) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + redirectPath + "\r\n");
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
