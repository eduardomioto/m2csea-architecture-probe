package br.com.mioto.cloud.dao;

import java.sql.SQLException;
import java.util.List;

import br.com.mioto.cloud.vo.ResponseTime;

public interface ResponseTimeDAO {

    public List<ResponseTime> getResponseTime() throws SQLException;
}
