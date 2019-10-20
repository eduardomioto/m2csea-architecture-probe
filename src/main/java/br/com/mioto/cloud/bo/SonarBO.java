package br.com.mioto.cloud.bo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.http.ParseException;
import org.json.JSONException;

import br.com.mioto.cloud.vo.AgregattedSonarIssues;
import br.com.mioto.cloud.vo.SonarIssues;
import br.com.mioto.cloud.vo.UnitTestCoverage;

public interface SonarBO {

    /**
     * Gets the all issues.
     *
     * @return the all issues
     * @throws SQLException the SQL exception
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ParseException the parse exception
     * @throws ParseException the parse exception
     * @throws ParseException the parse exception
     * @throws JSONException the JSON exception
     */
    public List<SonarIssues> getAllIssues() throws SQLException, IOException, ParseException, java.text.ParseException, org.json.simple.parser.ParseException, JSONException;

    /**
     * Gets the issues aggregated by services.
     *
     * @return the issues aggregated by services
     * @throws SQLException the SQL exception
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ParseException the parse exception
     * @throws ParseException the parse exception
     * @throws ParseException the parse exception
     * @throws JSONException the JSON exception
     */
    public List<AgregattedSonarIssues> getIssuesAggregatedByServices()
            throws SQLException, IOException, ParseException, java.text.ParseException, org.json.simple.parser.ParseException, JSONException;

    /**
     * Gets the unit test coverage.
     *
     * @return the unit test coverage
     * @throws SQLException
     */
    public List<UnitTestCoverage> getUnitTestCoverage() throws SQLException;

}
