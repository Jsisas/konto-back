package com.konto.konto.openBankingApi;

import com.konto.konto.auth.AuthUtil;
import com.konto.konto.openBankingApi.model.OpenBankingProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping(OpenBankingService.OPEN_BANKING_API_ENDPOINT)
public class OpenBankingController {

    private final OpenBankingService openBankingService;

    @GetMapping("/info")
    public ResponseEntity<List<OpenBankingProvider>> getOpenBankingProviderInfo(){
        return ResponseEntity.ok(openBankingService.getOpenBankingProviderInfo());
    }

    @PostMapping("/token-exists")
    public ResponseEntity<Map<String, Boolean>> currentUserTokenExists(@RequestBody OpenBankingProvider provider){
        return ResponseEntity.ok(Map.of("exists", openBankingService.currentUserTokenExists(provider)));
    }

}
