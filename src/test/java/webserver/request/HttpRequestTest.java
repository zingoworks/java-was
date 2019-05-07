package webserver.request;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.Assert.*;
import static webserver.HttpMethod.GET;
import static webserver.HttpMethod.POST;

public class HttpRequestTest {

    private String testDirectory = "./src/test/resources/";

    @Test
    public void request_GET() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "GET.txt"));
        HttpRequest request = new HttpRequest(in);

        assertEquals(GET, request.getMethod());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("javajigi", request.getParameter("userId"));
    }

    @Test
    public void request_POST() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "POST.txt"));
        HttpRequest request = new HttpRequest(in);

        assertEquals(POST, request.getMethod());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("javajigi", request.getParameter("userId"));
    }

}