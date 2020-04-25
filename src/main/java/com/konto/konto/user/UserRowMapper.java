package com.konto.konto.user;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User student = new User();
        student.setId(rs.getInt("id"));
        student.setFname(rs.getString("fname"));
        student.setLname(rs.getString("lname"));
        return student;
    }
}