package com.konto.konto.user;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
@RequiredArgsConstructor
public class UserDao {

    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    public User getUserByEmail(String email){
        String sql = "SELECT * FROM \"user\" WHERE email=:email";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("email", email);

        return namedJdbcTemplate.queryForObject(sql, namedParameters, new UserRowMapper());
    }

    public User getUserById(int userId){
        String sql = "SELECT * FROM \"user\" WHERE id=?";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", userId);

        return namedJdbcTemplate.queryForObject(sql, namedParameters, new UserRowMapper());
    }

    public void update(User user){
        String sql = "UPDATE \"user\" SET fname=:fname, lname=:lname, email=:email, password=:password WHERE id=:id";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", user.getId())
                .addValue("fname", user.getFname())
                .addValue("lname", user.getLname())
                .addValue("email", user.getEmail())
                .addValue("password", user.getPassword());

        namedJdbcTemplate.update(sql, namedParameters);
    }

    public void insert(User user){
        String sql = "INSERT INTO \"user\"(fname, lname, email, password) VALUES (:fname, :lname, :email, :password)";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("fname", user.getFname())
                .addValue("lname", user.getLname())
                .addValue("email", user.getEmail())
                .addValue("password", user.getPassword());
        namedJdbcTemplate.update(sql, namedParameters);
    }
}
