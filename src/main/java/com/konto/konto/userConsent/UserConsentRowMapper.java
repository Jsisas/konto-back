package com.konto.konto.userConsent;

import com.konto.konto.openBankingApi.model.OpenBankingProviderName;
import com.konto.konto.user.UserToken;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

class UserConsentRowMapper implements RowMapper<UserConsent> {
    public UserConsent mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserConsent userConsent = new UserConsent();
        userConsent.setId(rs.getInt("id"));
        userConsent.setConsentId(rs.getString("consent_id"));
        userConsent.setProvider(OpenBankingProviderName.valueOf(rs.getString("provider")));
        return userConsent;
    }
}