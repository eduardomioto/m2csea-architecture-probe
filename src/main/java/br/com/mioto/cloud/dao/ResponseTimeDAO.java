package br.com.mioto.cloud.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import br.com.mioto.cloud.vo.ResponseTime;

public interface ResponseTimeDAO {

    public Map<String, Double> getAverageTime(String days) throws SQLException;

    public List<ResponseTime> getMeanTime(String days) throws SQLException;
}
