package com.mtn.services;


import com.google.gson.Gson;
import com.mtn.dto.MakeTransfer;
import com.mtn.responses.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.mtn.constants.Constants.*;

@Service
@Slf4j
public class MTNServices {

    private static WebClientService webclientService;

    @Autowired
    public void setWebclientService(WebClientService webclientService) {
        this.webclientService = webclientService;
    }


    public static String getToken() {
        TokenResponse res = webclientService.requestWithEndpoint(domainUrl + tokenUrl)
                .post()
                .header("Ocp-Apim-Subscription-Key", "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
                .header("Authorization", "Basic xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
                .retrieve()
                .bodyToMono(TokenResponse.class)
                .block();

        String result = "Bearer " + res.getAccess_token();
        log.info("Result: " + result);

        return result;
    }

    public String makeTransfer(MakeTransfer request,String referenceID) {
        String token = getToken();
        HttpStatus res = webclientService.requestWithEndpoint(domainUrl + transferUrl)
                .post()
                .header("Ocp-Apim-Subscription-Key", "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
                .header("X-Target-Environment", "sandbox")
                .header("Authorization", token)
                .header("X-Reference-Id", referenceID)
                .body(Mono.just(request), MakeTransfer.class)
                .retrieve()
                .toBodilessEntity()
                .map(response -> response.getStatusCode())
                .block();

        Gson gson = new Gson();
        String result = gson.toJson(res.value());
        log.info("Result: " + result);

        return result;
    }

    public GetTransferResponse transferStatus(String referenceID) {
        String token = getToken();
        GetTransferResponse res = webclientService.requestWithEndpoint(domainUrl + transferUrl + "/" + referenceID)
                .get()
                .header("Ocp-Apim-Subscription-Key", "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
                .header("X-Target-Environment", "sandbox")
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(GetTransferResponse.class)
                .block();
        Gson gson = new Gson();
        String result = gson.toJson(res);
        log.info("Result: " + result);

        return res;
    }

    public AccountBalanceResponse getAccountBalance() {
        String token = getToken();
        AccountBalanceResponse res = webclientService.requestWithEndpoint(domainUrl + accountBalanceUrl)
                .get()
                .header("Ocp-Apim-Subscription-Key", "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
                .header("X-Target-Environment", "sandbox")
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(AccountBalanceResponse.class)
                .block();
        Gson gson = new Gson();
        String result = gson.toJson(res);
        log.info("Result: " + result);

        return res;
    }

    public AccountHolderResponse getAccountHolderStatus(String msisdn) {
        String token = getToken();
        AccountHolderResponse res = webclientService.requestWithEndpoint(domainUrl + accountHolderUrl + msisdn + "/active")
                .get()
                .header("Ocp-Apim-Subscription-Key", "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
                .header("X-Target-Environment", "sandbox")
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(AccountHolderResponse.class)
                .block();
        Gson gson = new Gson();
        String result = gson.toJson(res);
        log.info("Result: " + result);

        return res;
    }

    public AcountHolderInfoResponse getAccountHolderInfo(String msisdn) {
        String token = getToken();
        AcountHolderInfoResponse res = webclientService.requestWithEndpoint(domainUrl + accountHolderUrl + msisdn + "/basicuserinfo")
                .get()
                .header("Ocp-Apim-Subscription-Key", "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
                .header("X-Target-Environment", "sandbox")
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(AcountHolderInfoResponse.class)
                .block();
        Gson gson = new Gson();
        String result = gson.toJson(res);
        log.info("Result: " + result);

        return res;
    }

}
