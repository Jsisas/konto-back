package com.konto.konto.userToken;

import com.konto.konto.auth.AuthUtil;
import com.konto.konto.user.UserToken;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserTokenDao {

    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    public List<UserToken> getByUserId(int userId){
        String sql = "SELECT * FROM user_token WHERE userId=:userId";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("userId", userId);
        return namedJdbcTemplate.query(sql, namedParameters, new UserTokenRowMapper());
    }

    public void upsert(UserToken token){
        String sql = "";
        SqlParameterSource namedParameters;
        if(token.getId() == null){
            sql = "INSERT INTO user_token(access_token, access_token_expiration, refresh_token, refresh_token_expiration, scope, provider, userid) VALUES (:accessToken, :accessTokenExpiration, :refreshToken, :refreshTokenExpiration, :scope, :provider, :userId)";
            namedParameters = new MapSqlParameterSource()
                    .addValue("accessToken", token.getAccessToken())
                    .addValue("accessTokenExpiration", token.getAccessTokenExpiration())
                    .addValue("refreshToken", token.getRefreshToken())
                    .addValue("refreshTokenExpiration", token.getRefreshTokenExpiration())
                    .addValue("scope", token.getScope())
                    .addValue("provider", token.getProvider())
                    .addValue("userId", AuthUtil.getCurrentUser().getId());
        }else{
            sql = "UPDATE user_token SET access_token=:accessToken, access_token_expiration=:accessTokenExpiration, refresh_token=:refreshToken, refresh_token_expiration=:refreshTokenExpiration, scope=:scope, provider=:provider, userId=:userId  WHERE id=:id";
            namedParameters = new MapSqlParameterSource()
                    .addValue("id", token.getId())

                    .addValue("accessToken", token.getAccessToken())
                    .addValue("accessTokenExpiration", token.getAccessTokenExpiration())
                    .addValue("refreshToken", token.getRefreshToken())
                    .addValue("refreshTokenExpiration", token.getRefreshTokenExpiration())
                    .addValue("scope", token.getScope())
                    .addValue("provider", token.getProvider())
                    .addValue("userId", AuthUtil.getCurrentUser().getId());
        }
        namedJdbcTemplate.update(sql, namedParameters);
    }

}
