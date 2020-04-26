package com.konto.konto.userToken;

import com.konto.konto.auth.AuthUtil;
import com.konto.konto.user.UserToken;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserTokenDao {

    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    public List<UserToken> getById(int tokenId){
        String sql = "SELECT * FROM user_token WHERE id=:id";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", tokenId);
        return namedJdbcTemplate.query(sql, namedParameters, new UserTokenRowMapper());
    }

    public List<UserToken> getByUserId(int userId){
        String sql = "SELECT * FROM user_token WHERE userId=:userId";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("userId", userId);
        return namedJdbcTemplate.query(sql, namedParameters, new UserTokenRowMapper());
    }

    public void update(UserToken token){
        String sql = "UPDATE user_token SET access_token=:accessToken, access_token_expiration=:accessTokenExpiration, refresh_token=:refreshToken, refresh_token_expiration=:refreshTokenExpiration, scope=:scope, provider=:provider, userId=:userId  WHERE id=:id";
        SqlParameterSource namedParameters = getSqlParameterMap(token);
        namedJdbcTemplate.update(sql, namedParameters);
    }

    public void insert(UserToken token){
        String sql = "INSERT INTO user_token(access_token, access_token_expiration, refresh_token, refresh_token_expiration, scope, provider, userid) VALUES (:accessToken, :accessTokenExpiration, :refreshToken, :refreshTokenExpiration, :scope, :provider, :userId)";
        SqlParameterSource namedParameters = getSqlParameterMap(token);
        namedJdbcTemplate.update(sql, namedParameters);
    }

    public void insertAll(List<UserToken> tokens){
        String sql = "INSERT INTO user_token(access_token, access_token_expiration, refresh_token, refresh_token_expiration, scope, provider, userid) VALUES (:accessToken, :accessTokenExpiration, :refreshToken, :refreshTokenExpiration, :scope, :provider, :userId)";
        namedJdbcTemplate.batchUpdate(sql, SqlParameterSourceUtils.createBatch(tokens.toArray()));
    }

    private SqlParameterSource getSqlParameterMap(UserToken token){
        return new MapSqlParameterSource()
                .addValue("id", token.getId())
                .addValue("accessToken", token.getAccessToken())
                .addValue("accessTokenExpiration", token.getAccessTokenExpiration())
                .addValue("refreshToken", token.getRefreshToken())
                .addValue("refreshTokenExpiration", token.getRefreshTokenExpiration())
                .addValue("scope", token.getScope())
                .addValue("provider", token.getProvider().name())
                .addValue("userId", AuthUtil.getCurrentUser().getId());
    }

}
