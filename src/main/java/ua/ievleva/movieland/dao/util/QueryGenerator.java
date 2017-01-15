package ua.ievleva.movieland.dao.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Properties;


@Service
public class QueryGenerator {

    @Autowired
    private Properties sqlQueries;

    public static String buildConditionQuery(Map<String, String> searchParameters) {
        StringBuilder sb = new StringBuilder();
        searchParameters.forEach((k, v) -> sb.append(k).append("=\"").append(v).append("\" AND "));

        return sb.delete(sb.length() - 4, sb.length() - 1).toString();
    }

    public static String buildOrderByQuery(Map<String, String> searchParameters) {
        StringBuilder sb = new StringBuilder();
        searchParameters.forEach((k, v) -> {
            if (k.equalsIgnoreCase("desc") || k.equalsIgnoreCase("asc")) {
                sb.append(k).append(" ").append(v).append(", ");
            }
        });

        return sb.deleteCharAt(sb.length() - 2).toString();
    }

    public String generateQueryAllMovies(Map<String, String> parameters, String key) {

        String sqlCondition ;

        if (parameters.containsKey("desc") || parameters.containsKey("asc")) {
            sqlCondition = buildOrderByQuery(parameters);

        } else {
            sqlCondition = sqlQueries.getProperty("default.order.by");
        }

        return sqlQueries.getProperty(key)
                .replace("[conditions]", sqlCondition);
    }

    public String generateSearchQuery(Map<String, String> searchParameters) {
        String sql = sqlQueries.getProperty("movies.select.bySearchParameters");

        return sql.replace("[conditions]", buildConditionQuery(searchParameters));
    }

    public String generateQuery(String key) {
        return sqlQueries.getProperty(key);
    }

}

