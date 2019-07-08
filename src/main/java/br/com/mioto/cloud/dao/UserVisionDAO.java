package br.com.mioto.cloud.dao;

import br.com.mioto.cloud.vo.UserVision;

import java.sql.SQLException;
import java.util.List;

public interface UserVisionDAO {

    public List<UserVision> getAllUserVision() throws SQLException;

    public void storeUserVision(UserVision userVision) throws SQLException;

    public void updateUserVision(UserVision userVision) throws SQLException;
}
