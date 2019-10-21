package br.com.mioto.cloud.bo.impl;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.mioto.cloud.bo.CriticalityBO;
import br.com.mioto.cloud.dao.CriticalityDAO;
import br.com.mioto.cloud.vo.CriticalityVO;

/**
 * The Class CriticalityBOImpl.
 */
@Component
public class CriticalityBOImpl implements CriticalityBO {

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(CriticalityBOImpl.class);

    /** The criticality DAO. */
    @Autowired
    private CriticalityDAO criticalityDAO;

    /**
     * Save criticality.
     *
     * @param vo the vo
     * @throws SQLException the SQL exception
     */
    @Override
    public void saveCriticality(CriticalityVO vo) {
        try {
            criticalityDAO.saveCriticality(vo);
        } catch (final Exception e) {
            log.error("Error saving criticality. Error: ", e);
        }
    }



    /**
     * Populate.
     *
     * @param microservice the microservice
     * @param criticalityFactor the criticality factor
     * @param value the value
     * @param vision the vision
     * @return the criticality VO
     */
    @Override
    public CriticalityVO populate(String microservice, Integer criticalityFactor, String value, String vision) {
        final CriticalityVO vo = new CriticalityVO();
        vo.setMicroservice(microservice);
        vo.setCriticalityFactor(criticalityFactor);
        vo.setValue(value);
        vo.setVision(vision);
        return vo;

    }

    /**
     * Populate.
     *
     * @param microservice the microservice
     * @param criticalityFactor the criticality factor
     * @param value the value
     * @param vision the vision
     * @return the criticality VO
     */
    @Override
    public boolean hasChangeConfig() {
        boolean hasChangeConfig = false;
        try {
            hasChangeConfig = criticalityDAO.hasChangeConfig();
        } catch (final Exception e) {
            log.error("Error saving criticality. Error: ", e);
        }
        return hasChangeConfig;

    }

}
