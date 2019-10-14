package br.com.mioto.cloud.bo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.json.JSONException;
import org.json.simple.parser.ParseException;

import br.com.mioto.cloud.vo.ComputationalResources;

public interface DockerBO {


    /**
     * Gets the all resource comsuption.
     *
     * @return the all resource comsuption
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ParseException the parse exception
     * @throws ParseException the parse exception
     * @throws ParseException the parse exception
     * @throws JSONException the JSON exception
     * @throws SQLException
     */
    public List<ComputationalResources> getAllResourceComsuption() throws IOException, ParseException, org.apache.http.ParseException, JSONException, java.text.ParseException, SQLException ;


}
