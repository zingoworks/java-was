//package webserver;
//
//public class RequestLine {
//
//    private String method;
//    private String url;
//
//    public RequestLine(String requestLine) {
//        String[] parsed = requestLine.split(" ");
//        this.method = parsed[0];
//        this.url = parsed[1];
//    }
//
//    public String getMethod() {
//        return method;
//    }
//
//    public void setMethod(String method) {
//        this.method = method;
//    }
//
//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }
//
//    @Override
//    public String toString() {
//        final StringBuilder sb = new StringBuilder("RequestLine{");
//        sb.append("method='").append(method).append('\'');
//        sb.append(", url='").append(url).append('\'');
//        sb.append('}');
//        return sb.toString();
//    }
//
//}
