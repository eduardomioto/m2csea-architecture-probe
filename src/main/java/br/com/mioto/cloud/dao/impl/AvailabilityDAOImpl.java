package br.com.mioto.cloud.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.mioto.cloud.dao.AvailabilityDAO;
import br.com.mioto.cloud.vo.ConsulStatus;

@Component
public class AvailabilityDAOImpl extends BaseDAOImpl implements AvailabilityDAO  {

    @Override
    public void saveAvailabilityStatus(String microservice, String status) throws SQLException {

        final Connection conn =  getConnection();
        final String query = "INSERT INTO availability (microservice, status, dt_transaction) VALUES(?, ?, NOW())";

        final PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setString (1, microservice);
        preparedStmt.setString (2, status);

        preparedStmt.execute();
        conn.close();

        conn.close();

    }

    @Override
    public List<ConsulStatus> getAvailability(String days) throws SQLException {
        final Connection conn =  getConnection();
        final String query = "SELECT count(*) as downtimeOccurrences, microservice " +
                " FROM availability " +
                " WHERE dt_transaction >= DATE(NOW()) - INTERVAL " + days + " DAY " +
                " and status = \"critical\"" +
                " GROUP BY microservice";

        final java.sql.Statement stmt = conn.createStatement();
        final ResultSet rs = stmt.executeQuery(query);

        final List<ConsulStatus> list = new ArrayList<ConsulStatus>();
        while (rs.next()) {
            final ConsulStatus consulStatus = new ConsulStatus();
            consulStatus.setName(rs.getString("microservice"));
            consulStatus.setDowntimeOccurrences(rs.getInt("downtimeOccurrences"));
            list.add(consulStatus);
        }

        conn.close();
        return list;
    }
}
