package br.com.mioto.cloud.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.mioto.cloud.dao.BaseDAOImpl;
import br.com.mioto.cloud.dao.ResponseTimeDAO;
import br.com.mioto.cloud.vo.ResponseTime;

@Component
public class ResponseTimeDAOImpl extends BaseDAOImpl implements ResponseTimeDAO  {

    @Override
    public List<ResponseTime> getResponseTime() throws SQLException {
        final Connection conn =  getConnection();
        final String query = "SELECT avg(response_time) as avg, microservice  FROM response_time GROUP BY microservice";

        final java.sql.Statement stmt = conn.createStatement();
        final ResultSet rs = stmt.executeQuery(query);

        final List<ResponseTime> list = new ArrayList<ResponseTime>();
        while (rs.next()) {
            final ResponseTime responseTime = new ResponseTime();
            responseTime.setAverage( rs.getDouble("avg"));
            responseTime.setProject(rs.getString("microservice"));
            list.add(responseTime);
        }

        conn.close();
        return list;
    }

}
