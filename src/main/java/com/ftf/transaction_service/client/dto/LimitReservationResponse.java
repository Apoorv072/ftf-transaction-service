package com.ftf.transaction_service.client.dto;

import lombok.Data;

@Data
public class LimitReservationResponse {

    private String transactionReference;
    private boolean allowed;
    private String reservationId;
    private String message;

}
