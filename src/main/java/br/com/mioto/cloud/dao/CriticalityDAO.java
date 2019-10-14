package br.com.mioto.cloud.dao;

import java.sql.SQLException;

import br.com.mioto.cloud.vo.CriticalityVO;

public interface CriticalityDAO {

    void saveCriticality(CriticalityVO vo) throws SQLException;
}
