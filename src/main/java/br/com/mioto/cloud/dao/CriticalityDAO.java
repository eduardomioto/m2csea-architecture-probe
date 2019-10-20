package br.com.mioto.cloud.dao;

import java.sql.SQLException;

import br.com.mioto.cloud.vo.CriticalityVO;

public interface CriticalityDAO {

    public void saveAvailabilityStatus(String microservice, String status) throws SQLException;

    void saveCriticality(CriticalityVO vo) throws SQLException;
}
