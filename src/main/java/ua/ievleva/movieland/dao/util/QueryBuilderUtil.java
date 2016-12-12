package ua.ievleva.movieland.dao.util;


import java.util.Map;

public class QueryBuilderUtil{

    public static String buildConditionQuery(Map<String, String> searchParameters) {
        StringBuilder sb = new StringBuilder();
        searchParameters.forEach((k, v) -> sb.append(k).append("=\"").append(v).append("\" AND "));

        return sb.delete(sb.length() - 4, sb.length() - 1).toString();
    }
}

