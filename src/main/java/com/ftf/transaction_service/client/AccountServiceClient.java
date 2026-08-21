package com.ftf.transaction_service.client;

import com.ftf.transaction_service.client.dto.AccountResponse;
import com.ftf.transaction_service.client.dto.InternalTransferRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AccountServiceClient {

    private final RestTemplate restTemplate;

    public AccountServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public AccountResponse getAccountByNumber(String accountNumber) {

        String url = "http://localhost:8081/api/v1/accounts/byNumber/"+accountNumber;
        return restTemplate.getForObject(url, AccountResponse.class, accountNumber);
    }

    public void transfer(InternalTransferRequest request) {
        restTemplate.postForEntity(
                "http://localhost:8081/api/v1/accounts/internal/transfers",
                request,
                Void.class
        );
    }
}
