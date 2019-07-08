package br.com.mioto.cloud.dao.impl;

import br.com.mioto.cloud.dao.UserVisionDAO;
import br.com.mioto.cloud.vo.UserVision;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserVisionDAOImpl extends BaseDAOImpl implements UserVisionDAO  {

   public List<UserVision> getAllUserVision() throws SQLException {
       Connection conn =  getConnection();
       String query = "SELECT microservice, rating FROM user_vision order by microservice";
       Statement st = conn.createStatement();
       ResultSet rs = st.executeQuery(query);

       List<UserVision> listUserVision = new ArrayList<>();
       while (rs.next())
       {
           UserVision userVision = new UserVision();
           userVision.setMicroservice(rs.getString("microservice"));
           userVision.setRating(rs.getInt("rating"));
           listUserVision.add(userVision);
       }
       st.close();
       return listUserVision;
   }

    public void storeUserVision(UserVision userVision) throws SQLException {
        Connection conn =  getConnection();
        String query = "INSERT INTO user_vision (microservice, rating) VALUES(?, ?) ON DUPLICATE KEY UPDATE rating=?, microservice=?";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setString (1, userVision.getMicroservice());
        preparedStmt.setInt (2, userVision.getRating());
        preparedStmt.setInt (3, userVision.getRating());
        preparedStmt.setString (4, userVision.getMicroservice());
        preparedStmt.execute();
        conn.close();
    }

    public void updateUserVision(UserVision userVision) throws SQLException {
        Connection conn =  getConnection();
        String query = " update user_vision set rating = ? where microservice =  ?";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt   (1, userVision.getRating());
        preparedStmt.setString(2, userVision.getMicroservice());
        preparedStmt.executeUpdate();
        conn.close();
    }
}
