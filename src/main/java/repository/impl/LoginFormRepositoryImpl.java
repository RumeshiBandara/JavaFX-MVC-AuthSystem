package repository.impl;

import db.DBConnection;
import repository.LoginFormRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginFormRepositoryImpl implements LoginFormRepository {
    @Override
    public String getPasswordHashByEmail(String email) {
        String sql = "SELECT password_hash FROM users WHERE email=?";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("password_hash");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }


}
