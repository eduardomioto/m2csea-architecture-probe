package br.com.mioto.cloud.bo;

import java.sql.SQLException;

import br.com.mioto.cloud.vo.CriticalityVO;

public interface CriticalityBO {

    void saveCriticality(CriticalityVO vo) throws SQLException;

    public CriticalityVO populate(String microservice, Integer criticalityFactor, String value, String vision);
}
