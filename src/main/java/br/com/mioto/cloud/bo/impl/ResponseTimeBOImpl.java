package br.com.mioto.cloud.bo.impl;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.mioto.cloud.bo.ResponseTimeBO;
import br.com.mioto.cloud.dao.ResponseTimeDAO;
import br.com.mioto.cloud.vo.ResponseTime;

@Component
public class ResponseTimeBOImpl implements ResponseTimeBO {

    private static final Logger log = LoggerFactory.getLogger(ResponseTimeBOImpl.class);

    @Autowired
    private ResponseTimeDAO ResponseTimeDAO;

    @Override
    public List<ResponseTime> getAllResponseTimes() throws SQLException {
        return ResponseTimeDAO.getResponseTime();

    }




}
