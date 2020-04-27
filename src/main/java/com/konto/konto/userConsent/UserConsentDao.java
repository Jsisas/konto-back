package com.konto.konto.userConsent;

import com.konto.konto.openBankingApi.model.OpenBankingProviderName;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class UserConsentDao {

    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    public UserConsent getByUserIdAndProvider(int userId, OpenBankingProviderName provider){
        String sql = "SELECT * FROM user_consent WHERE userId=:userId AND provider=:provider";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("provider", provider.name());
        return namedJdbcTemplate.queryForObject(sql, namedParameters, new UserConsentRowMapper());
    };

}
