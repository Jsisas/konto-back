package com.konto.konto.openBankingApi.lhv;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping(path = "/api/openbanking/lhv")
public class LhvOpenBankingController {

    private final LhvOpenBankingService lhvOpenBankingService;


}
