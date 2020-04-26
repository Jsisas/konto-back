package com.konto.konto.openBankingApi;

import com.konto.konto.openBankingApi.model.OpenBankingProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(OpenBankingService.OPEN_BANKING_API_ENDPOINT)
public class OpenBankingController {

    private final OpenBankingService openBankingService;

    @GetMapping("/info")
    public ResponseEntity<List<OpenBankingProvider>> getOpenBankingProviderInfo(){
        return ResponseEntity.ok(openBankingService.getOpenBankingProviderInfo());
    }

}
