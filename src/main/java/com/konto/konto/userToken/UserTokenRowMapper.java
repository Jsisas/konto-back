package com.konto.konto.userToken;

import com.konto.konto.openBankingApi.model.OpenBankingProviderName;
import com.konto.konto.user.UserToken;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

class UserTokenRowMapper implements RowMapper<UserToken> {
    public UserToken mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserToken student = new UserToken();
        student.setId(rs.getInt("id"));
        student.setAccessToken(rs.getString("access_token"));
        student.setAccessTokenExpiration(rs.getTimestamp("access_token_expiration").toLocalDateTime());
        student.setRefreshToken(rs.getString("refresh_token"));
        student.setRefreshTokenExpiration(rs.getTimestamp("refresh_token_expiration").toLocalDateTime());
        student.setScope(rs.getString("scope"));
        student.setProvider(OpenBankingProviderName.valueOf(rs.getString("provider")));
        return student;
    }
}
