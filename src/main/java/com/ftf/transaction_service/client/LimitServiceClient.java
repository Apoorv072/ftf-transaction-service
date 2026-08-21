package com.ftf.transaction_service.client;

import com.ftf.transaction_service.client.dto.LimitReservationRequest;
import com.ftf.transaction_service.client.dto.LimitReservationResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class LimitServiceClient {

    private final RestTemplate restTemplate;

    public LimitServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public LimitReservationResponse reserveLimit(LimitReservationRequest request) {

        return restTemplate.postForObject("http://localhost:8083/api/v1/internal/limits/reserve", request, LimitReservationResponse.class);
    }

    public void releaseLimit(String reservationId) {

        restTemplate.delete("http://localhost:8083/api/v1/internal/limits/reservations/{reservationId}", reservationId);
    }
}
