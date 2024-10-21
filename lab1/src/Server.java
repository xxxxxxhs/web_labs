import com.fastcgi.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class Server {


    public static void main(String[] args) {
        FCGIInterface fcgiInterface = new FCGIInterface();

        while (fcgiInterface.FCGIaccept() >= 0) {
            String method = FCGIInterface.request.params.getProperty("REQUEST_METHOD");
            if (method.equals("POST")) {
                long time = System.nanoTime();
                try {
                    String body = readRequestBody();
                    if (!body.isEmpty()) {
                        LinkedHashMap<String, String> params =
                                parseJson(body);
                        if (params.containsKey("x") && params.containsKey("y") && params.containsKey("r")) {
                            try {
                                double x = Double.parseDouble(params.get("x"));
                                double y = Double.parseDouble(params.get("y"));
                                double r = Double.parseDouble(params.get("r"));

                                if (validateParams(x, y, r)) {
                                    boolean isHit = Checker.hit(x, y, r);
                                    System.out.println(response(time, x, y, r, isHit));
                                } else {
                                    System.out.println(error("validation error"));
                                }
                            } catch (NumberFormatException e) {
                                System.out.println(error("invalid data types in json"));
                            }
                        } else {
                            System.out.println(error("miss required params in json"));
                        }
                    } else {
                        System.out.println(error("empty post body"));
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }

    private static String readRequestBody() throws IOException {
        FCGIInterface.request.inStream.fill();

        var contentLength = FCGIInterface.request.inStream.available();
        var buffer = ByteBuffer.allocate(contentLength);
        var readBytes = FCGIInterface.request.inStream.read(buffer.array(), 0, contentLength);

        var requestBodyRaw = new byte[readBytes];
        buffer.get(requestBodyRaw);
        buffer.clear();

        return new String(requestBodyRaw, StandardCharsets.UTF_8);
    }

    private static String response(long time, double x, double y, double r, boolean isHit) {
        String content = """
                {"result":"%s","x":"%s","y":"%s","r":"%s","time":"%s","workTime":"%s","error":"all ok"}
                """.formatted(isHit, x, y, r, (double) (System.nanoTime() - time) / 10000000,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        return """
                Content-Type: application/json; charset=utf-8
                Content-Length: %d
                                
                                
                %s
                """.formatted(content.getBytes(StandardCharsets.UTF_8).length, content);
    }

    private static LinkedHashMap<String, String> parseJson(String json) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        json = json.trim().replaceAll("[{}\"]", "");
        String[] pairs = json.split(",");
        for (String pair : pairs) {
            String[] keyValue = pair.split(":");
            if (keyValue.length == 2) {
                map.put(keyValue[0].trim(), keyValue[1].trim());
            }
        }
        return map;
    }

    private static String error(String msg) {
        String content = """
                {"error":"%s"}
                """.formatted(msg);
        return """
                Content-Type: application/json; charset=utf-8
                Content-Length: %d

                %s
                """.formatted(content.getBytes(StandardCharsets.UTF_8).length, content);
    }

    private static boolean validateParams(double x, double y, double r) {
        if (x <= -3 || x >= 3) {
            return false;
        }
        if (y < -4 || y > 4) {
            return false;
        }
        if (!(r == 1 || r == 2 || r == 3 || r == 4 || r == 5)) {
            return false;
        }

        return true;
    }
}
