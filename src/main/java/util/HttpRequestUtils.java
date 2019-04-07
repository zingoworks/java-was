package util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.google.common.base.Strings;
import webserver.HttpMethod;

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

    public static HttpMethod getMethod(String requestLine) {
        String[] parsed = requestLine.split(" ");
        return HttpMethod.of(parsed[0]);
    }

    /**
     * @param headerLine Line contains header info
     * @return
     */

    public static Map<String, String> parseHeader(String headerLine) {
        if (headerLine == null || headerLine.isEmpty()) {
            return new HashMap<>();
        }

        Map<String, String> temp = new HashMap<>();
        String[] tokens = headerLine.split(": ");
        temp.put(tokens[0].trim(), tokens[1].trim());
        return temp;
    }

    /**
     *
     * @param parameters QueryString in URL or RequestBody
     * @return
     */

    public static Map<String, String> parseParameters(String parameters) {
        return parseValues(parameters, "&");
    }

    private static Map<String, String> parseValues(String values, String separator) {
        if (values == null || values.isEmpty()) {
            return new HashMap<>();
        }

        String[] tokens = values.split(separator);
        return Arrays.stream(tokens)
                .map(i -> getKeyValue(i, "="))
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    private static Pair getKeyValue(String keyValue, String regex) {
        if (Strings.isNullOrEmpty(keyValue)) {
            return null;
        }

        String[] tokens = keyValue.split(regex);
        if (tokens.length != 2) {
            return null;
        }

        return new Pair(tokens[0], tokens[1]);
    }

    public static class Pair {
        String key;
        String value;

        Pair(String key, String value) {
            this.key = key.trim();
            this.value = value.trim();
        }

        String getKey() {
            return key;
        }

        String getValue() {
            return value;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((key == null) ? 0 : key.hashCode());
            result = prime * result + ((value == null) ? 0 : value.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Pair other = (Pair) obj;
            if (key == null) {
                if (other.key != null)
                    return false;
            } else if (!key.equals(other.key))
                return false;
            if (value == null) {
                if (other.value != null)
                    return false;
            } else if (!value.equals(other.value))
                return false;
            return true;
        }

        @Override
        public String toString() {
            return "Pair [key=" + key + ", value=" + value + "]";
        }
    }
}
