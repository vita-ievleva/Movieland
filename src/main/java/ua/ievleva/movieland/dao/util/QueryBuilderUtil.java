package ua.ievleva.movieland.dao.util;


import java.util.Map;

public class QueryBuilderUtil {

    public static String buildConditionQuery(Map<String, String> searchParameters) {
        StringBuilder sb = new StringBuilder();
        searchParameters.forEach((k, v) -> sb.append(k).append("=\"").append(v).append("\" AND "));

        return sb.delete(sb.length() - 4, sb.length() - 1).toString();
    }

    public static String buildOrderByQuery(Map<String, String> searchParameters) {
        StringBuilder sb = new StringBuilder();
        searchParameters.forEach((k, v) -> {
            if (k.equalsIgnoreCase("desc") || k.equalsIgnoreCase("ask")) {
                sb.append(k).append(" ").append(v).append(", ");
            }
        });

        return sb.deleteCharAt(sb.length() - 2).toString();
    }
}

