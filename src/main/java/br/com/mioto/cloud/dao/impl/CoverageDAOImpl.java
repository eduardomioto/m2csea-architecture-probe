package br.com.mioto.cloud.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.mioto.cloud.dao.CoverageDAO;
import br.com.mioto.cloud.vo.UnitTestCoverage;

@Component
public class CoverageDAOImpl extends BaseDAOImpl implements CoverageDAO  {

    @Override
    public List<UnitTestCoverage> getUnitTestCoverage() throws SQLException {
        final Connection conn =  getConnection();
        final String query = "SELECT microservice, coverage from unit_test_coverage order by coverage asc";

        final java.sql.Statement stmt = conn.createStatement();
        final ResultSet rs = stmt.executeQuery(query);

        final List<UnitTestCoverage> list = new ArrayList<UnitTestCoverage>();
        while (rs.next()) {
            final UnitTestCoverage unitTestCoverage = new UnitTestCoverage();
            unitTestCoverage.setMicroservice(rs.getString("microservice"));
            unitTestCoverage.setCoverage(rs.getDouble("coverage"));
            list.add(unitTestCoverage);
        }

        conn.close();
        return list;
    }
}
