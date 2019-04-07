package webserver;

public enum HttpMethod {
    GET,
    PUT,
    POST,
    DELETE;

    public static HttpMethod of(String method) {
        for (HttpMethod value : HttpMethod.values()) {
            if(value.toString().equals(method)) {
                return value;
            }
        }

        return null;
    }
}
