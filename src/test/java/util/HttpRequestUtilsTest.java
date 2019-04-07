package util;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static webserver.HttpMethod.GET;

public class HttpRequestUtilsTest {
    private String lineWithQueryString = "GET /user/create?userId=testId&password=password HTTP/1.1 \n";
    private String lineWithoutQueryString = "GET /user/create HTTP/1.1 \n";
    private String headerLine = "Connection: keep-alive \n";

    @Test
    public void getPath() {
        assertThat(HttpRequestUtils.getPath(lineWithQueryString)).isEqualTo("/user/create");
        assertThat(HttpRequestUtils.getPath(lineWithoutQueryString)).isEqualTo("/user/create");
    }

    @Test
    public void getMethod() {
        assertThat(HttpRequestUtils.getMethod(lineWithQueryString)).isEqualTo(GET);
    }

    @Test
    public void getHeader() {
        Map<String, String> header = new HashMap<>();
        header.put("Connection", "keep-alive");
        assertThat(HttpRequestUtils.getHeader(headerLine)).isEqualTo(header);
    }
}
