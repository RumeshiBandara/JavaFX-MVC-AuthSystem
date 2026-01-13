package repository.impl;

import db.DBConnection;
import model.dto.SingUpDTO;
import repository.SingUpFormRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SingUpFormRepositoryImpl implements SingUpFormRepository {
    @Override
    public boolean saveUser(SingUpDTO user) {
        String sql = "INSERT INTO users(first_name, last_name, email, password_hash) VALUES(?,?,?,?)";
        try {
            Connection con = DBConnection.getInstance().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, user.getFirstName());
            pst.setString(2, user.getLastName());
            pst.setString(3, user.getEmail());
            pst.setString(4, user.getPassword());
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isEmailExist(String email) {
        String sql = "SELECT * FROM users WHERE email=?";
        try {
            Connection con = DBConnection.getInstance().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
