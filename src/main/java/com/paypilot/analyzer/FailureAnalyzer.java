package com.paypilot.analyzer;

import com.paypilot.enums.FailureCategory;
import org.springframework.stereotype.Component;

@Component
public class FailureAnalyzer {

    public FailureCategory analyze(String failureReason) {

        if (failureReason == null || failureReason.isBlank()) {
            return FailureCategory.UNKNOWN;
        }

        String reason = failureReason.trim().toUpperCase();

        switch (reason) {

            case "BANK_TIMEOUT":
            case "UPI_TIMEOUT":
                return FailureCategory.TIMEOUT;

            case "NETWORK_ERROR":
                return FailureCategory.NETWORK;

            case "INSUFFICIENT_FUNDS":
                return FailureCategory.CUSTOMER_ERROR;

            case "INVALID_CARD":
                return FailureCategory.PAYMENT_METHOD_ERROR;

            default:
                return FailureCategory.UNKNOWN;
        }
    }
    public boolean isRetryable(String failureReason) {

        FailureCategory category = analyze(failureReason);

        return category == FailureCategory.TIMEOUT
                || category == FailureCategory.NETWORK;
    }
}