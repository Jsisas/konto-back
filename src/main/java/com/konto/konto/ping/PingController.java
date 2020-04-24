package com.konto.konto.ping;

import com.konto.konto.OpenBankingApi.lhv.LhvOpenBankingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@AllArgsConstructor
@RequestMapping(path = "/api/ping")
public class PingController {

    private final LhvOpenBankingService lhvOpenBankingService;

    @ResponseBody
    @GetMapping("/authenticated")
    public ResponseEntity<String> lhvAuth(HttpServletRequest request, @RequestParam(value = "code") String code) {
        return ResponseEntity.ok("ok");
    }
}
