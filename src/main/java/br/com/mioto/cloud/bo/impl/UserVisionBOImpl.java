package br.com.mioto.cloud.bo.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.mioto.cloud.bo.DockerApiBO;
import br.com.mioto.cloud.dao.UserVisionDAO;
import br.com.mioto.cloud.vo.UserVision;

@Component
public class UserVisionBOImpl implements DockerApiBO {

    @Autowired
    private UserVisionDAO userVisionDAO;

    public List<UserVision> getAllUserVision() throws SQLException {
        return  userVisionDAO.getAllUserVision();
    }

    public void storeUserVision(UserVision userVision) throws SQLException {
        userVisionDAO.storeUserVision(userVision);
    }

    public void updateUserVision(UserVision userVision) throws SQLException {
        userVisionDAO.storeUserVision(userVision);
    }
}
