package webserver.response;

import webserver.HttpBody;
import webserver.HttpHeader;
import webserver.HttpStatus;

import java.io.DataOutputStream;
import java.io.OutputStream;

public class HttpResponse {

    private HttpStatus status;
    private HttpHeader header;
    private HttpBody body;

    private DataOutputStream dos;

    public HttpResponse(OutputStream out) {
        this.dos = new DataOutputStream(out);

    }

    public void addHeader(String key, String value) {

    }

    public void forward(String value) {

    }

    public void forwardBody(String value) {

    }

    public void response200Header(int value) {

    }

    public void sendRedirect(String value) {

    }

    public void processHeader() {

    }

}
