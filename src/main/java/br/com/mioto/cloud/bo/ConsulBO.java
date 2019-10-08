package br.com.mioto.cloud.bo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.http.ParseException;
import org.json.JSONException;

import br.com.mioto.cloud.vo.ConsulHealthcheck;

public interface ConsulBO {

    /**
     * Gets the all healthchecks.
     *
     * @return the all healthchecks
     * @throws SQLException the SQL exception
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ParseException the parse exception
     * @throws ParseException the parse exception
     * @throws ParseException the parse exception
     * @throws JSONException the JSON exception
     */
    public List<ConsulHealthcheck> getAllHealthchecks() throws SQLException, IOException, ParseException, java.text.ParseException, org.json.simple.parser.ParseException, JSONException;

}
