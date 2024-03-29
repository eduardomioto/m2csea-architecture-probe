package br.com.mioto.cloud.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import br.com.mioto.cloud.dao.ResponseTimeDAO;
import br.com.mioto.cloud.vo.ResponseTime;

@Component
public class ResponseTimeDAOImpl extends BaseDAOImpl implements ResponseTimeDAO  {

    @Override
    public Map<String, Double> getAverageTime(String days) throws SQLException {
        final Connection conn =  getConnection();
        final String query = "SELECT avg(response_time) as avg, microservice " +
        " FROM response_time " +
        " WHERE dt_transaction >= DATE(NOW()) - INTERVAL " + days  + " DAY " +
        " GROUP BY microservice";

        final java.sql.Statement stmt = conn.createStatement();
        final ResultSet rs = stmt.executeQuery(query);

        final Map<String, Double> map = new HashMap<String, Double>();
        while (rs.next()) {
            map.put(rs.getString("microservice"), rs.getDouble("avg"));
        }

        conn.close();
        return map;
    }

    @Override
    public List<ResponseTime> getMeanTime(String days) throws SQLException {

        final String query = "SET @rowindex := -1; " +
                "SELECT " +
                "   AVG(g.response_time) as mean, g.microservice " +
                "FROM " +
                "   (SELECT @rowindex:=@rowindex + 1 AS rowindex, " +
                "           response_time.response_time AS response_time " +
                "    FROM response_time " +
               // "    WHERE dt_transaction >= DATE(NOW()) - INTERVAL " + days  + " DAY " +
                "    ORDER BY response_time) AS g " +
                "WHERE " +
                "g.rowindex IN (FLOOR(@rowindex / 2) , CEIL(@rowindex / 2))";

        final Connection conn =  getConnection();

        final java.sql.Statement stmt = conn.createStatement();
        final ResultSet rs = stmt.executeQuery(query);

        final List<ResponseTime> list = new ArrayList<ResponseTime>();
        while (rs.next()) {
            final ResponseTime responseTime = new ResponseTime();
            responseTime.setAverage( rs.getDouble("mean"));
            responseTime.setProject(rs.getString("microservice"));
            list.add(responseTime);
        }

        conn.close();
        return list;
    }

}
