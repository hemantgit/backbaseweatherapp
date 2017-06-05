package bartburg.nl.backbaseweather.provision.remote.util;

import junit.framework.Assert;

import org.junit.Test;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Bart on 6/3/2017.
 */

public class QueryStringUtilTest {
    @Test
    public void mapToQueryStringTest() throws Exception {
        HashMap<String, String> testMap = new HashMap<>();
        testMap.put("q", "Den Bosch");
        testMap.put("lat", "45.234342364");
        testMap.put("long", "-45.234342364");
        String queryString = QueryStringUtil.mapToQueryString(testMap);
        Pattern pattern = Pattern.compile("^\\?([^=]+=[^=]+&)+[^=]+(=[^=]+)?$");
        Matcher matcher = pattern.matcher(queryString);
        Assert.assertTrue(matcher.matches());
    }
}
