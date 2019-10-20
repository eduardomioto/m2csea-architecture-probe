package br.com.mioto.cloud.dao;

import java.sql.SQLException;
import java.util.List;

import br.com.mioto.cloud.vo.UnitTestCoverage;

public interface CoverageDAO {

    /**
     * Gets the unit test coverage.
     *
     * @return the unit test coverage
     * @throws SQLException the SQL exception
     */
    public List<UnitTestCoverage> getUnitTestCoverage() throws SQLException;
}
