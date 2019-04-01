package util;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Map;

import model.User;
import org.junit.Test;

import util.HttpRequestUtils.Pair;

public class HttpRequestUtilsTest {
    @Test
    public void parseQueryString() {
        String queryString = "userId=javajigi";
        Map<String, String> parameters = HttpRequestUtils.parseQueryString(queryString);
        assertThat(parameters.get("userId"), is("javajigi"));
        assertThat(parameters.get("password"), is(nullValue()));

        queryString = "userId=javajigi&password=password2";
        parameters = HttpRequestUtils.parseQueryString(queryString);
        assertThat(parameters.get("userId"), is("javajigi"));
        assertThat(parameters.get("password"), is("password2"));
    }

    @Test
    public void parseQueryString_null() {
        Map<String, String> parameters = HttpRequestUtils.parseQueryString(null);
        assertThat(parameters.isEmpty(), is(true));

        parameters = HttpRequestUtils.parseQueryString("");
        assertThat(parameters.isEmpty(), is(true));

        parameters = HttpRequestUtils.parseQueryString(" ");
        assertThat(parameters.isEmpty(), is(true));
    }

    @Test
    public void parseQueryString_invalid() {
        String queryString = "userId=javajigi&password";
        Map<String, String> parameters = HttpRequestUtils.parseQueryString(queryString);
        assertThat(parameters.get("userId"), is("javajigi"));
        assertThat(parameters.get("password"), is(nullValue()));
    }

    @Test
    public void parseCookies() {
        String cookies = "logined=true; JSessionId=1234";
        Map<String, String> parameters = HttpRequestUtils.parseCookies(cookies);
        assertThat(parameters.get("logined"), is("true"));
        assertThat(parameters.get("JSessionId"), is("1234"));
        assertThat(parameters.get("session"), is(nullValue()));
    }

    @Test
    public void getKeyValue() throws Exception {
        Pair pair = HttpRequestUtils.getKeyValue("userId=javajigi", "=");
        assertThat(pair, is(new Pair("userId", "javajigi")));
    }

    @Test
    public void getKeyValue_invalid() throws Exception {
        Pair pair = HttpRequestUtils.getKeyValue("userId", "=");
        assertThat(pair, is(nullValue()));
    }

    @Test
    public void parseHeader() throws Exception {
        String header = "Content-Length: 59";
        Pair pair = HttpRequestUtils.parseHeader(header);
        assertThat(pair, is(new Pair("Content-Length", "59")));
    }

    //이하 추가 구현부

    @Test
    public void parseRequestLine() throws Exception {
        String requestLine = "GET /index.html HTTP/1.1";
        String[] tokens = HttpRequestUtils.parseRequestLine(requestLine);

        assertThat(tokens[0], is("GET"));
        assertThat(tokens[1], is("/index.html"));
        assertThat(tokens[2], is("HTTP/1.1"));
    }

    @Test
    public void getMethod() throws Exception {
        String requestLine = "GET /index.html HTTP/1.1";
        String url = HttpRequestUtils.getMethod(requestLine);
        assertThat(url, is("GET"));
    }

    @Test
    public void getUrl() throws Exception {
        String requestLine = "GET /index.html HTTP/1.1";
        String url = HttpRequestUtils.getUrl(requestLine);
        assertThat(url, is("/index.html"));
    }

    @Test
    public void getHttpVersion() throws Exception {
        String requestLine = "GET /index.html HTTP/1.1";
        String url = HttpRequestUtils.getHttpVersion(requestLine);
        assertThat(url, is("HTTP/1.1"));
    }

    @Test
    public void getParsedUrl() throws Exception {
        String url = "/user/create?userId=test&password=test&name=test&email=test";
        String parsedUrl = HttpRequestUtils.getParsedUrl(url);
        assertThat(parsedUrl, is("/user/create"));
    }

    @Test
    public void getRequestParameter() throws Exception {
        String url = "/user/create?userId=test&password=test&name=test&email=test";
        String requestParameter = HttpRequestUtils.getRequestParameter(url);
        assertThat(requestParameter, is("userId=test&password=test&name=test&email=test"));
    }

    @Test
    public void getUserFromRequestParameter() throws Exception {
        String url = "/user/create?userId=test&password=test&name=test&email=test";
        String requestParameter = HttpRequestUtils.getRequestParameter(url);
        User user = HttpRequestUtils.getUserFromRequestParameter(requestParameter);
        assertThat(user, is(new User("test", "test", "test", "test")));
    }
}
