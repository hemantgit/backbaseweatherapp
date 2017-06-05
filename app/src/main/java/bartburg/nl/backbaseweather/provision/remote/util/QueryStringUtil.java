package bartburg.nl.backbaseweather.provision.remote.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bart on 6/3/2017.
 */

public class QueryStringUtil {
    public static String mapToQueryString(HashMap<String, String> map) {
        StringBuilder sb = new StringBuilder();
        try {
            boolean first = true;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                sb.append(first ? "?" : "&");
                sb.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "utf-8"));
                first = false;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
