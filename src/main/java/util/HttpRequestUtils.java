package util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import webserver.HttpMethod;

//TODO : Guava 라이브러리 사용하여 리팩토링 해보기
public class HttpRequestUtils {

    /**
     * @param requestLine First line of request
     * @return
     */

    public static String getPath(String requestLine) {
        String[] parsed = requestLine.split(" ");

        String path = parsed[1];
        if (path.contains("?")) {
            return path.split("\\?")[0];
        }

        return path;
    }

    public static String getQueryString(String requestLine) {
        String[] parsed = requestLine.split(" ");

        String path = parsed[1];
        if (path.contains("?")) {
            return path.split("\\?")[1];
        }

        return null;
    }

    public static Map<String, String> getParameter(String values) {
        return parseValues(values, "&");
    }

    private static Map<String, String> parseValues(String values, String separator) {
        if (values == null) {
            return new HashMap<>();
        }

        String[] tokens = values.split(separator);
        return Arrays.stream(tokens)
                .map(i -> i.split("="))
                .collect(Collectors.toMap(p -> p[0], p -> p[1]));
    }

    public static HttpMethod getMethod(String requestLine) {
        String[] parsed = requestLine.split(" ");
        return HttpMethod.of(parsed[0]);
    }

    /**
     * @param headerLine Line contains header info
     * @return
     */

    public static Map<String, String> getHeader(String headerLine) {
        Map<String, String> temp = new HashMap<>();
        String[] tokens = headerLine.split(": ");
        temp.put(tokens[0].trim(), tokens[1].trim());
        return temp;
    }
}
