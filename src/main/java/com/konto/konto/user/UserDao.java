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

    public User getUserById(int userId){
        String sql = "SELECT * FROM user WHERE id=?";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", userId);

        return namedJdbcTemplate.queryForObject(sql, namedParameters, new UserRowMapper());
    }

    public void upsert(User user){
        String sql = "";
        SqlParameterSource namedParameters = new MapSqlParameterSource();
        if(user.getId() == null){
            sql = "INSERT INTO user(fname, lname) VALUES (:fname, :lname)";
            namedParameters = new MapSqlParameterSource()
                    .addValue("fname", user.getFname())
                    .addValue("lname", user.getLname());
        }else{
            sql = "UPDATE user SET fname=:fname, lname=:lname WHERE id=:id";
            namedParameters = new MapSqlParameterSource()
                    .addValue("id", user.getId())
                    .addValue("fname", user.getFname())
                    .addValue("lname", user.getLname());
        }
        namedJdbcTemplate.update(sql, namedParameters);
    }
}
