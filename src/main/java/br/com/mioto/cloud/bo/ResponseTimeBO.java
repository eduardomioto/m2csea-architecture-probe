package br.com.mioto.cloud.bo;

import java.sql.SQLException;
import java.util.List;

import br.com.mioto.cloud.vo.ResponseTime;

public interface ResponseTimeBO {

    List<ResponseTime> getAllResponseTimes() throws SQLException;



}
