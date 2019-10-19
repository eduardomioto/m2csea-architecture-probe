package br.com.mioto.cloud.bo;

import br.com.mioto.cloud.vo.CriticalityVO;

public interface CriticalityBO {

    void saveCriticality(CriticalityVO vo);

    public CriticalityVO populate(String microservice, Integer criticalityFactor, String value, String vision);
}
