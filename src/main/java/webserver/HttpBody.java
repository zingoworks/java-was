//package webserver;
//
//import util.HttpRequestUtils;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//public class HttpBody {
//
//    private Map<String, String> parameter = new HashMap<>();
//
//    public HttpBody(BufferedReader br) throws IOException {
//        while(true) {
//            String requestBody = br.readLine();
//            System.out.println("body를 받습니다 " + requestBody);
//
//            if(requestBody == null || requestBody.equals("")) {
//                break;
//            }
//
//            System.out.println("break 안하고");
//
//            this.parameter = HttpRequestUtils.parseQueryString(requestBody);
//        }
//    }
//
//    public String getParameter(String key) {
//        if(parameter.isEmpty() || !parameter.containsKey(key)) {
//            throw new IllegalArgumentException("인자가 없거나 존재하지 않는 Key 입니다.");
//        }
//
//        return parameter.get(key);
//    }
//
//    public Map<String, String> getParameters() {
//        return parameter;
//    }
//
//}
