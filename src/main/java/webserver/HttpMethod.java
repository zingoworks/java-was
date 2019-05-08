package webserver;

public enum HttpMethod {

    GET,
    POST;

    public static HttpMethod of(String method) {
        for (HttpMethod value : values()) {
            if(value.toString().equals(method)) {
                return value;
            }
        }
        return GET;
    }

}
