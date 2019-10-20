package br.com.mioto.cloud.dao;

import java.sql.SQLException;
import java.util.List;

import br.com.mioto.cloud.vo.ConsulStatus;

public interface AvailabilityDAO {

    public void saveAvailabilityStatus(String microservice, String status) throws SQLException;

    public List<ConsulStatus> getAvailability(String days) throws SQLException;


}
