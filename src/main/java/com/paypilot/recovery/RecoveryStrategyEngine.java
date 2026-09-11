package com.paypilot.recovery;

import com.paypilot.enums.FailureCategory;
import com.paypilot.enums.RecoveryStrategy;
import org.springframework.stereotype.Component;

@Component
public class RecoveryStrategyEngine {

    public RecoveryStrategy determineStrategy(
            FailureCategory category) {

        if (category == null) {
            return RecoveryStrategy.NO_ACTION;
        }

        switch (category) {

            case TIMEOUT:
            case NETWORK:
                return RecoveryStrategy.RETRY;

            case PAYMENT_METHOD_ERROR:
                return RecoveryStrategy.FALLBACK_PAYMENT_METHOD;

            case CUSTOMER_ERROR:
                return RecoveryStrategy.CUSTOMER_ACTION;

            case SYSTEM_ERROR:
                return RecoveryStrategy.RETRY;

            case UNKNOWN:
            default:
                return RecoveryStrategy.NO_ACTION;
        }
    }
}